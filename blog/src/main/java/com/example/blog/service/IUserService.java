package com.example.blog.service;

import com.example.blog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yaowenda
 * @since 2025-02-11
 */
public interface IUserService extends IService<User> {

    User checkUser(String username, String password);
}
