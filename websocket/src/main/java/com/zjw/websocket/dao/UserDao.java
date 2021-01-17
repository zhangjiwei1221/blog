package com.zjw.websocket.dao;

import com.zjw.websocket.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isOk(User user) {
        String sql = "select * from user where username = ? and password = ?";
        Object[] params = { user.getUsername(), user.getPassword() };
        int[] types = new int[]{ Types.VARCHAR, Types.VARCHAR };
        BeanPropertyRowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return !jdbcTemplate.query(sql, params, types, rowMapper).isEmpty();
    }
}
