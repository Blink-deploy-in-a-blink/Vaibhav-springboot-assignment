package com.flightreservation.controller;

import com.flightreservation.model.User;
import com.flightreservation.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        logger.info("Signup page accessed");
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        logger.info("Processing signup for email: {}", user.getEmail());

        if (result.hasErrors()) {
            return "signup";
        }

        try {
            userService.register(user);
            model.addAttribute("successMessage", "Registration successful! Please login.");
            return "login";
        } catch (RuntimeException e) {
            logger.error("Signup failed: {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "signup";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        logger.info("Login page accessed");
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") User user, BindingResult result,
                        HttpSession session, Model model) {
        logger.info("Processing login for email: {}", user.getEmail());

        if (result.hasFieldErrors("email") || result.hasFieldErrors("password")) {
            return "login";
        }

        try {
            User loggedInUser = userService.login(user.getEmail(), user.getPassword());
            session.setAttribute("userId", loggedInUser.getUserId());
            session.setAttribute("userEmail", loggedInUser.getEmail());
            session.setAttribute("userRole", loggedInUser.getRole());

            logger.info("User {} logged in successfully with role: {}", loggedInUser.getEmail(), loggedInUser.getRole());

            if ("ADMIN".equals(loggedInUser.getRole())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/user/dashboard";
            }
        } catch (RuntimeException e) {
            logger.error("Login failed: {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("user", new User());
            return "login";
        }
    }
}
