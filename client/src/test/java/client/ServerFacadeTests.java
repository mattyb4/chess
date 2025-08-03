package client;

import dataaccess.exceptions.AlreadyTakenException;
import model.AuthData;
import model.GameSumm;
import model.JoinRequest;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ResponseException;
import ui.ServerFacade;
import dataaccess.exceptions.*;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }
    @AfterEach
    public void cleanup() throws ResponseException {
        facade.clear();
    }
    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void positiveRegister() throws Exception{
        var testUser = new UserData("username", "password", "test@gmail.com");
        var authData = facade.register(testUser);
        assertTrue(authData.authToken().length() > 10);
    }

    @Test
    public void negativeRegister() throws ResponseException {
        var testUser = new UserData("username", "password", "test@gmail.com");
        facade.register(testUser);
        assertThrows(ResponseException.class, () -> {facade.register(testUser);});
    }

    @Test
    public void positiveLogin() throws Exception {
        var testUser = new UserData("username", "password", "test@gmail.com");
        facade.register(testUser);
        var authTest = facade.login(testUser);
        System.out.println("AuthData: " + authTest);
        assertNotNull(authTest);
        assertNotNull(authTest.authToken());
        assertEquals("username", authTest.username());
    }

    @Test
    public void negativeLogin() throws Exception {
        var testUser = new UserData("username", "password", "test@gmail.com");
        facade.register(testUser);
        var wrongUser = new UserData("username", "wrongpass", "test@gmail.com");
        assertThrows(ResponseException.class, () -> {facade.login(wrongUser);});
    }

    @Test
    public void positiveLogout() throws Exception {
        var testUser = new UserData("username", "password", "test@gmail.com");
        var authTest = facade.register(testUser);
        facade.logout(authTest.authToken());
        var exception = assertThrows(ResponseException.class, () -> {
            facade.logout(authTest.authToken());
        });
        assertNotEquals(200, exception.StatusCode());
    }

    @Test
    public void negativeLogout() throws Exception {
        var exception = assertThrows(ResponseException.class, () -> {
            facade.logout("");
        });
        assertNotEquals(200, exception.StatusCode());
    }

    @Test
    public void positiveCreate() throws Exception {
        var user = new UserData("user", "pass", "email");
        facade.register(user);
        AuthData auth = facade.login(user);
        var gameData = facade.create("testgame", auth.authToken());
        assertNotNull(gameData);
        assertNull(gameData.whiteUsername());
        assertNull(gameData.blackUsername());
        assertEquals("testgame", gameData.gameName());
    }

    @Test
    public void negativeCreate() throws Exception {
        assertThrows(ResponseException.class, () -> {facade.create("gamename","noauthtoken");});
    }

    @Test
    public void positiveList() throws Exception {
        var user = new UserData("user", "pass", "email");
        facade.register(user);
        AuthData auth = facade.login(user);
        facade.create("testgame", auth.authToken());
        Collection<GameSumm> games = facade.listAllGames(auth.authToken());
        GameSumm summary = games.iterator().next();
        assertNotNull(games);
        assertEquals("testgame", summary.gameName());
    }

    @Test
    public void negativeList() throws Exception {
        assertThrows(ResponseException.class, () -> {facade.listAllGames("invalidtoken");});
    }

    @Test
    public void positiveJoin() throws Exception {
        var user = new UserData("user", "pass", "email");
        facade.register(user);
        AuthData auth = facade.login(user);
        var gameData = facade.create("testgame", auth.authToken());
        int gameID = gameData.gameID();
        var request = new JoinRequest("WHITE", gameID);
        facade.join(request, auth.authToken());
        var updatedGames = facade.listAllGames(auth.authToken());
        var updatedGame = updatedGames.iterator().next();
        assertEquals("user", updatedGame.whiteUsername());
        assertEquals("testgame", updatedGame.gameName());
    }

    @Test
    public void negativeJoin() throws Exception {
        assertThrows(ResponseException.class, () -> {facade.join(new JoinRequest("WHITE",1),"invalidtoken");});
    }

}
