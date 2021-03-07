package com.qudratullo.springprojects.repository;

import com.qudratullo.springprojects.model.User;
import com.qudratullo.springprojects.service.Generator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository;
    private EmbeddedDatabase embeddedDatabase;

    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addScript("init_table.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        userRepository = new UserRepositoryImpl(embeddedDatabase, jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void saveUser() {
        User user = randomUser();

        userRepository.saveUser(user);

        assertNotNull(user.getId());
        Optional<User> optionalUser = userRepository.findById(user.getId());
        assertTrue(optionalUser.isPresent());
        User actualUser = optionalUser.get();
        assertEquals(user, actualUser);
    }

    @Test
    void getAllUsers() {
        User user1 = randomUser();
        User user2 = randomUser();
        userRepository.saveUser(user1);
        userRepository.saveUser(user2);

        List<User> users = userRepository.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    private User randomUser() {
        return new User(Generator.randomString(), Generator.randomString(), Generator.randomString());
    }
}