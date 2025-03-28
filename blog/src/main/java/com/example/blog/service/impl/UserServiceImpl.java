package com.example.blog.service.impl;

import com.example.blog.entity.User;
import com.example.blog.dao.UserMapper;
import com.example.blog.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yaowenda
 * @since 2025-02-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;


    public User checkUser(String username, String password) {
        System.out.println("username"+username);
        System.out.println("password"+password);

        User user = userMapper.findByUsernameAndPassword(username, password);
        return user;
    }
}
