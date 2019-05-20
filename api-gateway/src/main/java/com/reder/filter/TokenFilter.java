package com.reder.filter;

        import com.netflix.zuul.ZuulFilter;
        import com.netflix.zuul.context.RequestContext;
        import com.netflix.zuul.exception.ZuulException;
        import org.springframework.http.HttpStatus;
        import org.springframework.stereotype.Component;
        import org.springframework.util.StringUtils;

        import javax.servlet.http.HttpServletRequest;

        import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
        import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class TokenFilter  extends ZuulFilter{
    /**
     * 过滤类型设置
     * @return
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤顺序设置
     * @return
     */
    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    /**
     *  返回true
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("进来没.................");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        // 这里从url参数里获取，也可以从cookie，header获取
        String token =  request.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            // 返回沒有权限
        /*    requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value())*/;
        }
        return null;
    }
}
