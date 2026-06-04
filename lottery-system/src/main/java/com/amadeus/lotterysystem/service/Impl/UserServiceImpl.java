package com.amadeus.lotterysystem.service.Impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.amadeus.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.amadeus.lotterysystem.common.exception.ServiceException;
import com.amadeus.lotterysystem.common.utils.JWTUtil;
import com.amadeus.lotterysystem.common.utils.RegexUtil;
import com.amadeus.lotterysystem.controller.param.UserLoginParam;
import com.amadeus.lotterysystem.controller.param.UserPasswordLogin;
import com.amadeus.lotterysystem.controller.param.UserRegisterParam;
import com.amadeus.lotterysystem.dao.dataobject.Encrypt;
import com.amadeus.lotterysystem.dao.dataobject.UserDO;
import com.amadeus.lotterysystem.dao.mapper.UserMapper;
import com.amadeus.lotterysystem.service.UserService;
import com.amadeus.lotterysystem.service.dto.UserLoginDTO;
import com.amadeus.lotterysystem.service.dto.UserRegisterDTO;
import com.amadeus.lotterysystem.service.enums.UserIdentityEnum;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;




    @Override
    public UserRegisterDTO register(UserRegisterParam param) {
        //参数校验
       checkRegisterInfo(param);

        //加密私密数据
        UserDO userDO = new UserDO();
        userDO.setUserName(param.getName());
        userDO.setEmail(param.getMail());
        userDO.setPhoneNumber(new Encrypt(param.getPhoneNumber()));
        userDO.setIdentity(param.getIdentity());
        //如果密码非空,加密密码
        if(StringUtils.hasText(param.getPassword())){
            userDO.setPassword(DigestUtil.sha256Hex(param.getPassword()));
        }

        userMapper.insert(userDO);

        //构建返回数据
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUserId(userDO.getId());
        return userRegisterDTO;
    }

    @Override
    public UserLoginDTO login(UserLoginParam param) {
        if(param == null){
            throw new ServiceException(ServiceErrorCodeConstants.LOGIN_INFO_IS_EMPTY);
        }
        //检查param的参数类型是否是邮箱 + 密码登录,验证登录方式
        if(param instanceof UserPasswordLogin userPasswordLogin){
            return loginByPassword(userPasswordLogin);
        }
        throw new ServiceException(ServiceErrorCodeConstants.LOGIN_NOT_EXIST);
    }

    private UserLoginDTO loginByPassword(UserPasswordLogin param) {
        String loginName = param.getLoginName();
        String password = param.getPassword();
        if(!StringUtils.hasText(loginName) || !StringUtils.hasText(password)){
            throw new ServiceException(ServiceErrorCodeConstants.LOGIN_INFO_IS_EMPTY);
        }

        //按照LoginName从数据库中查找用户信息
        UserDO userDO = queryUserByLoginName(loginName.trim());
        if(userDO == null){
            throw new ServiceException(ServiceErrorCodeConstants.LOGIN_INFO_NOT_EXIST);
        }

        //有身份信息,但是身份信息和后端查到的身份信息对不上
        if(StringUtils.hasText(param.getMandatoryIdentity()) && !param.getMandatoryIdentity().trim().equalsIgnoreCase(userDO.getIdentity())){
            throw  new ServiceException(ServiceErrorCodeConstants.IDENTITY_ERROR);
        }

        //sha256加密前端密码,把前端密码和数据库中查到的用户信息进行密码校验
        String passwordDigest = DigestUtil.sha256Hex(password);
        if(!StringUtils.hasText(userDO.getPassword()) || !Objects.equals(userDO.getPassword(), passwordDigest)){
            throw new ServiceException(ServiceErrorCodeConstants.PASSWORD_ERROR);
        }

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        //设置token
        userLoginDTO.setToken(buildLoginToken(userDO));
        //设置身份identity
        userLoginDTO.setIdentity(userDO.getIdentity());
        return userLoginDTO;
    }

    private UserDO queryUserByLoginName(String loginName) {
        if(RegexUtil.checkMail(loginName)){
            return userMapper.selectByEmail(loginName);
        }
        if(RegexUtil.checkMobile(loginName)){
            return userMapper.selectByPhoneNumber(new Encrypt(loginName));
        }
        throw new ServiceException(ServiceErrorCodeConstants.LOGIN_INFO_NOT_EXIST);
    }
    
    //创建JWT token令牌
    private String buildLoginToken(UserDO userDO) {
        Map<String, Object> claim = new HashMap<>();
        claim.put("userId", userDO.getId());
        claim.put("userName", userDO.getUserName());
        claim.put("identity", userDO.getIdentity());
        return JWTUtil.genJwt(claim);
    }


    //注册参数校验
    private void checkRegisterInfo(UserRegisterParam param) {
        if (null == param) {
            throw new ServiceException(ServiceErrorCodeConstants.REGISTER_INFO_IS_EMPTY);
        }
        // 校验邮箱格式 xxx@xxx.xxx
        if (!RegexUtil.checkMail(param.getMail())) {
            throw new ServiceException(ServiceErrorCodeConstants.MAIL_ERROR);
        }
        // 校验手机号格式
        if (!RegexUtil.checkMobile(param.getPhoneNumber())) {
            throw new ServiceException(ServiceErrorCodeConstants.PHONE_NUMBER_ERROR);
        }

        // 校验身份信息
        if (null == UserIdentityEnum.forName(param.getIdentity())) {
            throw new ServiceException(ServiceErrorCodeConstants.IDENTITY_ERROR);
        }

        // 校验管理员密码必填
        if (param.getIdentity().equalsIgnoreCase(UserIdentityEnum.ADMIN.name())
                && !StringUtils.hasText(param.getPassword())) {
            throw new ServiceException(ServiceErrorCodeConstants.PASSWORD_IS_EMPTY);
        }

        // 密码校验，至少6位
        if (StringUtils.hasText(param.getPassword())
                && !RegexUtil.checkPassword(param.getPassword())) {
            throw new ServiceException(ServiceErrorCodeConstants.PASSWORD_ERROR);
        }

        // 校验邮箱是否被使用
        if (checkMailUsed(param.getMail())) {
            throw new ServiceException(ServiceErrorCodeConstants.MAIL_USED);
        }

        // 校验手机号是否被使用
        if (checkPhoneNumberUsed(param.getPhoneNumber())) {
            throw new ServiceException(ServiceErrorCodeConstants.PHONE_NUMBER_USED);
        }
    }

    /**
     * 检查注册的手机号是否被占用
     * @param phoneNumber
     * @return 1 : 占用  ,  0 : 未占用
     */

    private boolean checkPhoneNumberUsed(@NotBlank(message = "电话不能为空！") String phoneNumber) {
        Encrypt encryptPhone = new Encrypt(phoneNumber);
        long count = userMapper.countByPhoneNumber(encryptPhone);

        System.out.println("手机号 :" + phoneNumber);
        return count > 0;
    }

    /**
     * 检查注册的邮箱是否被占用
     * @param mail
     * @return 1 : 占用  ,  0 : 未占用
     */

    private boolean checkMailUsed(@NotBlank(message = "邮箱不能为空！") String mail) {
        long count = userMapper.countByEmailLong(mail);

        System.out.println("邮箱 :" + mail);
        return count > 0;
    }
}
