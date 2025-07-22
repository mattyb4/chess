package dataaccess;

import dataaccess.exceptions.*;
import model.AuthData;
import model.GameData;
import model.GameSumm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.GameService;

import java.sql.SQLException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class SQLGameTests {
    private SQLUserDAO userDAO;
    private SQLAuthDAO authDAO;
    private SQLGameDAO gameDAO;
    private GameService gameService;

    @BeforeEach
    public void setup() throws DataAccessException {
        userDAO = new SQLUserDAO();
        authDAO = new SQLAuthDAO();
        gameDAO = new SQLGameDAO();
        gameService = new GameService(gameDAO, authDAO);

        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
    }

    @Test
    @DisplayName("Functioning game creation")
    public void positiveCreateTest() throws DataAccessException, BadRequestException, InvalidUserException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            assertFalse(conn.isClosed(), "No open db connection");
        }
        AuthData testAuth = new AuthData("authtoken","username");
        authDAO.createAuth(testAuth);
        var gameData = gameService.create("testgame","authtoken");
        assertNotNull(gameData);
        assertNull(gameData.whiteUsername());
        assertNull(gameData.blackUsername());
        assertEquals("testgame", gameData.gameName());
    }

    @Test
    @DisplayName("Not logged in")
    public void negativeCreateTest() throws DataAccessException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            assertFalse(conn.isClosed(), "No open db connection");
        }
        assertThrows(InvalidUserException.class, () -> {gameService.create("gamename","noauthtoken");});
    }

    @Test
    @DisplayName("List games successfully")
    public void positiveListTest() throws DataAccessException, BadRequestException, InvalidUserException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            assertFalse(conn.isClosed(), "No open db connection");
        }
        AuthData testAuth = new AuthData("authtoken","username");
        authDAO.createAuth(testAuth);
        gameService.create("testgame","authtoken");
        Collection<GameSumm> games = gameService.listAllGames("authtoken");
        GameSumm summary = games.iterator().next();
        assertNotNull(games);
        assertEquals("testgame",summary.gameName());
    }

    @Test
    @DisplayName("Not logged in")
    public void negativeListTest() throws DataAccessException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            assertFalse(conn.isClosed(), "No open db connection");
        }
        assertThrows(InvalidUserException.class, () -> {gameService.listAllGames("invalidtoken");});
    }

    @Test
    @DisplayName("Successfully join game")
    public void positiveJoinTest()
            throws DataAccessException, BadRequestException, InvalidUserException, InvalidInputException, AlreadyTakenException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            assertFalse(conn.isClosed(), "No open db connection");
        }
        AuthData testAuth = new AuthData("authtoken","username");
        authDAO.createAuth(testAuth);
        var gameData = gameService.create("testgame",testAuth.authToken());
        int gameID = gameData.gameID();
        gameService.join(gameID, "WHITE", testAuth.authToken());
        GameData updatedGame = gameDAO.getGame(gameID);
        assertEquals("username",updatedGame.whiteUsername());
        assertEquals("testgame",updatedGame.gameName());
    }

    @Test
    @DisplayName("Unauthorized join attempt")
    public void negativeJoinTest() throws DataAccessException, SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            assertFalse(conn.isClosed(), "No open db connection");
        }
        assertThrows(InvalidUserException.class, () -> {gameService.join(1,"WHITE","invalidtoken");});
    }

}
