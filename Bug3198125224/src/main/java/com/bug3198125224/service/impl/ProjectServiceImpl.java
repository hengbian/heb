package com.bug3198125224.service.impl;

import com.bug3198125224.dao.ProjectDao;
import com.bug3198125224.dao.impl.ProjectDaoImpl;
import com.bug3198125224.entity.Project;
import com.bug3198125224.service.ProjectService;

import java.util.List;

/**
 * 项目服务实现类
 */
public class ProjectServiceImpl implements ProjectService {
    
    private final ProjectDao projectDao = new ProjectDaoImpl();
    
    @Override
    public Project getProjectById(int id) {
        return projectDao.findById(id);
    }
    
    @Override
    public List<Project> getAllProjects() {
        return projectDao.findAll();
    }
    
    @Override
    public List<Project> getProjectsByUserId(int userId) {
        return projectDao.findByUserId(userId);
    }
    
    @Override
    public boolean addProject(Project project) {
        return projectDao.add(project) > 0;
    }
    
    @Override
    public boolean updateProject(Project project) {
        return projectDao.update(project) > 0;
    }
    
    @Override
    public boolean updateProjectStatus(int id, int status) {
        return projectDao.updateStatus(id, status) > 0;
    }
    
    @Override
    public boolean deleteProject(int id) {
        return projectDao.delete(id) > 0;
    }
} 