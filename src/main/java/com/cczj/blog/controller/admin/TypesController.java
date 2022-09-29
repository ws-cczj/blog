package com.cczj.blog.controller.admin;

import com.cczj.blog.pojo.Type;
import com.cczj.blog.service.TypeService;
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
 * Date: 2022/09/24/11:38
 * Description:
 */
@Controller
@RequestMapping("/admin")
public class TypesController {
    private static final String LIST = "admin/types";
    private static final String INPUT = "admin/types-input";
    private static final String REDIRECT_LIST = "redirect:/admin/types";
    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 5, sort = {"id"},
            direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        // -- 将查询到的数据进行返回
        model.addAttribute("page", typeService.listType(pageable));
        return LIST;
    }

    // -- 新增分类
    @PostMapping("/types")
    public String save(Type type, RedirectAttributes attributes, Model model) {
        if (type.getName() == null) {
            model.addAttribute("msg", "该分类名称不能为空！");
            return INPUT;
        }
        if (typeService.getTypeByName(type.getName())) {
            model.addAttribute("msg", "该分类名称已经存在！");
            return INPUT;
        }
        Type t = typeService.saveType(type);
        if (t == null) {
            attributes.addFlashAttribute("msg", "新增失败!!");
        } else {
            attributes.addFlashAttribute("msg", "新增成功~~");
        }
        return REDIRECT_LIST;
    }

    // -- 修改分类
    @PostMapping("/types/{id}")
    public String update(@PathVariable Long id, Type type, RedirectAttributes attributes, Model model) {
        if (type.getName() == null) {
            model.addAttribute("msg", "该分类名称不能为空！");
            return INPUT;
        }
        if (typeService.getTypeByName(type.getName())) {
            model.addAttribute("msg", "该分类名称已经存在！");
            return INPUT;
        }
        Type t = typeService.updateType(id, type);
        if (t == null) {
            attributes.addFlashAttribute("msg", "修改失败!!");
        } else {
            attributes.addFlashAttribute("msg", "修改成功~~");
        }
        return REDIRECT_LIST;
    }


    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return INPUT;
    }

    // -- types 页面编辑功能
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return INPUT;
    }

    // -- types 页面删除功能
    @GetMapping("/types/{id}/delete")
    public String deleteInput(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("msg", "删除成功~~");
        return REDIRECT_LIST;
    }
}
