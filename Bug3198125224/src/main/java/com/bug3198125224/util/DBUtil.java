package com.bug3198125224.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库连接工具类
 */
public class DBUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/bug3198125224?useSSL=false&serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&connectionCollation=utf8mb4_unicode_ci";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        // 设置数据库连接池属性
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        
        // 连接池配置
        dataSource.setInitialSize(5);          // 初始连接数
        dataSource.setMaxTotal(20);            // 最大连接数
        dataSource.setMaxIdle(10);             // 最大空闲连接数
        dataSource.setMinIdle(5);              // 最小空闲连接数
        dataSource.setMaxWaitMillis(5000);     // 获取连接的最大等待时间
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * 关闭数据库连接池
     */
    public static void closeDataSource() throws SQLException {
        if (dataSource != null) {
            dataSource.close();
        }
    }
} 