package com.qudratullo.springprojects.repository;

import com.qudratullo.springprojects.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

@Component
class UserRepositoryImpl implements UserRepository {

    private static final String USER_TABLE_NAME = "users";
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @Autowired
    public UserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    @PostConstruct
    private void initTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + " (" +
                "id serial PRIMARY KEY," +
                "username VARCHAR(255) NOT NULL," +
                "fullname VARCHAR(255) NOT NULL," +
                "email VARCHAR(255) NOT NULL" +
                ")");
    }

    @Override
    public void saveUser(User user) {
        long userId = insertAndGetId(user);
        setUserIdWithReflection(user, userId);
    }

    private long insertAndGetId(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(USER_TABLE_NAME)
                .usingGeneratedKeyColumns("id");
        Map<String, String> parameters = new HashMap<>(3);
        parameters.put("username", user.getUsername());
        parameters.put("fullname", user.getFullName());
        parameters.put("email", user.getEmail());
        return jdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Optional<User> findById(Long userId) {
        return jdbcTemplate.query("SELECT * FROM " + USER_TABLE_NAME + " WHERE id=?", new Object[]{userId}, new UserRowMapper())
                .stream().findAny();
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("SELECT * FROM " + USER_TABLE_NAME, new UserRowMapper());
    }

    private void setUserIdWithReflection(User user, long userId) {
        try {
            Field idField = user.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(user, userId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            long userId = resultSet.getLong("id");
            String username = resultSet.getString("username");
            String fullName = resultSet.getString("fullname");
            String email = resultSet.getString("email");
            User user = new User(username, fullName, email);
            setUserIdWithReflection(user, userId);
            return user;
        }
    }
}