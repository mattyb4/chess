package handler;

import dataaccess.DataAccessException;
import service.GameService;
import service.UserService;
import spark.Request;
import spark.Response;

public class ClearHandler {
    private final UserService userService;
    private final GameService gameService;

    public ClearHandler(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    public String clear(Request req, Response res) throws DataAccessException {
        userService.clear();
        gameService.clear();
        return "{}";
    }
}
