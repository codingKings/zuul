package com.eway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
* @author 程龙
* @version 创建时间：2019年12月6日 下午1:39:45
* @ClassName 类名称：
* @Description 类描述：
*/
//@EnableZuulServer 这个注解的功能单一，很多的过滤器不生效，所以使用proxy代理，而不使用server,proxy的功能很完善
@EnableZuulProxy
@SpringBootApplication
public class GatewayApplication {
    
    public static void main(String[] args) {

        SpringApplication.run(GatewayApplication.class, args);
    }
    
    
    
}
