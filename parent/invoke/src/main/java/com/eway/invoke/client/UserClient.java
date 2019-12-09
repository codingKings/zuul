package com.eway.invoke.client;
/**
* @author 程龙
* @version 创建时间：2019年12月6日 上午10:57:58
* @ClassName 类名称：接口
* @Description 类描述：告诉feig请求的方式，请求的路径，请求的参数，返回值
*/

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eway.invoke.pojo.User;

@FeignClient("server-service")
public interface UserClient {
    @GetMapping("user/{id}")
    User queryById4(@PathVariable("id") Long id) ;
}
