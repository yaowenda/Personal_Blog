package com.example.blog.dao;

import com.example.blog.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yaowenda
 * @since 2025-02-11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}
