package com.reder.server.service;

import com.reder.server.databobject.UserInfo;
import org.springframework.stereotype.Service;


public interface UserService {
    /**
     * 通过openid来查询用户信息
     * @param openid
     * @return
     */
    UserInfo  findByOpenid (String openid);
}
