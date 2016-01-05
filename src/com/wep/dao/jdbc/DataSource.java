package com.wep.dao.jdbc;

/**
 * DataSource ����Դ����
 * 
 * @version 1.0
 * 
 */
public class DataSource {
    /** ������ */
    private String driverName;

    /** �����ַ��� */
    private String connectionString;

    /** �û��� */
    private String username;

    /** ���� */
    private String password;

    /**
     * ���� driverName
     * 
     * @return driverName
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * ���� driverName
     * 
     * @param driverName
     *            driverName
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    /**
     * ���� connectionString
     * 
     * @return connectionString
     */
    public String getConnectionString() {
        return connectionString;
    }

    /**
     * ���� connectionString
     * 
     * @param connectionString
     *            connectionString
     */
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    /**
     * ���� username
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * ���� username
     * 
     * @param username
     *            username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * ���� password
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * ���� password
     * 
     * @param password
     *            password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
