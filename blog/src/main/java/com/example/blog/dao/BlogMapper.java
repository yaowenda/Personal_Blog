package com.example.blog.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.blog.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yaowenda
 * @since 2025-02-11
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    int save(Blog blog);

    Blog findone(Integer id);

    int updateBlog(Blog blog);

    int delete(Integer id);

    List<Map<String, Integer>> getTopTypes();



}
