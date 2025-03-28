package com.example.blog.service.impl;

import com.example.blog.NotFoundException;
import com.example.blog.dao.BlogMapper;
import com.example.blog.entity.Type;
import com.example.blog.dao.TypeMapper;
import com.example.blog.service.ITypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yaowenda
 * @since 2025-02-11
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements ITypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<Map<String,Integer>> listTopType() {
//        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByDesc("blogs_count").last("limit " + size);
        return blogMapper.getTopTypes();
    }

    @Override
    public Integer saveType(Type type) {
        return typeMapper.save(type);
    }

    @Override
    public Type getType(int id) {
        return typeMapper.findone(id);
    }

    @Override
    public Page<Type> listType(int page, int size) {
        Page<Type> pageRequest = new Page<>(page, size);
        Page<Type> resultPage = typeMapper.selectPage(pageRequest, null);
        return resultPage != null ? resultPage : new Page<>();
    }

    @Override
    public Integer updateType(int id, Type type) {
        Type t = typeMapper.findone(id);
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        type.setId(id);
        return typeMapper.update(type);
    }

    @Override
    public void deleteType(int id) {
        typeMapper.delete(id);
    }

    @Override
    public List<Type> getTypeByName(String name){
        return typeMapper.getTypeByName(name);
    }
}
