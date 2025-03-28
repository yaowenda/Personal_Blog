package com.example.blog.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.entity.Blog;
import com.example.blog.entity.Type;
import com.example.blog.entity.User;
import com.example.blog.service.IBlogService;
import com.example.blog.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yaowenda
 * @since 2025-02-11
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private IBlogService blogService;
    @Autowired
    private ITypeService typeService;

    @GetMapping("/blogs")
    public String blogs(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size, Model model) {
        // 使用传入的页码参数
        Page<Blog> pageResult = blogService.page(new Page<>(page, size));

        List<Blog> records = pageResult.getRecords();
        if (records == null || records.isEmpty()) {
            records = new ArrayList<>();
        }
        for (Blog blog : records) {
            Type type = typeService.getType(blog.getTypeId());
            blog.setType(type);
        }

        model.addAttribute("page", pageResult);
        model.addAttribute("types", typeService.list());
        return "admin/admin";
    }

    // 处理搜索表单提交
    @PostMapping("/blogs")
    public String search(@ModelAttribute Blog blog,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer size,
                         Model model) {
        // checkbox未选中时不会提交值，需要特殊处理
        if (blog.getRecommened() == null) {
            blog.setRecommened(false);
        }

        Page<Blog> pageResult = blogService.listBlog(page, size, blog);
        // 手动填充分类信息
        List<Blog> records = pageResult.getRecords();
        for (Blog b : records) {
            Type type = typeService.getById(b.getTypeId());
            b.setType(type);
        }

        model.addAttribute("page", pageResult);
        model.addAttribute("types", typeService.list());
        // 保留搜索条件
        model.addAttribute("blog", blog);

        return "admin/admin";  // 直接返回视图，不使用重定向
    }

    //博客新增_手写
    @GetMapping("/blogs/input")
    public String input(Model model) {
        List<Type> types = typeService.list();
        if (types == null || types.isEmpty()) {
            types = new ArrayList<>();
        }
        model.addAttribute("types", types);//初始化type
        model.addAttribute("blog", new Blog());
        return "admin/pub";
    }

    //博客新增_上传
    @GetMapping("/blogs/input/upload")
    public String inputUpload(Model model) {
        List<Type> types = typeService.list();
        if (types == null || types.isEmpty()) {
            types = new ArrayList<>();
        }
        model.addAttribute("types", types);//初始化type
        model.addAttribute("blog", new Blog());
        return "admin/upload";
    }

    //博客编辑
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable int id, Model model) {
        model.addAttribute("types", typeService.list());//初始化type

        model.addAttribute("blog", blogService.findone(id));

        return "admin/pub";
    }

    @PostMapping("/blogs/submit")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user != null) {
            blog.setUserId(user.getId());
        }
        blog.setType(typeService.getType(blog.getTypeId()));

        if(blog.getId() != null) { //说明是编辑
            Integer b = blogService.updateBlog(blog.getId(), blog);
            if (b == null) {
                attributes.addFlashAttribute("message", "操作失败");
            } else {
                attributes.addFlashAttribute("message", "操作成功");
            }
        } else { //新增
            Integer b = blogService.saveBlog(blog);
            if (b == null) {
                attributes.addFlashAttribute("message", "操作失败");
            } else {
                attributes.addFlashAttribute("message", "操作成功");
            }
        }
        return "redirect:/admin/blogs";
    }

    @PostMapping("/blogs/upload/submit")
    public String uploadSubmit(Blog blog, @RequestParam("file") MultipartFile file, RedirectAttributes attributes, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user != null) {
            blog.setUserId(user.getId());
        }
        blog.setType(typeService.getType(blog.getTypeId()));
        // 处理一下content内容 把file中的markdown转成html
        try {
            Blog article = blogService.createFromMarkdown(file, blog);
            Integer b = blogService.saveBlog(article);
            if (b != null) {
                attributes.addFlashAttribute("message", "操作成功");
            } else {
                attributes.addFlashAttribute("message", "操作失败");
            }
        } catch (IOException e) {
            attributes.addFlashAttribute("message", "文件读取失败");
            return "redirect:/admin/blogs/input/upload";
        }
        return "redirect:/admin/blogs";
    }

    //博客删除
    @GetMapping("/blogs/{id}/delete")
    public String deleteInput(@PathVariable int id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";

    }

}
