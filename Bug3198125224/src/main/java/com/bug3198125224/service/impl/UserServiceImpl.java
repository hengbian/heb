package com.bug3198125224.service.impl;

import com.bug3198125224.dao.UserDao;
import com.bug3198125224.dao.impl.UserDaoImpl;
import com.bug3198125224.entity.User;
import com.bug3198125224.service.UserService;

import java.util.List;

/**
 * 用户服务实现类
 */
public class UserServiceImpl implements UserService {
    
    private final UserDao userDao = new UserDaoImpl();
    
    @Override
    public User login(String username, String password) {
        return userDao.findByUsernameAndPassword(username, password);
    }
    
    @Override
    public User getUserById(int id) {
        return userDao.findById(id);
    }
    
    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
    
    @Override
    public boolean addUser(User user) {
        // 检查用户名是否已存在
        List<User> allUsers = userDao.findAll();
        for (User existingUser : allUsers) {
            if (existingUser.getUsername().equals(user.getUsername())) {
                return false; // 用户名已存在
            }
        }
        return userDao.add(user) > 0;
    }
    
    @Override
    public boolean updateUser(User user) {
        // 检查用户名是否与其他用户重复
        List<User> allUsers = userDao.findAll();
        for (User existingUser : allUsers) {
            if (existingUser.getUsername().equals(user.getUsername()) && existingUser.getId() != user.getId()) {
                return false; // 用户名已被其他用户使用
            }
        }
        return userDao.update(user) > 0;
    }
    
    @Override
    public boolean deleteUser(int id) {
        return userDao.delete(id) > 0;
    }
} 