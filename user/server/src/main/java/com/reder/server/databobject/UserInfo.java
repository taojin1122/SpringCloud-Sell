package com.reder.server.databobject;

import lombok.Data;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class UserInfo {

   @Id
    private String id;

    private String username;

    private String password;

    private String openid;

    private Integer role;
}
