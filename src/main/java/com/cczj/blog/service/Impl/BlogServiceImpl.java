package com.cczj.blog.service.Impl;

import com.cczj.blog.dao.BlogRepository;
import com.cczj.blog.dao.CommentRepository;
import com.cczj.blog.pojo.Blog;
import com.cczj.blog.pojo.Type;
import com.cczj.blog.service.BlogService;
import com.cczj.blog.util.MarkdownUtils;
import com.cczj.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Author: 捶捶自己
 * version: 1.0
 * Date: 2022/09/24/18:01
 * Description:
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getReferenceById(id);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog b = new Blog();
        Blog blog = blogRepository.getReferenceById(id);
        BeanUtils.copyProperties(blog, b);
        b.setContent(MarkdownUtils.markdownToHtml(b.getContent()));
        blogRepository.updateViews(id);
        return b;
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.equal(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, Long id) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"), id);
            }
        }, pageable);
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery("%" + query + "%", pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "updateTime"));
        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        for (String year: years) {
            map.put(year,blogRepository.findBlogsByYear(year));
        }
        return map;
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            if (blog.getFlag() == null) {
                blog.setFlag("原创");
            }
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setCreateTime(blogRepository.getReferenceById(blog.getId()).getCreateTime());
            blog.setViews(blogRepository.getReferenceById(blog.getId()).getViews());
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog resultBlog = blogRepository.getReferenceById(id);
        BeanUtils.copyProperties(blog, resultBlog);
        return blogRepository.save(resultBlog);
    }

    @Transactional
    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        commentRepository.deleteByBlogIdAndParentCommentNotNull(id);
        commentRepository.deleteByBlogId(id);
        blogRepository.deleteById(id);
    }
}
