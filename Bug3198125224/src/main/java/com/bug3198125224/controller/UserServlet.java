package com.bug3198125224.controller;

import com.bug3198125224.entity.User;
import com.bug3198125224.service.UserService;
import com.bug3198125224.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 用户控制器
 */
@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
    
    private final UserService userService = new UserServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 设置请求和响应的字符编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || "/".equals(pathInfo)) {
            // 获取所有用户
            listUsers(request, response);
        } else if ("/login".equals(pathInfo)) {
            // 跳转到登录页面
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        } else if ("/logout".equals(pathInfo)) {
            // 退出登录
            logout(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 设置请求和响应的字符编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        if ("/login".equals(pathInfo)) {
            // 处理登录请求
            login(request, response);
        } else if ("/add".equals(pathInfo)) {
            // 添加用户
            addUser(request, response);
        } else if ("/update".equals(pathInfo)) {
            // 更新用户
            updateUser(request, response);
        } else if ("/delete".equals(pathInfo)) {
            // 删除用户
            deleteUser(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    /**
     * 处理登录请求
     */
    private void login(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        User user = userService.login(username, password);
        
        if (user != null) {
            // 登录成功，将用户信息存入会话
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            
            // 根据角色重定向到不同页面
            if (user.isAdmin()) {
                response.sendRedirect(request.getContextPath() + "/project/admin");
            } else {
                response.sendRedirect(request.getContextPath() + "/project/user");
            }
        } else {
            // 登录失败
            request.setAttribute("error", "用户名或密码错误");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
    
    /**
     * 退出登录
     */
    private void logout(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        response.sendRedirect(request.getContextPath() + "/user/login");
    }
    
    /**
     * 获取所有用户
     */
    private void listUsers(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 检查是否是管理员
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null || !currentUser.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        List<User> users = userService.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/views/user_list.jsp").forward(request, response);
    }
    
    /**
     * 添加用户
     */
    private void addUser(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 检查是否是管理员
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null || !currentUser.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int role = Integer.parseInt(request.getParameter("role"));
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        
        if (userService.addUser(user)) {
            response.sendRedirect(request.getContextPath() + "/user/");
        } else {
            request.setAttribute("error", "添加用户失败");
            request.getRequestDispatcher("/WEB-INF/views/user_form.jsp").forward(request, response);
        }
    }
    
    /**
     * 更新用户
     */
    private void updateUser(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 检查是否是管理员
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null || !currentUser.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int role = Integer.parseInt(request.getParameter("role"));
        
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        
        if (userService.updateUser(user)) {
            response.sendRedirect(request.getContextPath() + "/user/");
        } else {
            request.setAttribute("error", "更新用户失败");
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/views/user_form.jsp").forward(request, response);
        }
    }
    
    /**
     * 删除用户
     */
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        // 检查是否是管理员
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null || !currentUser.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        // 检查被删除的用户是否是管理员
        User userToDelete = userService.getUserById(id);
        if (userToDelete != null && userToDelete.isAdmin()) {
            // 不允许删除管理员用户
            response.sendRedirect(request.getContextPath() + "/user/?error=cannot_delete_admin");
            return;
        }
        
        userService.deleteUser(id);
        
        response.sendRedirect(request.getContextPath() + "/user/");
    }
} 