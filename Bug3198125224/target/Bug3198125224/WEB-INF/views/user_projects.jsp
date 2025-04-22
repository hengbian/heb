<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的项目 - Bug3198125224</title>
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
        .btn-info {
            background-color: #2196F3;
        }
        .btn-info:hover {
            background-color: #0b7dda;
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
        .status-form {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .status-form select {
            padding: 5px;
            border-radius: 4px;
            border: 1px solid #ddd;
        }
        .status-form button {
            padding: 5px 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .status-form button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="user-info">
            欢迎, ${user.username} | 
            <a href="${pageContext.request.contextPath}/user/logout">退出登录</a>
        </div>
        
        <h1>我的项目</h1>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>项目名称</th>
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
                        <td>${project.statusStr}</td>
                        <td><fmt:formatDate value="${project.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td><fmt:formatDate value="${project.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>
                            <form class="status-form" action="${pageContext.request.contextPath}/project/updateStatus" method="post">
                                <input type="hidden" name="id" value="${project.id}">
                                <select name="status">
                                    <option value="0" ${project.status == 0 ? 'selected' : ''}>立项</option>
                                    <option value="1" ${project.status == 1 ? 'selected' : ''}>进行中</option>
                                    <option value="2" ${project.status == 2 ? 'selected' : ''}>结项</option>
                                </select>
                                <button type="submit">更新状态</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html> 