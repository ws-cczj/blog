package com.cczj.blog.util;

import com.cczj.blog.pojo.Tag;
import com.cczj.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: 捶捶自己
 * version: 1.0
 * Date: 2022/09/25/17:36
 * Description:
 */
public class TmpTransformTools {
    // -- 将数据库中博客标签的属性转换为字符串返回给前端
    public static String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuilder ids = new StringBuilder();
            boolean flag = false;
            for (Tag tag: tags) {
                if (flag) {
                    ids.append(",");
                }else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }
        return "";
    }

    // -- 将数据库中的标签与新加入标签进行比对
    public static boolean judgeTagIsExist(String tag, List<Tag> tags) {
        for (Tag t: tags) {
            if (tag.equals(t.getId().toString())) {
                return true;
            }
        }
        return false;
    }
}
