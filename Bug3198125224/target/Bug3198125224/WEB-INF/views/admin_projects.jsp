<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>项目管理 - Bug3198125224</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #333;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
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
        .btn-danger {
            background-color: #f44336;
        }
        .btn-danger:hover {
            background-color: #d32f2f;
        }
        .btn-info {
            background-color: #2196F3;
        }
        .btn-info:hover {
            background-color: #0b7dda;
        }
        .btn-warning {
            background-color: #ff9800;
        }
        .btn-warning:hover {
            background-color: #e68a00;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        .user-info {
            text-align: right;
            margin-bottom: 20px;
        }
        .actions {
            display: flex;
            gap: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="user-info">
            欢迎, ${user.username} | 
            <a href="${pageContext.request.contextPath}/user/logout">退出登录</a>
        </div>
        
        <div class="header">
            <h1>项目管理</h1>
            <div>
                <a href="${pageContext.request.contextPath}/project/add" class="btn">添加项目</a>
                <a href="${pageContext.request.contextPath}/user/" class="btn btn-info">用户管理</a>
            </div>
        </div>
        
        <!-- 错误消息显示区域 -->
        <c:if test="${param.error == 'delete_failed'}">
            <div style="color: #f44336; background-color: #ffebee; padding: 10px; margin-bottom: 15px; border-radius: 4px;">
                错误：删除项目失败！
            </div>
        </c:if>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>项目名称</th>
                    <th>负责人</th>
                    <th>状态</th>
                    <th>创建时间</th>
                    <th>更新时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="project" items="${projects}">
                    <tr>
                        <td>${project.id}</td>
                        <td>${project.name}</td>
                        <td>${project.username}</td>
                        <td>${project.statusStr}</td>
                        <td><fmt:formatDate value="${project.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td><fmt:formatDate value="${project.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="actions">
                            <a href="${pageContext.request.contextPath}/project/detail?id=${project.id}" class="btn btn-info">详情</a>
                            <a href="${pageContext.request.contextPath}/project/edit?id=${project.id}" class="btn btn-warning">编辑</a>
                            <a href="javascript:void(0)" onclick="confirmDelete(${project.id})" class="btn btn-danger">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <script>
        function confirmDelete(id) {
            if (confirm('确定要删除此项目吗？')) {
                window.location.href = '${pageContext.request.contextPath}/project/delete?id=' + id;
            }
        }
    </script>
</body>
</html> 