package com.example.blog.service.impl;

import com.example.blog.entity.Tag;
import com.example.blog.dao.TagMapper;
import com.example.blog.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}
