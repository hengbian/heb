<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${project == null ? '添加项目' : '编辑项目'} - Bug3198125224</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 600px;
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
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], select, textarea {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        textarea {
            height: 100px;
            resize: vertical;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .btn:hover {
            background-color: #45a049;
        }
        .btn-secondary {
            background-color: #f2f2f2;
            color: #333;
            margin-left: 10px;
        }
        .btn-secondary:hover {
            background-color: #e6e6e6;
        }
        .error-message {
            color: red;
            margin-bottom: 15px;
        }
        .user-info {
            text-align: right;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="user-info">
            欢迎, ${user.username} | 
            <a href="${pageContext.request.contextPath}/user/logout">退出登录</a>
        </div>
        
        <h1>${project == null ? '添加项目' : '编辑项目'}</h1>
        
        <c:if test="${not empty error}">
            <div class="error-message">
                ${error}
            </div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/project/${project == null ? 'add' : 'update'}" method="post">
            <c:if test="${project != null}">
                <input type="hidden" name="id" value="${project.id}">
            </c:if>
            
            <div class="form-group">
                <label for="name">项目名称：</label>
                <input type="text" id="name" name="name" value="${project != null ? project.name : ''}" required>
            </div>
            
            <div class="form-group">
                <label for="description">项目描述：</label>
                <textarea id="description" name="description" required>${project != null ? project.description : ''}</textarea>
            </div>
            
            <div class="form-group">
                <label for="status">项目状态：</label>
                <select id="status" name="status" required>
                    <option value="0" ${project != null && project.status == 0 ? 'selected' : ''}>立项</option>
                    <option value="1" ${project != null && project.status == 1 ? 'selected' : ''}>进行中</option>
                    <option value="2" ${project != null && project.status == 2 ? 'selected' : ''}>结项</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="userId">负责人：</label>
                <select id="userId" name="userId" required>
                    <c:forEach var="userItem" items="${users}">
                        <option value="${userItem.id}" ${project != null && project.userId == userItem.id ? 'selected' : ''}>${userItem.username}</option>
                    </c:forEach>
                </select>
            </div>
            
            <button type="submit" class="btn">${project == null ? '添加' : '更新'}</button>
            <a href="${pageContext.request.contextPath}/project/admin" class="btn btn-secondary">取消</a>
        </form>
    </div>
</body>
</html> 