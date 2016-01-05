package com.wep.dao.jdbc;

import java.sql.Connection;

/**
 * 
 * JdbcCallback JDBC�ص��ӿ�
 * @version 1.0
 *
 * @param <T> ����ֵ����
 */
public interface JdbcCallback<T> {
    /**
     * �ص��ӿ�
     * @param conn ���ݿ����Ӷ���
     * @return 
     */
    T callback(Connection conn);
}
