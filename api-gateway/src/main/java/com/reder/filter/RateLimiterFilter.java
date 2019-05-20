package com.reder.filter;




import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import com.reder.exception.RateLimitException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 *  限流 ,采用的令牌桶算法
 */
public class RateLimiterFilter  extends ZuulFilter{
      // 每秒中放100个令牌
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // tryAcquire去取令牌
        // 判断没有取到令牌
        if (!RATE_LIMITER.tryAcquire()) {
           throw new RateLimitException();
        }
        return null;
    }
}
