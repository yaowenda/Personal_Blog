package com.example.blog.dao;

import com.example.blog.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yaowenda
 * @since 2025-02-11
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
