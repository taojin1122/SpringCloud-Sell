package com.reder.server.repository;

import com.reder.server.databobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    UserInfo  findByOpenid(String openid);
}
