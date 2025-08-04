package server;

import dataaccess.*;
import handler.ClearHandler;
import handler.GameHandler;
import handler.UserHandler;
import service.GameService;
import service.UserService;
import spark.*;

public class Server {

    UserDAO userDAO = new SQLUserDAO();
    AuthDAO authDAO = new SQLAuthDAO();
    GameDAO gameDAO = new SQLGameDAO();

    UserService userService = new UserService(userDAO, authDAO);
    GameService gameService = new GameService(gameDAO, authDAO);

    UserHandler userHandler = new UserHandler(userService);
    GameHandler gameHandler = new GameHandler(gameService);
    ClearHandler clearHandler = new ClearHandler(userService, gameService);

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", userHandler::register);
        Spark.delete("/db", clearHandler::clear);
        Spark.post("/session", userHandler::login);
        Spark.delete("/session", userHandler::logout);
        Spark.post("/game", gameHandler::create);
        Spark.get("/game", gameHandler::listAllGames);
        Spark.put("/game", gameHandler::join);
        Spark.get("/game/:id", gameHandler::getGame);
        Spark.delete("/game", gameHandler::leaveGame);
        //This line initializes the server and can be removed once you have a functioning endpoint 
        //Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

}
