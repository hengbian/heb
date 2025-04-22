<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>项目详情 - ${project.name} - Bug3198125224</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #333;
            margin-top: 0;
        }
        .project-info {
            margin-bottom: 20px;
        }
        .project-info p {
            margin: 8px 0;
        }
        .project-info label {
            font-weight: bold;
            display: inline-block;
            width: 100px;
        }
        .description {
            background-color: #f9f9f9;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
            white-space: pre-line;
        }
        .btn {
            display: inline-block;
            padding: 8px 16px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-right: 10px;
        }
        .btn:hover {
            background-color: #45a049;
        }
        .btn-warning {
            background-color: #ff9800;
        }
        .btn-warning:hover {
            background-color: #e68a00;
        }
        .user-info {
            text-align: right;
            margin-bottom: 20px;
        }
        .action-buttons {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="user-info">
            欢迎, ${user.username} | 
            <a href="${pageContext.request.contextPath}/user/logout">退出登录</a>
        </div>
        
        <h1>项目详情</h1>
        
        <div class="project-info">
            <p><label>项目ID：</label> ${project.id}</p>
            <p><label>项目名称：</label> ${project.name}</p>
            <p><label>负责人：</label> ${project.username}</p>
            <p><label>项目状态：</label> ${project.statusStr}</p>
            <p><label>创建时间：</label> <fmt:formatDate value="${project.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
            <p><label>更新时间：</label> <fmt:formatDate value="${project.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
        </div>
        
        <h3>项目描述：</h3>
        <div class="description">
            ${project.description}
        </div>
        
        <div class="action-buttons">
            <c:if test="${user.isAdmin()}">
                <a href="${pageContext.request.contextPath}/project/edit?id=${project.id}" class="btn btn-warning">编辑项目</a>
            </c:if>
            
            <c:choose>
                <c:when test="${user.isAdmin()}">
                    <a href="${pageContext.request.contextPath}/project/admin" class="btn">返回列表</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/project/user" class="btn">返回列表</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html> 