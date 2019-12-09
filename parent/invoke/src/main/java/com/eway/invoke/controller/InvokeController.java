package com.eway.invoke.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.eway.invoke.client.UserClient;
import com.eway.invoke.pojo.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;


/**
* @author 程龙
* @version 创建时间：2019年12月4日 下午8:39:56
* @ClassName 类名称：
* @Description 类描述：
*/
@RestController
@RequestMapping("invoke")
public class InvokeController {
    @Autowired
    private RestTemplate restTemplate;
    
//    @Autowired
//    private DiscoveryClient discoveryClient;
    
    /*
     * //第三种种方式用到的
     * 
     * @Autowired
     * private RibbonLoadBalancerClient client;
     */
    
    @GetMapping("{id}")
    public User queryById(@PathVariable("id") Long id) {
        
        //第四种方式：最终方式，在启动类中的restTemplate加一个注解，@LoadBalanced
        String url = "http://server-service/user/"+id;   
        User user = restTemplate.getForObject(url, User.class);
        return user;
        
        
        /*
         * //第三种方式
         * //负载均衡器，默认是轮询
         * ServiceInstance instance = client.choose("server-service");
         * String url = "http://"+instance.getHost()+":"+instance.getPort()+"/user/"+id;
         * User user = restTemplate.getForObject(url, User.class);
         * return user;
         */
         
        
        /*
         * //第二种方式：
         * //根据服务id获取服务实例
         * List<ServiceInstance> instances = discoveryClient.getInstances("server-service");
         * //从实例中取出ip和端口
         * ServiceInstance instance = instances.get(0);
         * String url = "http://"+instance.getHost()+":"+instance.getPort()+"/user/"+id;
         * User user = restTemplate.getForObject(url, User.class);
         * return user;
         */
        
        /*
         * //第一种方式：
         * String url = "http://localhost:8095/user/"+id;
         * User user = restTemplate.getForObject(url, User.class);
         * return user;
         */
    }
    
    /**
     * 服务降级处理demo
     * @param id
     * @return
     */
    @GetMapping("hystrx/{id}")
    @HystrixCommand(fallbackMethod = "queryByIdFallback")//此注解开启服务降级处理，如果调用服务超时，返回失败信息的方法必须与对应成功的方法的返回值一致，参数列表也一样
    public String queryByIds(@PathVariable("id") Long id) {
        
        //第四种方式：最终方式，在启动类中的restTemplate加一个注解，@LoadBalanced
        String url = "http://server-service/user/"+id;   
        String user = restTemplate.getForObject(url, String.class);
        return user;
    }
    
    public String queryByIdFallback(Long id) {
        return "服务太拥挤了";
    }
    
    //手动实现一个熔断器
    @GetMapping("circuitBreaker/{id}")
    @HystrixCommand(
        commandProperties= {
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"),
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="60")
        }
    )
    public String queryById3(@PathVariable("id") Long id) {
        if(id % 2 == 0) {
            throw new RuntimeException("服务异常。。。");
            
        }
        String url = "http://server-service/user/"+id;
        String user = restTemplate.getForObject(url, String.class);
        return user;
    }
    
//    @Autowired
//    private UserClient userClient;
//    
//    @GetMapping("{id}")
//    public User queryById4(@PathVariable("id") Long id) {
//        return userClient.queryById4(id);
//    }
    
}
