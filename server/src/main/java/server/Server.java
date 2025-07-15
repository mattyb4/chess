package server;

import dataaccess.*;
import handler.ClearHandler;
import handler.UserHandler;
import service.UserService;
import spark.*;

public class Server {
    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();

    UserService userService = new UserService(userDAO, authDAO);

    UserHandler userHandler = new UserHandler(userService);
    ClearHandler clearHandler = new ClearHandler(userService);
    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", userHandler::register);
        Spark.delete("/db", clearHandler::clear);
        Spark.post("/session", userHandler::login);
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
