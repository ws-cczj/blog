package com.cczj.blog.service;

import com.cczj.blog.pojo.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: 捶捶自己
 * version: 1.0
 * Date: 2022/09/23/23:44
 * Description:
 */
public interface UserService {
    User checkUser(String username, String password);
    boolean checkUserIsExist(Long id);

    User getUserById(Long id);
}
