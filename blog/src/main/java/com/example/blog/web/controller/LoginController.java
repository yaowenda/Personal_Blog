package com.example.blog.web.controller;

import com.example.blog.entity.User;
import com.example.blog.service.IUserService;
import com.example.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "admin/login";
    }



    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        // 处理已登录用户
        if (session.getAttribute("user") != null) {
            return "redirect:/admin/index";
        }


    User user = userService.checkUser(username, MD5Utils.md5(password));
    if (user != null) { //输入的是正确的
        user.setPassword(null); //不把密码传到前面去
        session.setAttribute("user",user); // 查到的用户对象放到session里面来
        return "redirect:/admin/index";

    } else {
        attributes.addFlashAttribute("msg","用户名或密码错误");
        return "redirect:/admin/login"; //注意用重定向
    }
    }

    @GetMapping("/index")  // 添加这个新方法
    public String index() {
        return "admin/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin/login";

    }

}
