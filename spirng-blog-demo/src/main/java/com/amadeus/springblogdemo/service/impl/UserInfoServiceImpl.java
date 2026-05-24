package com.amadeus.springblogdemo.service.impl;

import com.amadeus.springblogdemo.common.exception.BlogException;
import com.amadeus.springblogdemo.common.utils.BeanConvert;
import com.amadeus.springblogdemo.common.utils.JwtUtils;
import com.amadeus.springblogdemo.common.utils.SecurityUtils;
import com.amadeus.springblogdemo.domain.BlogInfo;
import com.amadeus.springblogdemo.domain.UserInfo;
import com.amadeus.springblogdemo.mapper.BlogInfoMapper;
import com.amadeus.springblogdemo.mapper.UserInfoMapper;
import com.amadeus.springblogdemo.pojo.request.UserLoginRequest;
import com.amadeus.springblogdemo.pojo.response.UserInfoResponse;
import com.amadeus.springblogdemo.pojo.response.UserLoginResponse;
import com.amadeus.springblogdemo.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    private final BlogInfoMapper blogInfoMapper;

    public UserInfoServiceImpl(BlogInfoMapper blogInfoMapper) {
        this.blogInfoMapper = blogInfoMapper;
    }

    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        if (request == null || !StringUtils.hasText(request.getUserName()) || !StringUtils.hasText(request.getPassword())) {
            throw new BlogException("用户名或密码不能为空");
        }

        UserInfo userInfo = getOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getUserName, request.getUserName())
                .eq(UserInfo::getDeleteFlag, 0));
        if (userInfo == null || userInfo.getId() == null) {
            throw new BlogException("用户不存在");
        }
        if (!SecurityUtils.verify(request.getPassword(), userInfo.getPassword())) {
            throw new BlogException("密码错误");
        }

        Map<String, Object> claim = new HashMap<>();
        claim.put("userId", userInfo.getId());
        claim.put("userName", userInfo.getUserName());
        return new UserLoginResponse(userInfo.getId(), JwtUtils.genJwt(claim));
    }

    @Override
    public UserInfoResponse getUserInfo(Integer userId) {
        UserInfo userInfo = getById(userId);
        if (userInfo == null || Integer.valueOf(1).equals(userInfo.getDeleteFlag())) {
            throw new BlogException("用户不存在");
        }
        return BeanConvert.convert(userInfo);
    }

    @Override
    public UserInfoResponse getAuthorInfo(Integer blogId) {
        BlogInfo blogInfo = blogInfoMapper.selectById(blogId);
        if (blogInfo == null || Integer.valueOf(1).equals(blogInfo.getDeleteFlag())) {
            throw new BlogException("博客不存在");
        }
        return getUserInfo(blogInfo.getUserId());
    }
}
