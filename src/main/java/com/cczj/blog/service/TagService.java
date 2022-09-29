package com.cczj.blog.service;

import com.cczj.blog.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: 捶捶自己
 * version: 1.0
 * Date: 2022/09/24/11:17
 * Description:
 */
public interface TagService {
    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    boolean getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer size);

    Tag updateTag(Long id, Tag type);

    void deleteTag(Long id);
}
