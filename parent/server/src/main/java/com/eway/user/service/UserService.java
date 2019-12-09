package com.eway.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eway.user.mapper.UserMapper;
import com.eway.user.pojo.User;

/**
* @author 程龙
* @version 创建时间：2019年12月4日 下午8:05:12
* @ClassName 类名称：
* @Description 类描述：
*/
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    
    public User queryById(Long id) {
      //以下线程休眠是为了配合测试超过Hystrix服务降级响应时长而做的,即服务响应时间超时
//        try{
//            Thread.sleep(2000);
//        } catch (InterruptedException e){
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return userMapper.selectByPrimaryKey(id);
    }
}
