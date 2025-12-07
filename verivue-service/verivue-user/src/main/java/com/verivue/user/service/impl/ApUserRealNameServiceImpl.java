package com.verivue.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verivue.api.wemedia.IWeMediaClient;
import com.verivue.common.constants.UserVerificationConstants;
import com.verivue.model.user.dto.AuthDto;
import com.verivue.model.user.dto.UserRealnameDto;
import com.verivue.model.user.pojo.ApUserRealname;
import com.verivue.model.common.dto.PageResponseResult;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.user.pojo.ApUser;
import com.verivue.model.wemedia.pojo.WmUser;
import com.verivue.user.mapper.ApUserMapper;
import com.verivue.user.mapper.ApUserRealNameMapper;
import com.verivue.user.service.ApUserRealNameService;
import com.verivue.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@Transactional
public class ApUserRealNameServiceImpl extends ServiceImpl<ApUserRealNameMapper, ApUserRealname> implements ApUserRealNameService {

    @Autowired
    private IWeMediaClient weMediaClient;
    @Autowired
    private ApUserMapper apUserMapper;
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String MyEmail;

    /**
     * Get User Identity Verification List
     *
     * @param authDto
     * @return
     */
    @Override
    public ResponseResult getUserVerificationList(AuthDto authDto) {
        if (authDto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        authDto.checkParam();

        //Page Query
        IPage page = new Page(authDto.getPage(), authDto.getSize());

        LambdaQueryWrapper<ApUserRealname> queryWrapper = new LambdaQueryWrapper<>();

        if (authDto.getStatus() != null) {
            queryWrapper.eq(ApUserRealname::getStatus, authDto.getStatus());
        }

        queryWrapper.orderByDesc(ApUserRealname::getCreatedTime);
        page = page(page, queryWrapper);

        ResponseResult result = new PageResponseResult(authDto.getPage(), authDto.getSize(), (int) page.getTotal());
        result.setData(page.getRecords());
        return result;
    }

    /**
     * Handle User Identity Verification Rejected or Approved
     *
     * @param authDto
     * @param status
     * @return
     */
    @Override
    public ResponseResult handleVerification(AuthDto authDto, Short status) {
        //1. Check Param
        if(authDto == null || authDto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        // 2. Update verification status
        ApUserRealname apUserRealName = new ApUserRealname();
        apUserRealName.setId(authDto.getId());
        apUserRealName.setStatus(status);
        apUserRealName.setUpdatedTime(new Date());
        if(StringUtils.isNotBlank(authDto.getMsg())){
            apUserRealName.setReason(authDto.getMsg());
        }
        updateById(apUserRealName);

        // 3. If status equals 9 (approved), create a media account
        if(status.equals(UserVerificationConstants.APPROVED)){
            ResponseResult result = createNewWmUser(authDto);
            if(result != null){
                return result;
            }
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    private ResponseResult createNewWmUser(AuthDto authDto) {
        Integer id = authDto.getId();

        //Query user identity verification data
        ApUserRealname apUserRealName = getById(id);
        if(apUserRealName == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        // Query user info in app side
        Long userId = apUserRealName.getUserId();
        ApUser user = apUserMapper.selectById(userId);
        if(user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        // Create new WeMedia account
        WmUser wmUser = weMediaClient.findWmUserByName(user.getName());
        if (wmUser == null) {
            wmUser = new WmUser();
            wmUser.setApUserId(user.getId());
            wmUser.setCreatedTime(new Date());
            wmUser.setName(user.getName());
            wmUser.setNickname(user.getNickname());
            wmUser.setImage(user.getImage());
            wmUser.setEmail(user.getEmail());
            wmUser.setType(apUserRealName.getType());
            wmUser.setPassword(user.getPassword());
            wmUser.setSalt(user.getSalt());
            wmUser.setPhone(user.getPhone());
            wmUser.setStatus(9);
            weMediaClient.addWmUser(wmUser);


            //send email
            try {
                sendAccountCreatedEmail(wmUser);
            }catch (Exception e){
                log.error("Failed to send notification email to user: {}", user.getEmail(), e);
            }

        }

        //Update ApUser table
        user.setFlag((short) 1);
        user.setCertification(true);
        user.setIdentityAuthentication(true);
        apUserMapper.updateById(user);

        return null;
    }

    private void sendAccountCreatedEmail(WmUser user) {
        log.info("Start to send email....");
        String email = user.getEmail();
        if (StringUtils.isNotBlank(email)) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(MyEmail);
            message.setTo(email);
            message.setSubject("[VeriVue] WeMedia Account Has Been Created");
            message.setText("Hello " + user.getName() + ",\n\nYour WeMedia account has been successfully created.\n\nRegards,\nVeriVue Team");
            javaMailSender.send(message);
        }
    }


    //User

    /**
     * Register WeMedia account
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult registerWeMedia(UserRealnameDto dto) {
        if(dto == null ||
                dto.getType() == null ||
                StringUtils.isBlank(dto.getName()) ||
                StringUtils.isBlank(dto.getIdno()) ||
                StringUtils.isBlank(dto.getFontImage()) ||
                StringUtils.isBlank(dto.getBackImage()) ||
                StringUtils.isBlank(dto.getHoldImage()) ||
                StringUtils.isBlank(dto.getLiveImage())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApUserRealname apUserRealName = new ApUserRealname();
        BeanUtils.copyProperties(dto, apUserRealName);

        Long userId = AppThreadLocalUtil.getUser().getId();
        apUserRealName.setUserId(userId);

        apUserRealName.setStatus(UserVerificationConstants.PENDING_REVIEW);
        apUserRealName.setCreatedTime(new Date());
        apUserRealName.setSubmitedTime(new Date());
        save(apUserRealName);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS.getCode(), "The materials have been submitted. Please wait patiently for the administrator's approval.");
    }
}
