package com.bug3198125224.service;

import com.bug3198125224.entity.User;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 用户对象，如果登录失败返回null
     */
    User login(String username, String password);
    
    /**
     * 获取用户信息
     * @param id 用户ID
     * @return 用户对象，如果不存在返回null
     */
    User getUserById(int id);
    
    /**
     * 获取所有用户
     * @return 用户列表
     */
    List<User> getAllUsers();
    
    /**
     * 添加用户
     * @param user 用户对象
     * @return 是否成功
     */
    boolean addUser(User user);
    
    /**
     * 更新用户
     * @param user 用户对象
     * @return 是否成功
     */
    boolean updateUser(User user);
    
    /**
     * 删除用户
     * @param id 用户ID
     * @return 是否成功
     */
    boolean deleteUser(int id);
} 