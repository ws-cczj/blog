package com.cczj.blog.controller.admin;

import com.cczj.blog.pojo.Tag;
import com.cczj.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created with IntelliJ IDEA.
 * Author: 捶捶自己
 * version: 1.0
 * Date: 2022/09/24/17:02
 * Description:
 */
@Controller
@RequestMapping("/admin")
public class TagsController {
    private static final String LIST = "admin/tags";
    private static final String INPUT = "admin/tags-input";
    private static final String REDIRECT_LIST = "redirect:/admin/tags";
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 5, sort = {"id"},
            direction = Sort.Direction.DESC) Pageable pageable,
                       Model model) {
        // -- 将查询到的数据进行返回
        model.addAttribute("page", tagService.listTag(pageable));
        return LIST;
    }

    // -- 新增分类
    @PostMapping("/tags")
    public String save(Tag tag, RedirectAttributes attributes, Model model) {
        if (tag.getName() == null) {
            model.addAttribute("msg", "该标签名称不能为空！");
            return INPUT;
        }
        if (tagService.getTagByName(tag.getName())) {
            model.addAttribute("msg", "该标签名称已经存在！");
            return INPUT;
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("msg", "新增失败!!");
        } else {
            attributes.addFlashAttribute("msg", "新增成功~~");
        }
        return REDIRECT_LIST;
    }

    // -- 修改分类
    @PostMapping("/tags/{id}")
    public String update(@PathVariable Long id, Tag tag, RedirectAttributes attributes, Model model) {
        if (tag.getName() == null) {
            model.addAttribute("msg", "该标签名称不能为空！");
            return INPUT;
        }
        if (tagService.getTagByName(tag.getName())) {
            model.addAttribute("msg", "该标签名称已经存在！");
            return INPUT;
        }
        Tag t = tagService.updateTag(id, tag);
        if (t == null) {
            attributes.addFlashAttribute("msg", "修改失败!!");
        } else {
            attributes.addFlashAttribute("msg", "修改成功~~");
        }
        return REDIRECT_LIST;
    }


    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return INPUT;
    }

    // -- Tags 页面编辑功能
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return INPUT;
    }

    // -- Tags 页面删除功能
    @GetMapping("/tags/{id}/delete")
    public String deleteInput(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("msg", "删除成功~~");
        return REDIRECT_LIST;
    }
}
