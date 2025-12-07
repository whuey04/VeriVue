package com.verivue.wemedia.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verivue.file.service.FileStorageService;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.wemedia.dto.WmLoginDto;
import com.verivue.model.wemedia.dto.WmUserUpdateDto;
import com.verivue.model.wemedia.pojo.WmUser;
import com.verivue.model.wemedia.vo.WmUserVo;
import com.verivue.utils.common.AppJwtUtil;
import com.verivue.utils.thread.WmThreadLocalUtil;
import com.verivue.wemedia.mapper.WmUserMapper;
import com.verivue.wemedia.service.WmUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements WmUserService {

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * WeMedia Login
     *
     * @param loginDto
     * @return
     */
    @Override
    public ResponseResult login(WmLoginDto loginDto) {

        if(StringUtils.isBlank(loginDto.getName()) || StringUtils.isBlank(loginDto.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "Username or password is invalid");
        }

        // Query user
        WmUser user = getOne(Wrappers.<WmUser>lambdaQuery().eq(WmUser::getName, loginDto.getName()));
        if(user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        if (user.getStatus() != 9){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR, "Account is Locked");
        }

        //Check password
        String salt = user.getSalt();
        String password = loginDto.getPassword();
        password = DigestUtils.md5DigestAsHex((salt + password).getBytes());
        if (password.equals(user.getPassword())) {

            user.setLoginTime(new Date());
            updateById(user);

            // return jwt data
            Map<String, Object> map = new HashMap<>();
            map.put("token", AppJwtUtil.getToken(user.getId().longValue()));
            map.put("user", user);
            return ResponseResult.okResult(map);

        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
    }

    /**
     * Get Current User Details
     *
     * @return
     */
    @Override
    public ResponseResult getWmUserDetails() {
        WmUserVo wmUserVo = new WmUserVo();

        Long userId = WmThreadLocalUtil.getUser().getId();
        WmUser user = getById(userId);
        if(user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "User Not Exist");
        }

        BeanUtils.copyProperties(user, wmUserVo);
        return ResponseResult.okResult(wmUserVo);
    }

    /**
     * Get User Details by ID
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult getWmUserDetailsById(Long id) {
        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmUser user = getById(id);
        if(user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "User Not Exist");
        }

        return ResponseResult.okResult(user);
    }

    /**
     * Update User Details
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult updateWmUser(WmUserUpdateDto dto) {
        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmUser dbUser = getById(dto.getId());
        if (dbUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "User Not Exist");
        }

        if(StringUtils.isNotBlank(dto.getNickname()) && !dbUser.getNickname().equals(dto.getNickname())){
            dbUser.setNickname(dto.getNickname());
        }

        if (StringUtils.isNotBlank(dto.getEmail()) && !dbUser.getEmail().equals(dto.getEmail())) {
            dbUser.setEmail(dto.getEmail());
        }

        if (StringUtils.isNotBlank(dto.getPhone()) && !dbUser.getPhone().equals(dto.getPhone())) {
            dbUser.setPhone(dto.getPhone());
        }

        if(StringUtils.isNotBlank(dto.getImage()) && (dbUser.getImage() == null || !dbUser.getImage().equals(dto.getImage()))) {
            if (StringUtils.isNotBlank(dbUser.getImage())) {
                fileStorageService.delete(dbUser.getImage());
            }
            dbUser.setImage(dto.getImage());
        }

        if (dto.getType() != null && !dbUser.getType().equals(dto.getType())){
            dbUser.setType(dto.getType());
        }

        if (StringUtils.isNotBlank(dto.getPassword())) {
            String salt = dbUser.getSalt();
            String newPassword = DigestUtils.md5DigestAsHex((salt + dto.getPassword()).getBytes());
            if(!newPassword.equals(dbUser.getPassword())){
                dbUser.setPassword(newPassword);
            }
        }

        updateById(dbUser);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS.getCode(), "Update WeMedia success");
    }
}
