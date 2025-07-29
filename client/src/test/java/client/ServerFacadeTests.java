package client;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ResponseException;
import ui.ServerFacade;

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

}
