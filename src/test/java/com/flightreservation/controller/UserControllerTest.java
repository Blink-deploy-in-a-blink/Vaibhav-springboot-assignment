package com.flightreservation.controller;

import com.flightreservation.model.User;
import com.flightreservation.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private HttpSession session;

    @InjectMocks
    private UserController userController;

    private User testUser;

    @Before
    public void setUp() {
        testUser = new User();
        testUser.setUserId(1);
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");
        testUser.setRole("USER");
    }

    @Test
    public void testShowSignupForm() {
        String viewName = userController.showSignupForm(model);

        assertEquals("signup", viewName);
        verify(model, times(1)).addAttribute(eq("user"), any(User.class));
    }

    @Test
    public void testSignup_Success() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.register(testUser)).thenReturn(testUser);

        String viewName = userController.signup(testUser, bindingResult, model);

        assertEquals("login", viewName);
        verify(userService, times(1)).register(testUser);
        verify(model, times(1)).addAttribute("successMessage", "Registration successful! Please login.");
    }

    @Test
    public void testSignup_ValidationErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.signup(testUser, bindingResult, model);

        assertEquals("signup", viewName);
        verify(userService, never()).register(any());
    }

    @Test
    public void testSignup_EmailAlreadyExists() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.register(testUser)).thenThrow(new RuntimeException("Email already registered"));

        String viewName = userController.signup(testUser, bindingResult, model);

        assertEquals("signup", viewName);
        verify(model, times(1)).addAttribute("errorMessage", "Email already registered");
    }

    @Test
    public void testShowLoginForm() {
        String viewName = userController.showLoginForm(model);

        assertEquals("login", viewName);
        verify(model, times(1)).addAttribute(eq("user"), any(User.class));
    }

    @Test
    public void testLogin_Success_UserRole() {
        when(bindingResult.hasFieldErrors("email")).thenReturn(false);
        when(bindingResult.hasFieldErrors("password")).thenReturn(false);
        when(userService.login(testUser.getEmail(), testUser.getPassword())).thenReturn(testUser);

        String viewName = userController.login(testUser, bindingResult, session, model);

        assertEquals("redirect:/user/dashboard", viewName);
        verify(session, times(1)).setAttribute("userId", testUser.getUserId());
        verify(session, times(1)).setAttribute("userEmail", testUser.getEmail());
        verify(session, times(1)).setAttribute("userRole", testUser.getRole());
    }

    @Test
    public void testLogin_Success_AdminRole() {
        testUser.setRole("ADMIN");
        when(bindingResult.hasFieldErrors("email")).thenReturn(false);
        when(bindingResult.hasFieldErrors("password")).thenReturn(false);
        when(userService.login(testUser.getEmail(), testUser.getPassword())).thenReturn(testUser);

        String viewName = userController.login(testUser, bindingResult, session, model);

        assertEquals("redirect:/admin/dashboard", viewName);
        verify(session, times(1)).setAttribute("userRole", "ADMIN");
    }

    @Test
    public void testLogin_ValidationErrors() {
        when(bindingResult.hasFieldErrors("email")).thenReturn(true);

        String viewName = userController.login(testUser, bindingResult, session, model);

        assertEquals("login", viewName);
        verify(userService, never()).login(anyString(), anyString());
    }

    @Test
    public void testLogin_InvalidCredentials() {
        when(bindingResult.hasFieldErrors("email")).thenReturn(false);
        when(bindingResult.hasFieldErrors("password")).thenReturn(false);
        when(userService.login(testUser.getEmail(), testUser.getPassword()))
                .thenThrow(new RuntimeException("Invalid email or password"));

        String viewName = userController.login(testUser, bindingResult, session, model);

        assertEquals("login", viewName);
        verify(model, times(1)).addAttribute("errorMessage", "Invalid email or password");
        verify(model, times(1)).addAttribute(eq("user"), any(User.class));
    }
}
