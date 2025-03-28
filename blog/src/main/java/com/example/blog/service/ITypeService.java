package com.example.blog.service;


import com.example.blog.entity.Type;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yaowenda
 * @since 2025-02-11
 */
public interface ITypeService extends IService<Type> {

    List<Map<String,Integer>> listTopType();

    Integer saveType(Type type);
    Type getType(int id);

    com.baomidou.mybatisplus.extension.plugins.pagination.Page<Type> listType(int page, int size);

    Integer updateType(int id, Type type);

    void deleteType(int id);

    List<Type> getTypeByName(String name);
}
