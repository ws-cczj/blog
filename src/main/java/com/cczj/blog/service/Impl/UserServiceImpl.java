package com.cczj.blog.service.Impl;

import com.cczj.blog.dao.UserRepository;
import com.cczj.blog.pojo.User;
import com.cczj.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Author: 捶捶自己
 * version: 1.0
 * Date: 2022/09/23/23:46
 * Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public boolean checkUserIsExist(Long id) {
        for (User user : userRepository.findAll()) {
            if (Objects.equals(id, user.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getReferenceById(id);
    }
}
