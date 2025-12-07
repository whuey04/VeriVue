package com.verivue.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verivue.admin.mapper.AdUserMapper;
import com.verivue.admin.service.AdUserService;
import com.verivue.common.constants.UserConstants;
import com.verivue.file.service.FileStorageService;
import com.verivue.model.admin.dto.AdUserDto;
import com.verivue.model.admin.dto.AdUserLoginDto;
import com.verivue.model.admin.dto.AdUserPageQueryDto;
import com.verivue.model.admin.dto.AdUserUpdateDto;
import com.verivue.model.admin.pojo.AdUser;
import com.verivue.model.common.dto.PageResponseResult;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.user.pojo.ApUser;
import com.verivue.utils.common.AppJwtUtil;
import com.verivue.utils.common.SaltGenerateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AdUserServiceImpl extends ServiceImpl<AdUserMapper, AdUser> implements AdUserService {

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Admin Login
     *
     * @param adUserDto
     * @return
     */
    @Override
    public ResponseResult adminLogin(AdUserLoginDto adUserDto) {
        if(StringUtils.isBlank(adUserDto.getName()) || StringUtils.isBlank(adUserDto.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "Username or password is empty");
        }

        AdUser user = getOne(Wrappers.<AdUser>lambdaQuery().eq(AdUser::getName, adUserDto.getName()));
        if(user == null){
            ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "Invalid User");
        }

        if (user.getStatus() != UserConstants.AD_STATUS_NORMAL) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ACCOUNT_LOCKED);
        }

        String userSalt = user.getSalt();
        String password = adUserDto.getPassword();
        password = DigestUtils.md5DigestAsHex((password + userSalt).getBytes());
        if (!password.equals(user.getPassword())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        user.setLatestLoginTime(new Date());
        updateById(user);

        Map<String,Object> map = new HashMap<>();
        map.put("token", AppJwtUtil.getToken(user.getId().longValue()));
        map.put("user", user);
        return ResponseResult.okResult(map);
    }

    /**
     * Add New Admin Account
     *
     * @param userDto
     * @return
     */
    @Override
    public ResponseResult addAdmin(AdUserDto userDto) {
        if (userDto == null ||
                StringUtils.isBlank(userDto.getName()) ||
                StringUtils.isBlank(userDto.getPassword()) ||
                StringUtils.isBlank(userDto.getPhone())||
                StringUtils.isBlank(userDto.getEmail())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        AdUser user = new AdUser();
        BeanUtils.copyProperties(userDto, user);

        //Check if name exist
        LambdaQueryWrapper<AdUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdUser::getName, userDto.getName());
        long count = count(queryWrapper);
        if (count > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST, "Account already exist");
        }

        // Set default data
        if (StringUtils.isBlank(userDto.getNickname())){
            user.setNickname(UserConstants.AD_USER_NICKNAME);
        }

        // Set Salt
        String salt = SaltGenerateUtils.generateSalt(8);
        user.setSalt(salt);

        // Set Password
        String password = userDto.getPassword();
        password = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        user.setPassword(password);

        user.setStatus(UserConstants.AD_STATUS_NORMAL);
        user.setCreatedTime(new Date());

        save(user);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS.getCode(), "Add new admin success");
    }

    /**
     * Get All Admin in List
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult getAdminList(AdUserPageQueryDto dto) {
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        dto.checkParam();

        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<AdUser> queryWrapper = new LambdaQueryWrapper<>();

        //Account status query
        if (dto.getStatus() != null) {
            queryWrapper.eq(AdUser::getStatus, dto.getStatus());
        }

        //Name fuzzy query
        if(StringUtils.isNotBlank(dto.getName())){
            queryWrapper.like(AdUser::getName, dto.getName());
        }

        queryWrapper.orderByDesc(AdUser::getCreatedTime);
        page = page(page, queryWrapper);


        ResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        result.setData(page.getRecords());

        return result;
    }

    /**
     * Get Admin Details by ID
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult getAdminDetails(Long id) {
        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        AdUser user = getById(id);
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "Admin Not Exist");
        }

        return ResponseResult.okResult(user);
    }

    /**
     * Update Admin Details
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult updateAdminDetails(AdUserUpdateDto dto) {
        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        AdUser dbUser = getById(dto.getId());

        if (dbUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "User Not Exist");
        }

        if(StringUtils.isNotBlank(dto.getNickname()) && !dbUser.getNickname().equals(dto.getNickname())){
            dbUser.setNickname(dto.getNickname());
        }

        if (StringUtils.isNotBlank(dto.getPhone()) && !dbUser.getPhone().equals(dto.getPhone())) {
            dbUser.setPhone(dto.getPhone());
        }

        if (StringUtils.isNotBlank(dto.getEmail()) && !dbUser.getEmail().equals(dto.getEmail())) {
            dbUser.setEmail(dto.getEmail());
        }

        if(StringUtils.isNotBlank(dto.getImage()) && (dbUser.getImage() == null || !dbUser.getImage().equals(dto.getImage()))) {
            if (StringUtils.isNotBlank(dbUser.getImage())) {
                fileStorageService.delete(dbUser.getImage());
            }
            dbUser.setImage(dto.getImage());
        }

        if (StringUtils.isNotBlank(dto.getPassword())) {
            String salt = dbUser.getSalt();
            String newPassword = DigestUtils.md5DigestAsHex((dto.getPassword() + salt).getBytes());
            if(!newPassword.equals(dbUser.getPassword())){
                dbUser.setPassword(newPassword);
            }
        }

        updateById(dbUser);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS.getCode(), "Update admin success");
    }
}
