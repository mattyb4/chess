package handler;

import com.google.gson.Gson;
import dataaccess.BadRequestException;
import dataaccess.DataAccessException;
import dataaccess.InvalidUserException;
import model.GameData;
import model.GameSumm;
import service.GameService;
import spark.Request;
import spark.Response;

import java.util.Collection;
import java.util.Map;

public class GameHandler {
    private final GameService service;

    public GameHandler(GameService service) {
        this.service = service;
    }

    public String create(Request req, Response res) throws DataAccessException {
        try {
            var serializer = new Gson();
            GameData data = serializer.fromJson(req.body(), GameData.class);
            GameData gameData = service.create(data.gameName(), req.headers("Authorization"));
            var result = serializer.toJson(gameData);
            res.status(200);
            return result;
        } catch (BadRequestException e) {
            res.status(400);
            return new Gson().toJson(new ErrorHandler("Error: no game name"));
        } catch (InvalidUserException e) {
            res.status(401);
            return new Gson().toJson(new ErrorHandler("Error: unauthorized"));
        }
    }

    public String listAllGames(Request req, Response res) throws DataAccessException {
        try {
            var serializer = new Gson();
            Collection<GameSumm> gameData = service.listAllGames(req.headers("Authorization"));
            Map<String, Collection<GameSumm>> adjustedData = Map.of("games", gameData);
            var result = serializer.toJson(adjustedData);
            res.status(200);
            return result;
        } catch (InvalidUserException e) {
            res.status(401);
            return new Gson().toJson(new ErrorHandler("Error: unauthorized"));
        }
    }
}
