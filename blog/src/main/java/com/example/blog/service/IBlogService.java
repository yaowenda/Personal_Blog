package com.example.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.entity.Type;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yaowenda
 * @since 2025-02-11
 */
public interface IBlogService extends IService<Blog> {

    Blog createFromMarkdown(MultipartFile file, Blog blog) throws IOException;

    //按分类查询
    IPage<Blog> pageByTypeId(int typeId, int page, int size);

    Blog getAndConvert(int id);

    //    根据id获取blog
    Blog findone(int id);


    //    分页
    Page<Blog> listBlog(Integer page, Integer size, Blog blog);

    //    保存
    Integer saveBlog(Blog blog);

    //    更新
    Integer updateBlog(int id, Blog blog);

    //    删除
    void deleteBlog(int id);
}
