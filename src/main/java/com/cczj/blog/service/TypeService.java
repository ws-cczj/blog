package com.cczj.blog.service;

import com.cczj.blog.pojo.Type;
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
public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    boolean getTypeByName(String name);

    Page<Type> listType(Pageable pageable);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);
    Type updateType(Long id, Type type);

    void deleteType(Long id);
}
