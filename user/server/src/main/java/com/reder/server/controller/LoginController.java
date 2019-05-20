package com.reder.server.controller;

import com.netflix.discovery.converters.Auto;
import com.reder.server.VO.ResultVO;
import com.reder.server.constant.CookieConstant;
import com.reder.server.constant.RedisConstant;
import com.reder.server.databobject.UserInfo;
import com.reder.server.enums.ResultEnum;
import com.reder.server.enums.RoleEnum;
import com.reder.server.repository.UserInfoRepository;
import com.reder.server.service.UserService;
import com.reder.server.utils.CookieUtil;
import com.reder.server.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 买家登录
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openid") String openid, HttpServletResponse response) {
        // 1。openId和数据库里面的数据配置
          UserInfo userInfo = userService.findByOpenid(openid);
          if (userInfo == null) {
              return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
          }
        // 2、判断角色
         if (!RoleEnum.BUYER.getCode().equals(userInfo.getRole())) {
               return  ResultVOUtil.error(ResultEnum.ROLE_ERROR);
         }
        // 3、cookie 里设置openid = abc
        CookieUtil.set(response, CookieConstant.OPENID, openid, CookieConstant.EXPIRE);
          return ResultVOUtil.success();
    }

    /**
     * 卖家登录
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("/seller")
    public ResultVO seller(@RequestParam("openid") String openid, HttpServletRequest request, HttpServletResponse response) {
        // 判断是否登录,防止不停的往redis中写数据
        Cookie  cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        String redisValue = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue()));
        if (cookie != null && !StringUtils.isEmpty(redisValue)) {
              return ResultVOUtil.success();
        }

        // 1。openId和数据库里面的数据配置
        UserInfo userInfo = userService.findByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        // 2、判断角色
        if (!RoleEnum.SELLER.getCode().equals(userInfo.getRole())) {
            return  ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }
        // 3、 redis中设置uuid = openid
        String token = UUID.randomUUID().toString();
        Integer expire = CookieConstant.EXPIRE;
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE,token),
                                          openid,expire, TimeUnit.SECONDS);
        // 4、 cookie里设置token = UUID
          CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);
        return ResultVOUtil.success();
    }
}
