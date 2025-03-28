package com.example.blog.dao;


import com.example.blog.entity.Type;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yaowenda
 * @since 2025-02-11
 */
@Mapper
public interface TypeMapper extends BaseMapper<Type> {

    /**
     * 保存分类
     */
    int save(Type type);

    /**
     * 根据ID查询分类
     */
    Type findone(@Param("id") int id);

    Integer update(Type type);

    /**
     * 删除分类
     */
    void delete(@Param("id") int id);

    List<Type> getTypeByName(String name);

}
