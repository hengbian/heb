<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户管理 - Bug3198125224</title>
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
        .btn-warning {
            background-color: #ff9800;
        }
        .btn-warning:hover {
            background-color: #e68a00;
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
        .role-0 {
            background-color: #e3f2fd;
        }
        .role-1 {
            background-color: #e8f5e9;
        }
        .actions {
            display: flex;
            gap: 5px;
        }
        
        /* 用户表单样式 */
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.4);
        }
        .modal-content {
            background-color: #fefefe;
            margin: 10% auto;
            padding: 20px;
            border-radius: 5px;
            width: 400px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }
        .close:hover {
            color: black;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], input[type="password"], select {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
            border: 1px solid #ddd;
            border-radius: 4px;
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
            <h1>用户管理</h1>
            <div>
                <button class="btn" onclick="openUserForm()">添加用户</button>
                <a href="${pageContext.request.contextPath}/project/admin" class="btn btn-info">返回项目管理</a>
            </div>
        </div>
        
        <!-- 错误消息显示区域 -->
        <c:if test="${param.error == 'cannot_delete_admin'}">
            <div style="color: #f44336; background-color: #ffebee; padding: 10px; margin-bottom: 15px; border-radius: 4px;">
                错误：不能删除管理员用户！
            </div>
        </c:if>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>用户名</th>
                    <th>角色</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="userItem" items="${users}">
                    <tr class="role-${userItem.role}">
                        <td>${userItem.id}</td>
                        <td>${userItem.username}</td>
                        <td>${userItem.role == 1 ? '管理员' : '普通用户'}</td>
                        <td class="actions">
                            <button class="btn btn-warning" onclick="openEditForm(${userItem.id}, '${userItem.username}', ${userItem.role})">编辑</button>
                            <a href="javascript:void(0)" onclick="confirmDelete(${userItem.id})" class="btn btn-danger">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <!-- 用户表单模态框 -->
    <div id="userFormModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeUserForm()">&times;</span>
            <h2 id="formTitle">添加用户</h2>
            
            <form id="userForm" action="${pageContext.request.contextPath}/user/add" method="post">
                <input type="hidden" id="userId" name="id">
                
                <div class="form-group">
                    <label for="username">用户名：</label>
                    <input type="text" id="username" name="username" required>
                </div>
                
                <div class="form-group">
                    <label for="password">密码：</label>
                    <input type="password" id="password" name="password" required>
                </div>
                
                <div class="form-group">
                    <label for="role">角色：</label>
                    <select id="role" name="role" required>
                        <option value="0">普通用户</option>
                        <option value="1">管理员</option>
                    </select>
                </div>
                
                <button type="submit" class="btn">保存</button>
                <button type="button" class="btn btn-secondary" onclick="closeUserForm()">取消</button>
            </form>
        </div>
    </div>
    
    <script>
        function confirmDelete(id) {
            if (confirm('确定要删除此用户吗？')) {
                window.location.href = '${pageContext.request.contextPath}/user/delete?id=' + id;
            }
        }
        
        function openUserForm() {
            document.getElementById('formTitle').innerText = '添加用户';
            document.getElementById('userForm').action = '${pageContext.request.contextPath}/user/add';
            document.getElementById('userId').value = '';
            document.getElementById('username').value = '';
            document.getElementById('password').value = '';
            document.getElementById('role').value = '0';
            document.getElementById('userFormModal').style.display = 'block';
        }
        
        function openEditForm(id, username, role) {
            document.getElementById('formTitle').innerText = '编辑用户';
            document.getElementById('userForm').action = '${pageContext.request.contextPath}/user/update';
            document.getElementById('userId').value = id;
            document.getElementById('username').value = username;
            document.getElementById('password').value = '';
            document.getElementById('role').value = role;
            document.getElementById('userFormModal').style.display = 'block';
        }
        
        function closeUserForm() {
            document.getElementById('userFormModal').style.display = 'none';
        }
    </script>
</body>
</html> 