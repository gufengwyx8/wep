package com.wep.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * StatementCallback
 * 
 * @version 1.0
 * 
 * @param <T> ����ֵ����
 */
public abstract class StatementCallback<T> implements JdbcCallback<T> {

    @Override
    public T callback(Connection conn) {
        if (conn == null) {
            throw new IllegalAccessError("����δ��");
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
     * ���ʹ��Statement�ӳٵ�����ʵ��
     * @param stmt ���ݿ�ʵ��
     * @return ����ֵ
     */
    public abstract T execute(Statement stmt);

}
