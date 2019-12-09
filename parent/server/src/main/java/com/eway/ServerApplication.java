package com.eway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import tk.mybatis.spring.annotation.MapperScan;

/**
* @author 程龙
* @version 创建时间：2019年12月4日 下午7:43:53
* @ClassName 类名称：
* @Description 类描述：
*/
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.eway.user.mapper")
public class ServerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class,args);  //这个args的作用是传入不同的propertis配置文件启动整个项目
    }
    
}
