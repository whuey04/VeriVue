package com.verivue.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verivue.common.constants.UserConstants;
import com.verivue.file.service.FileStorageService;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.user.dto.LoginDto;
import com.verivue.model.user.dto.UserDto;
import com.verivue.model.user.dto.UserUpdateDto;
import com.verivue.model.user.pojo.ApUser;
import com.verivue.model.user.vo.UserVo;
import com.verivue.user.mapper.ApUserMapper;
import com.verivue.user.service.ApUserService;
import com.verivue.utils.common.AppJwtUtil;
import com.verivue.utils.common.SaltGenerateUtils;
import com.verivue.utils.thread.AppThreadLocalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * User Login
     *
     * @param loginDto
     * @return
     */
    @Override
    public ResponseResult userLogin(LoginDto loginDto) {
        // 1. Normal Login (Using phoneNo + password)
        if(!StringUtils.isBlank(loginDto.getPhone()) && !StringUtils.isBlank(loginDto.getPassword())){
            // 1.1 Query User
            ApUser user = getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, loginDto.getPhone()));
            if(user == null){
                ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "Invalid User");
            }

            // 1.2 Check Password
            String userSalt = user.getSalt();
            String password = loginDto.getPassword();
            password = DigestUtils.md5DigestAsHex((userSalt + password).getBytes());
            if (!password.equals(user.getPassword())) {
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }

            // 1.3 return JWT data
            Map<String,Object> map = new HashMap<>();
            map.put("token", AppJwtUtil.getToken(user.getId().longValue()));
            map.put("user", user);
            return ResponseResult.okResult(map);
        }else {
            // 2.Guest user – also returns a token, id = 0
            Map<String,Object> map = new HashMap<>();
            map.put("token",AppJwtUtil.getToken(0L));
            return ResponseResult.okResult(map);
        }
    }

    /**
     * Register user account
     *
     * @param userDto
     * @return
     */
    @Override
    public ResponseResult addUser(UserDto userDto) {
        if (userDto == null ||
                StringUtils.isBlank(userDto.getName()) ||
                StringUtils.isBlank(userDto.getPassword()) ||
                StringUtils.isBlank(userDto.getPhone())||
                StringUtils.isBlank(userDto.getEmail())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApUser user = new ApUser();
        BeanUtils.copyProperties(userDto, user);

        // Check if phone exist
        LambdaQueryWrapper<ApUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApUser::getPhone, userDto.getPhone());
        long phoneCount = count(queryWrapper);
        if (phoneCount > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST, "Account already exist");
        }

        // Set default data
        if (StringUtils.isBlank(userDto.getNickname())) {
            user.setNickname(UserConstants.AP_USER_NICKNAME);
        }

        if (userDto.getSex() == null){
            user.setSex(UserConstants.SEX_UNKNOWN);
        }

        // Set Salt
        String salt = SaltGenerateUtils.generateSalt(8);
        user.setSalt(salt);

        // Set Password
        String password = userDto.getPassword();
        password = DigestUtils.md5DigestAsHex((salt + password).getBytes());
        user.setPassword(password);

        user.setStatus(UserConstants.AP_STATUS_NORMAL);
        user.setFlag(UserConstants.FLAG_NORMAL_USER);
        user.setCreatedTime(new Date());

        save(user);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS.getCode(), "Add new user success");
    }

    /**
     * Get user details
     *
     * @return
     */
    @Override
    public ResponseResult getUserDetails() {

        UserVo userVo = new UserVo();

        //Get current userID
        Long userID = AppThreadLocalUtil.getUser().getId();
        ApUser user = getById(userID);
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "User Not Exist");
        }

        BeanUtils.copyProperties(user, userVo);

        return ResponseResult.okResult(userVo);

    }

    /**
     * Get user details by id
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult getUserDetailsById(Long id) {

        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApUser user = getById(id);
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "User Not Exist");
        }

        return ResponseResult.okResult(user);
    }

    /**
     * Update user details
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult updateUser(UserUpdateDto dto) {

        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApUser dbUser = getById(dto.getId());
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

        if (dto.getSex() != null && !dbUser.getSex().equals(dto.getSex())){
            dbUser.setSex(dto.getSex());
        }

        if (StringUtils.isNotBlank(dto.getPassword())) {
            String salt = dbUser.getSalt();
            String newPassword = DigestUtils.md5DigestAsHex((salt + dto.getPassword()).getBytes());
            if(!newPassword.equals(dbUser.getPassword())){
                dbUser.setPassword(newPassword);
            }
        }

        updateById(dbUser);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS.getCode(), "Update user success");
    }
}
