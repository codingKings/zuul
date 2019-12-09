package com.eway.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
* @author 程龙
* @version 创建时间：2019年12月6日 下午4:01:53
* @ClassName 类名称：
* @Description 类描述：业务场景，判断用户的请求中有没有access-token参数，有就放行，没有就拦截
*/
@Component
public class LoginFilter extends ZuulFilter{

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取请求上下文,RequestContext的作用域，从请求到达zuul到路由结束把响应信息返回给浏览器
        RequestContext ctx = RequestContext.getCurrentContext();
        //获取request对象
        HttpServletRequest request = ctx.getRequest();
        //获取请求的参数 access-token
        String token = request.getParameter("access-token");
        //判断是否存在，
        if(StringUtils.isBlank(token)) {
            //不存在，未登录，则拦截
           ctx.setSendZuulResponse(false);
           //返回403
           ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        }
        return null;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER-1;//请求处理在pre之前执行
    }
    
}
