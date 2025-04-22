package com.bug3198125224.service;

import com.bug3198125224.entity.Project;

import java.util.List;

/**
 * 项目服务接口
 */
public interface ProjectService {
    
    /**
     * 获取项目信息
     * @param id 项目ID
     * @return 项目对象，如果不存在返回null
     */
    Project getProjectById(int id);
    
    /**
     * 获取所有项目
     * @return 项目列表
     */
    List<Project> getAllProjects();
    
    /**
     * 获取用户负责的项目
     * @param userId 用户ID
     * @return 项目列表
     */
    List<Project> getProjectsByUserId(int userId);
    
    /**
     * 添加项目
     * @param project 项目对象
     * @return 是否成功
     */
    boolean addProject(Project project);
    
    /**
     * 更新项目
     * @param project 项目对象
     * @return 是否成功
     */
    boolean updateProject(Project project);
    
    /**
     * 更新项目状态
     * @param id 项目ID
     * @param status 项目状态
     * @return 是否成功
     */
    boolean updateProjectStatus(int id, int status);
    
    /**
     * 删除项目
     * @param id 项目ID
     * @return 是否成功
     */
    boolean deleteProject(int id);
} 