package com.flightreservation.service;

import com.flightreservation.dao.UserDAO;
import com.flightreservation.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

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
    public void testRegister_Success() {
        when(userDAO.existsByEmail(testUser.getEmail())).thenReturn(false);
        when(userDAO.save(testUser)).thenReturn(testUser);

        User result = userService.register(testUser);

        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userDAO, times(1)).existsByEmail(testUser.getEmail());
        verify(userDAO, times(1)).save(testUser);
    }

    @Test(expected = RuntimeException.class)
    public void testRegister_EmailAlreadyExists() {
        when(userDAO.existsByEmail(testUser.getEmail())).thenReturn(true);

        userService.register(testUser);
    }

    @Test
    public void testLogin_Success() {
        when(userDAO.findByEmailAndPassword(testUser.getEmail(), testUser.getPassword()))
                .thenReturn(testUser);

        User result = userService.login(testUser.getEmail(), testUser.getPassword());

        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userDAO, times(1)).findByEmailAndPassword(testUser.getEmail(), testUser.getPassword());
    }

    @Test(expected = RuntimeException.class)
    public void testLogin_InvalidCredentials() {
        when(userDAO.findByEmailAndPassword(testUser.getEmail(), "wrongpassword"))
                .thenReturn(null);

        userService.login(testUser.getEmail(), "wrongpassword");
    }

    @Test
    public void testFindByEmail() {
        when(userDAO.findByEmail(testUser.getEmail())).thenReturn(testUser);

        User result = userService.findByEmail(testUser.getEmail());

        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userDAO, times(1)).findByEmail(testUser.getEmail());
    }

    @Test
    public void testExistsByEmail_True() {
        when(userDAO.existsByEmail(testUser.getEmail())).thenReturn(true);

        boolean result = userService.existsByEmail(testUser.getEmail());

        assertTrue(result);
        verify(userDAO, times(1)).existsByEmail(testUser.getEmail());
    }

    @Test
    public void testExistsByEmail_False() {
        when(userDAO.existsByEmail("nonexistent@example.com")).thenReturn(false);

        boolean result = userService.existsByEmail("nonexistent@example.com");

        assertFalse(result);
        verify(userDAO, times(1)).existsByEmail("nonexistent@example.com");
    }
}
