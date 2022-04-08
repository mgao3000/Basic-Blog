package com.devmountain.blog.service;

import com.devmountain.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    boolean authenticate(String username, String password);
    List<User> findAll();
    Page<User> findAll(Pageable pageable);
    User findByUserName(String userName);
    User findById(Long id);
    User create(User user);
    User edit(User user);
    void deleteById(Long id);
}
