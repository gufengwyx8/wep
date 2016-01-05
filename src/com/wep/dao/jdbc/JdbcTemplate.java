package com.wep.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * JdbcTemplate JDBCģ����
 * 
 * @version 1.0
 * 
 */
public class JdbcTemplate {

    /** ����Դ���� */
    private DataSource dataSource;

    /**
     * ��������Դ���췽��
     * 
     * @param dataSource
     *            ����Դ����
     */
    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * ��������Դ���û�ȡ���ݿ����Ӷ���
     * 
     * @return ���ݿ����Ӷ���
     */
    private Connection getConnection() {
        try {
            Class.forName(dataSource.getDriverName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                dataSource.getConnectionString(), dataSource.getUsername(),
                dataSource.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * ʹ�ûص��ӿ�ִ�����ݿⷽ��
     * 
     * @param <T>
     *            ����ֵ����
     * @param callback
     *            �ص��ӿ�ʵ��
     * @return �ص��ӿڷ���ֵ
     */
    public <T> T execute(JdbcCallback<T> callback) {
        Connection conn = this.getConnection();
        T result = callback.callback(conn);
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * ִ�и�����䣬����Ӱ������
     * 
     * @param sql
     *            sql���
     * @return Ӱ������
     */
    public int executeUpdate(final String sql) {
        return this.execute(new StatementCallback<Integer>() {
            @Override
            public Integer execute(Statement stmt) {
                try {
                    return stmt.executeUpdate(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return -1;
            }
        });
    }

    /**
     * ִ�и�����䣬����Ӱ��������ʹ��Ԥ�������
     * 
     * @param sql
     *            �������
     * @param params
     *            ����
     * @return Ӱ������
     */
    public int executeUpdate(final String sql, final Object[] params) {
        return this.execute(new PreparedStatementCallback<Integer>() {

            @Override
            public Integer execute(PreparedStatement ps) {
                try {
                    return ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return -1;
            }

            @Override
            public Object[] getParams() {
                return params;
            }

            @Override
            public String getSql() {
                return sql;
            }
        });
    }

    /**
     * ִ�в�ѯ��䣬���ز�ѯ�����
     * 
     * @param sql
     *            ��ѯ���
     * @return �����
     */
    public List<Map<String, Object>> executeQuery(final String sql) {
        return this.execute(new StatementCallback<List<Map<String, Object>>>() {

            @Override
            public List<Map<String, Object>> execute(Statement stmt) {
                ResultSet rs = null;
                List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
                try {
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        Map<String, Object> map = new TreeMap<String, Object>();
                        ResultSetMetaData md = rs.getMetaData();
                        for (int i = 1; i <= md.getColumnCount(); i++) {
                            map.put(md.getColumnLabel(i), rs.getObject(i));
                        }
                        result.add(map);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return result;
            }

        });
    }

    /**
     * ִ�в�ѯ��䣬���ؽ������ʹ��Ԥ�������
     * @param sql ��ѯ���
     * @param params ����
     * @return �����
     */
    public List<Map<String, Object>> executeQuery(final String sql,
            final Object[] params) {
        return this
                .execute(new PreparedStatementCallback<List<Map<String, Object>>>() {

                    @Override
                    public List<Map<String, Object>> execute(
                            PreparedStatement ps) {
                        ResultSet rs = null;
                        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
                        try {
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                Map<String, Object> map = new TreeMap<String, Object>();
                                ResultSetMetaData md = rs.getMetaData();
                                for (int i = 1; i <= md.getColumnCount(); i++) {
                                    map.put(md.getColumnLabel(i), rs
                                            .getObject(i));
                                }
                                result.add(map);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return result;
                    }

                    @Override
                    public Object[] getParams() {
                        return params;
                    }

                    @Override
                    public String getSql() {
                        return sql;
                    }

                });
    }

    /**
     * ���� dataSource
     * 
     * @return dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * ���� dataSource
     * 
     * @param dataSource
     *            dataSource
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
