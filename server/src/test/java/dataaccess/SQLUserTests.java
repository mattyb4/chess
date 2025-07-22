package dataaccess;


import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.BadRequestException;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.InvalidUserException;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.UserService;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SQLUserTests {
    private SQLUserDAO userDAO;
    private SQLAuthDAO authDAO;
    private UserService userService;

    @BeforeEach
    public void setup() throws DataAccessException {
        userDAO = new SQLUserDAO();
        authDAO = new SQLAuthDAO();
        userService = new UserService(userDAO, authDAO);

        userDAO.clear();
        authDAO.clear();
    }

    @Test
    public void positiveRegisterTest() throws DataAccessException, BadRequestException, AlreadyTakenException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            assertFalse(conn.isClosed(), "No open db connection");
        }
        var testUser = new UserData("username", "password", "test@gmail.com");
        var authTest = userService.register(testUser);

        assertNotNull(authTest.authToken());
        assertEquals("username", authTest.username());
    }
    @Test
    @DisplayName("Register failure - duplicate username")
    public void negativeRegisterTest() throws DataAccessException, BadRequestException, AlreadyTakenException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            assertFalse(conn.isClosed(), "No open db connection");
        }
        var testUser = new UserData("username", "password", "test@gmail.com");
        userService.register(testUser);
        assertThrows(AlreadyTakenException.class, () -> {userService.register(testUser);});
    }

    @Test
    @DisplayName("Login Success")
    public void positiveLoginTest() throws DataAccessException, BadRequestException, InvalidUserException, AlreadyTakenException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            assertFalse(conn.isClosed(), "No open db connection");
        }
        var testUser = new UserData("username", "password", "test@gmail.com");
        userService.register(testUser);
        var authTest = userService.login(testUser);
        assertNotNull(authTest.authToken());
        assertEquals("username", authTest.username());
    }

    @Test
    @DisplayName("Login failure")
    public void negativeLoginTest() throws DataAccessException, BadRequestException, AlreadyTakenException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            assertFalse(conn.isClosed(), "No open db connection");
        }
        var testUser = new UserData("username", "password", "test@gmail.com");
        userService.register(testUser);
        var wrongUser = new UserData("username", "wrongpass", "test@gmail.com");
        assertThrows(InvalidUserException.class, () -> {userService.login(wrongUser);});
    }

    @Test
    @DisplayName("Logout success")
    public void positiveLogoutTest() throws DataAccessException, BadRequestException, AlreadyTakenException, InvalidUserException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            assertFalse(conn.isClosed(), "No open db connection");
        }
        var testUser = new UserData("username", "password", "test@gmail.com");
        var authTest = userService.register(testUser);
        userService.logout(authTest.authToken());
        assertNull(authDAO.getAuth(authTest.authToken()));
    }

    @Test
    @DisplayName("Logout failure")
    public void negativeLogoutTest() throws DataAccessException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            assertFalse(conn.isClosed(), "No open db connection");
        }
        assertThrows(InvalidUserException.class, () -> {userService.logout("");});
    }

}
