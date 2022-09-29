package com.cczj.blog.controller.admin;

import com.cczj.blog.pojo.User;
import com.cczj.blog.service.UserService;
import com.cczj.blog.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * Author: 捶捶自己
 * version: 1.0
 * Date: 2022/09/23/23:52
 * Description:
 */
@Controller
@RequestMapping("/admin")
public class LoginController {
    private static final String REDIRECT_INDEX = "redirect:/";
    private static final String REDIRECT_LIST = "redirect:/admin";
    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "admin/index";
        }
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session, RedirectAttributes attributes) throws Exception {
        User user = userService.checkUser(username, Md5Util.encodeByMd5(password));
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return REDIRECT_INDEX;
        } else {
            attributes.addFlashAttribute("msg", "用户名或密码错误!");
            return REDIRECT_LIST;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return REDIRECT_LIST;
    }
}
