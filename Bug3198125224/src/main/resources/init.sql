-- 创建数据库
CREATE DATABASE IF NOT EXISTS bug3198125224 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE bug3198125224;

-- 创建用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `role` int(11) NOT NULL DEFAULT '0' COMMENT '角色：0-普通用户，1-管理员',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 创建项目表
CREATE TABLE IF NOT EXISTS `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目ID',
  `name` varchar(100) NOT NULL COMMENT '项目名称',
  `description` text COMMENT '项目描述',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '项目状态：0-立项，1-进行中，2-结项',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `user_id` int(11) NOT NULL COMMENT '负责人ID',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目表';

-- 添加外键约束
ALTER TABLE `project` ADD CONSTRAINT `fk_project_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

-- 插入初始数据
-- 管理员账号
INSERT INTO `user` (`username`, `password`, `role`) VALUES ('admin', 'admin', 1);

-- 普通用户
INSERT INTO `user` (`username`, `password`, `role`) VALUES ('user1', 'user1', 0);
INSERT INTO `user` (`username`, `password`, `role`) VALUES ('user2', 'user2', 0);

-- 初始项目
INSERT INTO `project` (`name`, `description`, `status`, `create_time`, `update_time`, `user_id`) 
VALUES ('示例项目1', '这是一个示例项目1的描述，用于测试系统功能。', 0, NOW(), NOW(), 2);

INSERT INTO `project` (`name`, `description`, `status`, `create_time`, `update_time`, `user_id`) 
VALUES ('示例项目2', '这是一个示例项目2的描述，用于测试系统功能。', 1, NOW(), NOW(), 3); 