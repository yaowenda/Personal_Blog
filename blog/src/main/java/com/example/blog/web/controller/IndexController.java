package com.example.blog.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.NotFoundException;
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
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ITypeService typeService;


    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "5") Integer size,
                        Model model) {
        Page<Blog> pageResult = blogService.page(new Page<>(page, size));
        // 填充每个博客的type信息
        List<Blog> blogs = pageResult.getRecords();
        for (Blog blog : blogs) {
            Type type = typeService.getType(blog.getTypeId());
            blog.setType(type);
        }
        model.addAttribute("page", pageResult);
        List<Map<String,Integer>> types = typeService.listTopType();
        model.addAttribute("types", types);
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable int id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

}
