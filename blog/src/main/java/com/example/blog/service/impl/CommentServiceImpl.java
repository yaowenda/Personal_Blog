package com.example.blog.service.impl;

import com.example.blog.entity.Comment;
import com.example.blog.dao.CommentMapper;
import com.example.blog.service.ICommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
