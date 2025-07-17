package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ClearServiceTest {
    private MemoryUserDAO userDAO;
    private MemoryAuthDAO authDAO;
    private MemoryGameDAO gameDAO;
    private UserService userService;
    private GameService gameService;

    @BeforeEach
    public void setup() throws DataAccessException {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
        userService = new UserService(userDAO, authDAO);
        gameService = new GameService(gameDAO, authDAO);
    }

    @Test
    @DisplayName("Functioning clear method")
    public void positiveClearTest() throws DataAccessException, BadRequestException, InvalidUserException {
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
