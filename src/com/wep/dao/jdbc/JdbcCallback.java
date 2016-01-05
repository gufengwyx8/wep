package com.wep.dao.jdbc;

import java.sql.Connection;

/**
 * 
 * JdbcCallback JDBC回调接口
 * @version 1.0
 *
 * @param <T> 返回值泛型
 */
public interface JdbcCallback<T> {
    /**
     * 回调接口
     * @param conn 数据库连接对象
     * @return 
     */
    T callback(Connection conn);
}
