package com.cczj.blog.dao;

import com.cczj.blog.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: 捶捶自己
 * version: 1.0
 * Date: 2022/09/24/11:18
 * Description:
 */
public interface TypeRepository extends JpaRepository<Type,Long> {

    Type getTypeByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
