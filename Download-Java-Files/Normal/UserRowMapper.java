package com.clocker.clocker.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet row, int rowNum) throws SQLException {
        User user = new User();
        user.setId(row.getInt("id"));
        user.setUsername(row.getString("username"));
        user.setName(row.getString("name"));
        user.setEmail(row.getString("email"));
        return user;
    }
}