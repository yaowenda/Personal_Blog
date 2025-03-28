package com.example.blog.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.entity.Blog;
import com.example.blog.entity.Type;
import com.example.blog.service.IBlogService;
import com.example.blog.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {
    @Autowired
    private ITypeService typeService;

    @Autowired
    private IBlogService blogService;
    @GetMapping("/types/{id}")
    public String types(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "5") Integer size,
                        @PathVariable int id,
                        Model model) {
        List<Type> types = typeService.list();
        if (id == -1) { //从主页点进来的
            id = types.get(0).getId();
        }
        model.addAttribute("types", types);
        IPage<Blog> pageResult = blogService.pageByTypeId(id,page,size);
        // 填充每个博客的type信息
        List<Blog> blogs = pageResult.getRecords();
        for (Blog blog : blogs) {
            Type type = typeService.getType(blog.getTypeId());
            blog.setType(type);
        }
        model.addAttribute("page", pageResult);
        model.addAttribute("activeTypeId", id);
        return "types";
    }

}
