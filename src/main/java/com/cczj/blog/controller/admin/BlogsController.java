package com.cczj.blog.controller.admin;

import com.cczj.blog.pojo.Blog;
import com.cczj.blog.pojo.User;
import com.cczj.blog.service.BlogService;
import com.cczj.blog.service.TagService;
import com.cczj.blog.service.TypeService;
import com.cczj.blog.util.TmpTransformTools;
import com.cczj.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * Author: 捶捶自己
 * version: 1.0
 * Date: 2022/09/24/10:37
 * Description:
 */
@Controller
@RequestMapping("/admin")
public class BlogsController {
    private static final String LIST = "admin/blogs";
    private static final String INPUT = "admin/blogs-input";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    // -- content is showed in the blogs
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 5, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST;
    }

    // -- searchContent is displayed to blogs in the search after
    @PostMapping("/blogs/search")
    public String serach(@PageableDefault(size = 5, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: bloglist";
    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        Blog blog = blogService.getBlog(id);
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("tagIds_rtn", TmpTransformTools.tagsToIds(blog.getTags()));
        model.addAttribute("blog", blog);
        return INPUT;
    }

    // -- 发布或保存博客内容
    @PostMapping("/blogs")
    public String publish(Blog blog, RedirectAttributes attributes
            , HttpSession session, @RequestParam("tagIds")String tagIds) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(tagIds));
        Blog b = blogService.saveBlog(blog);
        if (b == null) {
            attributes.addFlashAttribute("msg", "操作失败!!");
        } else {
            attributes.addFlashAttribute("msg", "操作成功~~");
        }
        return REDIRECT_LIST;
    }

    // -- 删除博客
    @GetMapping("/blogs/{id}/delete")
    public String delete(Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("msg","删除成功~");
        return REDIRECT_LIST;
    }
}
