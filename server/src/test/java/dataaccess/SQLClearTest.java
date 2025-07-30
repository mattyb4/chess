package dataaccess;

import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.BadRequestException;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.InvalidUserException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.GameService;
import service.UserService;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class SQLClearTest {
    private SQLUserDAO userDAO;
    private SQLAuthDAO authDAO;
    private SQLGameDAO gameDAO;
    private UserService userService;
    private GameService gameService;

    @BeforeEach
    public void setup() throws DataAccessException {
        userDAO = new SQLUserDAO();
        authDAO = new SQLAuthDAO();
        gameDAO = new SQLGameDAO();
        userService = new UserService(userDAO, authDAO);
        gameService = new GameService(gameDAO, authDAO);

        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
    }

    @Test
    @DisplayName("Functioning clear method")
    public void positiveClearTest() throws DataAccessException, BadRequestException, InvalidUserException, SQLException, AlreadyTakenException {
        try (var conn = DatabaseManager.getConnection()) {
            assertFalse(conn.isClosed(), "No open db connection");
        }

        userDAO.createUser(new UserData("username","password", "test@gmail.com"));
        authDAO.createAuth(new AuthData("authtoken","username"));
        gameService.create("gamename","authtoken");

        assertNotNull(userDAO.getUser("username"));
        assertNotNull(authDAO.getAuth("authtoken"));
        assertNotNull(gameDAO.getGame(1));

        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();

        assertNull(userDAO.getUser("username"));
        assertNull(authDAO.getAuth("authtoken"));
        assertNull(gameDAO.getGame(1));
    }
}
