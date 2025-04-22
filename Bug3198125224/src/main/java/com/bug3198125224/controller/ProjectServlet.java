package com.bug3198125224.controller;

import com.bug3198125224.entity.Project;
import com.bug3198125224.entity.User;
import com.bug3198125224.service.ProjectService;
import com.bug3198125224.service.UserService;
import com.bug3198125224.service.impl.ProjectServiceImpl;
import com.bug3198125224.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 项目控制器
 */
@WebServlet("/project/*")
public class ProjectServlet extends HttpServlet {
    
    private final ProjectService projectService = new ProjectServiceImpl();
    private final UserService userService = new UserServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 设置请求和响应的字符编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        if ("/admin".equals(pathInfo)) {
            // 管理员查看所有项目
            listAllProjects(request, response);
        } else if ("/user".equals(pathInfo)) {
            // 用户查看自己负责的项目
            listUserProjects(request, response);
        } else if ("/detail".equals(pathInfo)) {
            // 查看项目详情
            showProjectDetail(request, response);
        } else if ("/add".equals(pathInfo)) {
            // 显示添加项目表单
            showAddForm(request, response);
        } else if ("/edit".equals(pathInfo)) {
            // 显示编辑项目表单
            showEditForm(request, response);
        } else if ("/delete".equals(pathInfo)) {
            // 删除项目（支持GET请求）
            deleteProject(request, response);
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
        
        if ("/add".equals(pathInfo)) {
            // 添加项目
            addProject(request, response);
        } else if ("/update".equals(pathInfo)) {
            // 更新项目
            updateProject(request, response);
        } else if ("/updateStatus".equals(pathInfo)) {
            // 更新项目状态
            updateProjectStatus(request, response);
        } else if ("/delete".equals(pathInfo)) {
            // 删除项目
            deleteProject(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    /**
     * 管理员查看所有项目
     */
    private void listAllProjects(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 检查是否是管理员
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        if (!currentUser.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/project/user");
            return;
        }
        
        List<Project> projects = projectService.getAllProjects();
        request.setAttribute("projects", projects);
        request.getRequestDispatcher("/WEB-INF/views/admin_projects.jsp").forward(request, response);
    }
    
    /**
     * 用户查看自己负责的项目
     */
    private void listUserProjects(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 检查是否已登录
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        List<Project> projects = projectService.getProjectsByUserId(currentUser.getId());
        request.setAttribute("projects", projects);
        request.getRequestDispatcher("/WEB-INF/views/user_projects.jsp").forward(request, response);
    }
    
    /**
     * 查看项目详情
     */
    private void showProjectDetail(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 检查是否已登录
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        int projectId = Integer.parseInt(request.getParameter("id"));
        Project project = projectService.getProjectById(projectId);
        
        if (project == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        // 检查权限：管理员可以查看所有项目，普通用户只能查看自己负责的项目
        if (!currentUser.isAdmin() && project.getUserId() != currentUser.getId()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        
        request.setAttribute("project", project);
        request.getRequestDispatcher("/WEB-INF/views/project_detail.jsp").forward(request, response);
    }
    
    /**
     * 显示添加项目表单
     */
    private void showAddForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 检查是否是管理员
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null || !currentUser.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        // 获取所有用户列表，用于分配项目
        List<User> users = userService.getAllUsers();
        request.setAttribute("users", users);
        
        request.getRequestDispatcher("/WEB-INF/views/project_form.jsp").forward(request, response);
    }
    
    /**
     * 显示编辑项目表单
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 检查是否是管理员
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null || !currentUser.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        int projectId = Integer.parseInt(request.getParameter("id"));
        Project project = projectService.getProjectById(projectId);
        
        if (project == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        request.setAttribute("project", project);
        
        // 获取所有用户列表，用于分配项目
        List<User> users = userService.getAllUsers();
        request.setAttribute("users", users);
        
        request.getRequestDispatcher("/WEB-INF/views/project_form.jsp").forward(request, response);
    }
    
    /**
     * 添加项目
     */
    private void addProject(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 检查是否是管理员
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null || !currentUser.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int status = Integer.parseInt(request.getParameter("status"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        
        // 验证数据
        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("error", "项目名称不能为空");
            showAddForm(request, response);
            return;
        }
        
        if (status < 0 || status > 2) {
            request.setAttribute("error", "无效的状态值");
            showAddForm(request, response);
            return;
        }
        
        // 创建项目对象
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setStatus(status);
        project.setCreateTime(new Date());
        project.setUpdateTime(new Date());
        project.setUserId(userId);
        
        if (projectService.addProject(project)) {
            response.sendRedirect(request.getContextPath() + "/project/admin");
        } else {
            request.setAttribute("error", "添加项目失败");
            
            // 获取所有用户列表，用于分配项目
            List<User> users = userService.getAllUsers();
            request.setAttribute("users", users);
            
            request.getRequestDispatcher("/WEB-INF/views/project_form.jsp").forward(request, response);
        }
    }
    
    /**
     * 更新项目
     */
    private void updateProject(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 检查是否是管理员
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null || !currentUser.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int status = Integer.parseInt(request.getParameter("status"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        
        // 验证数据
        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("error", "项目名称不能为空");
            request.setAttribute("project", new Project(id, name, description, status, null, null, userId));
            showEditForm(request, response);
            return;
        }
        
        if (status < 0 || status > 2) {
            request.setAttribute("error", "无效的状态值");
            request.setAttribute("project", new Project(id, name, description, status, null, null, userId));
            showEditForm(request, response);
            return;
        }
        
        // 检查项目是否存在
        Project existingProject = projectService.getProjectById(id);
        if (existingProject == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "项目不存在");
            return;
        }
        
        // 创建项目对象并更新
        Project project = new Project();
        project.setId(id);
        project.setName(name);
        project.setDescription(description);
        project.setStatus(status);
        project.setUpdateTime(new Date());
        project.setUserId(userId);
        
        if (projectService.updateProject(project)) {
            response.sendRedirect(request.getContextPath() + "/project/admin");
        } else {
            request.setAttribute("error", "更新项目失败");
            request.setAttribute("project", project);
            
            // 获取所有用户列表，用于分配项目
            List<User> users = userService.getAllUsers();
            request.setAttribute("users", users);
            
            request.getRequestDispatcher("/WEB-INF/views/project_form.jsp").forward(request, response);
        }
    }
    
    /**
     * 更新项目状态
     */
    private void updateProjectStatus(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        // 检查是否已登录
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        int id = Integer.parseInt(request.getParameter("id"));
        int status = Integer.parseInt(request.getParameter("status"));
        
        // 获取项目信息
        Project project = projectService.getProjectById(id);
        
        if (project == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        // 检查权限：管理员可以修改所有项目，普通用户只能修改自己负责的项目
        if (!currentUser.isAdmin() && project.getUserId() != currentUser.getId()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        
        // 验证状态值范围 (0-立项, 1-进行中, 2-结项)
        if (status < 0 || status > 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的状态值");
            return;
        }
        
        // 更新项目状态
        boolean success = projectService.updateProjectStatus(id, status);
        if (!success) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "更新项目状态失败");
            return;
        }
        
        // 根据用户角色跳转到不同页面
        if (currentUser.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/project/admin");
        } else {
            response.sendRedirect(request.getContextPath() + "/project/user");
        }
    }
    
    /**
     * 删除项目
     */
    private void deleteProject(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        // 检查是否是管理员
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null || !currentUser.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        // 获取项目信息，确保项目存在
        Project project = projectService.getProjectById(id);
        if (project == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "项目不存在");
            return;
        }
        
        // 删除项目
        boolean success = projectService.deleteProject(id);
        if (!success) {
            response.sendRedirect(request.getContextPath() + "/project/admin?error=delete_failed");
            return;
        }
        
        response.sendRedirect(request.getContextPath() + "/project/admin");
    }
} 