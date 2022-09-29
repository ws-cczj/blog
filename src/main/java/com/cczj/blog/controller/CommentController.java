package com.cczj.blog.controller;

import com.cczj.blog.pojo.Comment;
import com.cczj.blog.pojo.User;
import com.cczj.blog.service.BlogService;
import com.cczj.blog.service.CommentService;
import com.cczj.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * Author: 捶捶自己
 * version: 1.0
 * Date: 2022/09/26/15:36
 * Description:
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;
    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long id = comment.getBlog().getId();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String avatar_admin = user.getAvatar();
            if (avatar_admin == null) {
                comment.setAvatar(avatar);
            }
            comment.setNickname(user.getNickname());
            comment.setAvatar(avatar_admin);
        } else {
            comment.setAvatar(avatar);
        }
        comment.setBlog(blogService.getBlog(id));
        commentService.saveComment(comment);
        return "redirect:/comments/" + id;
    }
}
