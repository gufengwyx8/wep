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
 * JdbcTemplate JDBC模板类
 * 
 * @version 1.0
 * 
 */
public class JdbcTemplate {

    /** 数据源对象 */
    private DataSource dataSource;

    /**
     * 设置数据源构造方法
     * 
     * @param dataSource
     *            数据源对象
     */
    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 根据数据源配置获取数据库连接对象
     * 
     * @return 数据库连接对象
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
     * 使用回调接口执行数据库方法
     * 
     * @param <T>
     *            返回值类型
     * @param callback
     *            回调接口实例
     * @return 回调接口返回值
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
     * 执行更新语句，返回影响行数
     * 
     * @param sql
     *            sql语句
     * @return 影响行数
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
     * 执行更新语句，返回影响行数，使用预处理对象
     * 
     * @param sql
     *            更新语句
     * @param params
     *            参数
     * @return 影响行数
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
     * 执行查询语句，返回查询结果集
     * 
     * @param sql
     *            查询语句
     * @return 结果集
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
     * 执行查询语句，返回结果集，使用预处理对象
     * @param sql 查询语句
     * @param params 参数
     * @return 结果集
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
     * 返回 dataSource
     * 
     * @return dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * 设置 dataSource
     * 
     * @param dataSource
     *            dataSource
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
