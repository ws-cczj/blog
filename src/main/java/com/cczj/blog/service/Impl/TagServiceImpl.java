package com.cczj.blog.service.Impl;

import com.cczj.blog.dao.TagRepository;
import com.cczj.blog.pojo.Tag;
import com.cczj.blog.service.TagService;
import com.cczj.blog.util.TmpTransformTools;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: 捶捶自己
 * version: 1.0
 * Date: 2022/09/24/11:17
 * Description:
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag Tag) {
        return tagRepository.save(Tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.getReferenceById(id);
    }

    @Transactional
    @Override
    public boolean getTagByName(String name) {
        return tagRepository.getTagByName(name) != null;
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Transactional
    @Override
    public List<Tag> listTag(String ids) {
        List<Tag> tags = tagRepository.findAll();
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids)) {
            String[] idArray = ids.split(",");
            for (String s : idArray) {
                if (TmpTransformTools.judgeTagIsExist(s, tags)) {
                    list.add(Long.valueOf(s));
                } else {
                    Tag tag = new Tag();
                    tag.setName(s);
                    tagRepository.save(tag);
                    list.add(tagRepository.getTagByName(s).getId());
                }
            }
        }
        return tagRepository.findAllById(list);
    }

    @Transactional
    @Override
    public List<Tag> listTagTop(Integer size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "blogs.size"));
        return tagRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag resultTag = tagRepository.getReferenceById(id);
        BeanUtils.copyProperties(tag, resultTag);
        return tagRepository.save(resultTag);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
