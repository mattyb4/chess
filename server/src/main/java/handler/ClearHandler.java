package handler;

import com.google.gson.Gson;
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
        try {
            userService.clear();
            gameService.clear();
            res.status(200);
            return "{}";
        } catch (DataAccessException e) {
            res.status(500);
            return new Gson().toJson(new ErrorHandler("Error: internal server error"));
        }
    }
}
