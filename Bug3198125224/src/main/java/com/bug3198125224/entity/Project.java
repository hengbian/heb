package com.bug3198125224.entity;

import java.util.Date;

/**
 * 项目实体类
 */
public class Project {
    private int id;            // 项目ID
    private String name;       // 项目名称
    private String description; // 项目描述
    private int status;        // 项目状态：0-立项，1-进行中，2-结项
    private Date createTime;   // 创建时间
    private Date updateTime;   // 更新时间
    private int userId;        // 负责人ID
    private String username;   // 负责人用户名（非数据库字段，用于展示）

    public Project() {
    }

    public Project(int id, String name, String description, int status, Date createTime, Date updateTime, int userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getStatusStr() {
        switch (status) {
            case 0:
                return "立项";
            case 1:
                return "进行中";
            case 2:
                return "结项";
            default:
                return "未知";
        }
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }
} 