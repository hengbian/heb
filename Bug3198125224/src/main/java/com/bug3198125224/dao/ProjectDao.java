package com.bug3198125224.dao;

import com.bug3198125224.entity.Project;

import java.util.List;

/**
 * 项目DAO接口
 */
public interface ProjectDao {
    
    /**
     * 通过ID查询项目
     * @param id 项目ID
     * @return 项目对象，如果不存在返回null
     */
    Project findById(int id);
    
    /**
     * 查询所有项目
     * @return 项目列表
     */
    List<Project> findAll();
    
    /**
     * 查询用户负责的项目
     * @param userId 用户ID
     * @return 项目列表
     */
    List<Project> findByUserId(int userId);
    
    /**
     * 添加项目
     * @param project 项目对象
     * @return 影响的行数
     */
    int add(Project project);
    
    /**
     * 更新项目
     * @param project 项目对象
     * @return 影响的行数
     */
    int update(Project project);
    
    /**
     * 更新项目状态
     * @param id 项目ID
     * @param status 项目状态
     * @return 影响的行数
     */
    int updateStatus(int id, int status);
    
    /**
     * 删除项目
     * @param id 项目ID
     * @return 影响的行数
     */
    int delete(int id);
} 