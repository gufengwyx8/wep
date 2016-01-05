package com.wep.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 * PreparedStatementCallback 
 * ʹ��Ԥ�������ص��ӿ�
 * 
 * @version 1.0
 * @param <T> ����ֵ����
 */
public abstract class PreparedStatementCallback<T> implements JdbcCallback<T> {

    @Override
    public T callback(Connection conn) {
        if (conn == null) {
            throw new IllegalAccessError("����δ��");
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
     * ����Ԥ�������
     * @param ps Ԥ�������ʵ��
     * @param value ����ֵ
     * @param index ��������
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
     * ���ʹ��PreparedStatement�ӳٵ�����ʵ��
     * @param ps ���ݿ�ʵ��
     * @return ����ֵ
     */
    public abstract T execute(PreparedStatement ps);

    /**
     * ����sql�ַ���
     * @return sql
     */
    public abstract String getSql();

    /**
     * ���ز�������
     * @return ��������
     */
    public abstract Object[] getParams();
}
