package com.wep.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 * PreparedStatementCallback 
 * 使用预处理对象回调接口
 * 
 * @version 1.0
 * @param <T> 返回值泛型
 */
public abstract class PreparedStatementCallback<T> implements JdbcCallback<T> {

    @Override
    public T callback(Connection conn) {
        if (conn == null) {
            throw new IllegalAccessError("连接未打开");
        }
        PreparedStatement ps = null;
        T result = null;
        try {
            ps = conn.prepareStatement(this.getSql());
            for (int i = 0; i < this.getParams().length; i++) {
                this.setParam(ps, this.getParams()[i], i + 1);
            }
            result = this.execute(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 设置预处理参数
     * @param ps 预处理对象实例
     * @param value 参数值
     * @param index 参数索引
     */
    private void setParam(PreparedStatement ps, Object value, int index) {
        if (ps == null) {
            return;
        }
        if (value instanceof Integer) {
            try {
                ps.setInt(index, (Integer) value);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (value instanceof String) {
            try {
                ps.setString(index, (String) value);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (value instanceof Date) {
            try {
                ps.setDate(index, new java.sql.Date(((Date) value).getTime()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                ps.setObject(index, value);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 如何使用PreparedStatement延迟到子类实现
     * @param ps 数据库实例
     * @return 返回值
     */
    public abstract T execute(PreparedStatement ps);

    /**
     * 返回sql字符串
     * @return sql
     */
    public abstract String getSql();

    /**
     * 返回参数集合
     * @return 参数集合
     */
    public abstract Object[] getParams();
}
