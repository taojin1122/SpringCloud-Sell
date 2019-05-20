package com.reder.server.service.impl;

import com.reder.server.databobject.UserInfo;
import com.reder.server.repository.UserInfoRepository;
import com.reder.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService{

    @Autowired
    private UserInfoRepository repository;

    @Override
    public UserInfo findByOpenid(String openid) {
        return  repository.findByOpenid(openid);
    }
}
