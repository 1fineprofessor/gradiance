package com.Demo.webAppDemo2.demo.dao;

import com.Demo.webAppDemo2.demo.model.Student;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class gradienceDaoImpl implements gradienceDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public gradienceDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM users",
                new BeanPropertyRowMapper<>(Student.class)
        );
    }

}
