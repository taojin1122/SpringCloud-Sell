package com.reder.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.reder.constant.RedisConstant;
import com.reder.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 *  权限拦截 (区分买家和卖家）, 买家
 */
@Component
public class AuthBuyerFilter extends ZuulFilter {
    private static final String BUYER_URI = "order/order/create";
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    /**
     * 是否应该拦截
     * @return  返回true为拦截
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if (BUYER_URI.equals(request.getRequestURI())) {
            return true;
        }
        return false ;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        /**   依靠买家和卖家的特征，
         *   /order/create 只能买家访问（cookie 里有openid）
         *
         */
            Cookie cookie = CookieUtil.get(request,"openid");
             if (cookie == null || StringUtils.isEmpty(cookie.getValue())) {
                 requestContext.setSendZuulResponse(false);
                 requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
             }
        return null;
    }
}
