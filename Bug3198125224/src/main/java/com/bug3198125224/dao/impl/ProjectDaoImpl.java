package com.bug3198125224.dao.impl;

import com.bug3198125224.dao.ProjectDao;
import com.bug3198125224.entity.Project;
import com.bug3198125224.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目DAO实现类
 */
public class ProjectDaoImpl implements ProjectDao {

    @Override
    public Project findById(int id) {
        String sql = "SELECT p.*, u.username FROM project p LEFT JOIN user u ON p.user_id = u.id WHERE p.id = ?";
        Project project = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    project = extractProjectFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return project;
    }

    @Override
    public List<Project> findAll() {
        String sql = "SELECT p.*, u.username FROM project p LEFT JOIN user u ON p.user_id = u.id";
        List<Project> projects = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Project project = extractProjectFromResultSet(rs);
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return projects;
    }

    @Override
    public List<Project> findByUserId(int userId) {
        String sql = "SELECT p.*, u.username FROM project p LEFT JOIN user u ON p.user_id = u.id WHERE p.user_id = ?";
        List<Project> projects = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Project project = extractProjectFromResultSet(rs);
                    projects.add(project);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return projects;
    }

    @Override
    public int add(Project project) {
        String sql = "INSERT INTO project (name, description, status, create_time, update_time, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        int result = 0;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, project.getName());
            pstmt.setString(2, project.getDescription());
            pstmt.setInt(3, project.getStatus());
            
            // 如果创建时间和更新时间为空，则使用当前时间
            Timestamp now = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(4, project.getCreateTime() != null ? new Timestamp(project.getCreateTime().getTime()) : now);
            pstmt.setTimestamp(5, project.getUpdateTime() != null ? new Timestamp(project.getUpdateTime().getTime()) : now);
            pstmt.setInt(6, project.getUserId());
            
            result = pstmt.executeUpdate();
            
            // 获取生成的主键ID
            if (result > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        project.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }

    @Override
    public int update(Project project) {
        String sql = "UPDATE project SET name = ?, description = ?, status = ?, update_time = ?, user_id = ? WHERE id = ?";
        int result = 0;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, project.getName());
            pstmt.setString(2, project.getDescription());
            pstmt.setInt(3, project.getStatus());
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(5, project.getUserId());
            pstmt.setInt(6, project.getId());
            
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }

    @Override
    public int updateStatus(int id, int status) {
        String sql = "UPDATE project SET status = ?, update_time = ? WHERE id = ?";
        int result = 0;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, status);
            pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(3, id);
            
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM project WHERE id = ?";
        int result = 0;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * 从ResultSet中提取项目信息
     */
    private Project extractProjectFromResultSet(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setId(rs.getInt("id"));
        project.setName(rs.getString("name"));
        project.setDescription(rs.getString("description"));
        project.setStatus(rs.getInt("status"));
        project.setCreateTime(rs.getTimestamp("create_time"));
        project.setUpdateTime(rs.getTimestamp("update_time"));
        project.setUserId(rs.getInt("user_id"));
        project.setUsername(rs.getString("username"));
        return project;
    }
} 