package com.wep.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * StatementCallback
 * 
 * @version 1.0
 * 
 * @param <T> 返回值泛型
 */
public abstract class StatementCallback<T> implements JdbcCallback<T> {

    @Override
    public T callback(Connection conn) {
        if (conn == null) {
            throw new IllegalAccessError("连接未打开");
        }
        Statement stmt = null;
        T result = null;
        try {
            stmt = conn.createStatement();
            result = this.execute(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 如何使用Statement延迟到子类实现
     * @param stmt 数据库实例
     * @return 返回值
     */
    public abstract T execute(Statement stmt);

}
