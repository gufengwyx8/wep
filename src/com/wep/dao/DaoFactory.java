/*
 * @(#)DaoFactory.java 2013-5-9 ÏÂÎç09:53:01 Audio
 */
package com.wep.dao;

import com.wep.dao.jdbc.DataSource;
import com.wep.dao.jdbc.JdbcTemplate;

/**
 * DaoFactory
 * 
 * @version 1.0
 * 
 */
public class DaoFactory {

    private static JdbcTemplate jdbcTemplate;

    private static WepDao wepDao;

    static {
        DataSource ds = new DataSource();
        ds.setConnectionString("jdbc:mysql://localhost/wep");
        ds.setDriverName("com.mysql.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("root");
        jdbcTemplate = new JdbcTemplate(ds);

        wepDao = new WepDao();
        wepDao.setJdbcTemplate(jdbcTemplate);
    }

    /**
     * ·µ»Ø  wepDao
     * @return wepDao
     */
    public static WepDao getWepDao() {
        return wepDao;
    }

}
