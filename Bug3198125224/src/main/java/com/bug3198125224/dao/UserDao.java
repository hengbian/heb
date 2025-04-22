package com.bug3198125224.dao;

import com.bug3198125224.entity.User;

import java.util.List;

/**
 * 用户DAO接口
 */
public interface UserDao {
    
    /**
     * 通过用户名和密码查询用户
     * @param username 用户名
     * @param password 密码
     * @return 用户对象，如果不存在返回null
     */
    User findByUsernameAndPassword(String username, String password);
    
    /**
     * 通过ID查询用户
     * @param id 用户ID
     * @return 用户对象，如果不存在返回null
     */
    User findById(int id);
    
    /**
     * 查询所有用户
     * @return 用户列表
     */
    List<User> findAll();
    
    /**
     * 添加用户
     * @param user 用户对象
     * @return 影响的行数
     */
    int add(User user);
    
    /**
     * 更新用户
     * @param user 用户对象
     * @return 影响的行数
     */
    int update(User user);
    
    /**
     * 删除用户
     * @param id 用户ID
     * @return 影响的行数
     */
    int delete(int id);
} 