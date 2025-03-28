package com.example.blog.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.entity.Type;
import com.example.blog.service.ITypeService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
public class TypeController {
    @Autowired
    private ITypeService typeService;
    //分页查询
    @GetMapping("/types")
    public String types(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model) {
        // 确保页码不小于1
        page = Math.max(1, page);
        Page<Type> typePage = typeService.listType(page, size);
        if (typePage == null) {
            typePage = new Page<>();  // 确保返回空的分页对象而不是 null
        }
        model.addAttribute("page", typePage);
        return "/admin/types";
    }
    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());  // 添加这行
        return "/admin/type-pub";
    }
    @GetMapping("types/{id}/input")
    public String editInput(@PathVariable int id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "/admin/type-pub";
    }

    @PostMapping("/types")
    public String post(@Validated Type type, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "redirect:/admin/types/input";  // 如果有错误，返回表单页面
        }
        List<Type> type1 = typeService.getTypeByName(type.getName());
        if(type1 != null && !type1.isEmpty()) {
//            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }
        Integer t = typeService.saveType(type);
        if (t == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String post(@Validated Type type, BindingResult result, @PathVariable  int id, RedirectAttributes attributes) {

        List<Type> type1 = typeService.getTypeByName(type.getName());
        if(type1 != null && !type1.isEmpty()) {
//            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }
        Integer t = typeService.updateType(id, type);
        if (t == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }


}
