/*
 * @(#)WepDao.java 2014-5-23 œ¬ŒÁ10:19:27 wep
 */
package com.wep.dao;

import java.util.Arrays;

import com.wep.dao.jdbc.JdbcTemplate;

/**
 * WepDao
 * @author wang
 * @version 1.0
 *
 */
public class WepDao {
    private JdbcTemplate jdbcTemplate;

    public void saveIV(String ivStr) {
        String sql = "insert into `iv` (value) values (?)";
        Object[] params = { ivStr };
        jdbcTemplate.executeUpdate(sql, params);
    }

    public void saveCipher(String cipher) {
        String sql = "insert into `cipher` (value) values (?)";
        Object[] params = { cipher };
        jdbcTemplate.executeUpdate(sql, params);
    }

    public void saveKey(String key) {
        String sql = "insert into `key` (value) values (?)";
        Object[] params = { key };
        jdbcTemplate.executeUpdate(sql, params);
    }

    public void saveResult(int[] k) {
        String sql = "insert into `result` (value) values (?)";
        Object[] params = { Arrays.toString(k) };
        jdbcTemplate.executeUpdate(sql, params);
    }

    /**
     * ∑µªÿ  jdbcTemplate
     * @return jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * …Ë÷√ jdbcTemplate
     * @param jdbcTemplate jdbcTemplate
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
