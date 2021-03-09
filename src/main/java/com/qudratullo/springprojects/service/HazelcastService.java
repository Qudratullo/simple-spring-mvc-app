package com.qudratullo.springprojects.service;

import com.hazelcast.core.HazelcastInstance;
import com.qudratullo.springprojects.model.User;
import com.qudratullo.springprojects.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

@Component
public class HazelcastService {

    private final HazelcastInstance hazelcastInstance;
    private final UserRepository userRepository;

    @Autowired
    public HazelcastService(UserRepository userRepository, HazelcastInstance hazelcastInstance) {
        this.userRepository = userRepository;
        this.hazelcastInstance = hazelcastInstance;
    }

    @PostConstruct
    private void initUsersMap() {
        Map<Long, User> dataMap = hazelcastInstance.getMap("data");
        dataMap.clear();
        long count = userRepository.getAllUsers()
                .stream().map(user -> dataMap.put(user.getId(), user)).count();
    }

    @PreDestroy
    private void shutdownHazelcast() {
        hazelcastInstance.shutdown();
    }

    public List<User> getUsers() {
        Map<Long, User> dataMap = hazelcastInstance.getMap("data");
        return new ArrayList<>(dataMap.values());
    }

    public void addUser(@NonNull User user) {
        Objects.requireNonNull(user.getId());
        Map<Long, User> dataMap = hazelcastInstance.getMap("data");
        dataMap.put(user.getId(), user);
    }

    // Refresh data in every 1 hour
    @Scheduled(fixedRate = 60 * 60 * 1000)
    private void refreshData() {
        initUsersMap();
    }
}
