package com.flightreservation.service;

import com.flightreservation.dao.UserDAO;
import com.flightreservation.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDAO userDAO;

    public User register(User user) {
        logger.info("Registering new user with email: {}", user.getEmail());

        if (userDAO.existsByEmail(user.getEmail())) {
            logger.warn("User with email {} already exists", user.getEmail());
            throw new RuntimeException("Email already registered");
        }

        User savedUser = userDAO.save(user);
        logger.info("User registered successfully with ID: {}", savedUser.getUserId());
        return savedUser;
    }

    public User login(String email, String password) {
        logger.info("Login attempt for email: {}", email);

        User user = userDAO.findByEmailAndPassword(email, password);

        if (user == null) {
            logger.warn("Login failed for email: {}", email);
            throw new RuntimeException("Invalid email or password");
        }

        logger.info("Login successful for user: {}", email);
        return user;
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public User findById(Integer userId) {
        return userDAO.findById(userId);
    }

    public boolean existsByEmail(String email) {
        return userDAO.existsByEmail(email);
    }
}
