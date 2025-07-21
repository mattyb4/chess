package handler;

import com.google.gson.Gson;
import dataaccess.*;
import model.GameData;
import model.GameSumm;
import model.JoinRequest;
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
        } catch (DataAccessException e) {
            res.status(500);
            return new Gson().toJson(new ErrorHandler("Error: internal server error"));
        }
    }

    public String listAllGames(Request req, Response res) throws DataAccessException {
        try {
            var serializer = new Gson();
            Collection<GameSumm> gameData = service.listAllGames(req.headers("Authorization"));
            //convert collection into map with the key "games" to match output requirements
            Map<String, Collection<GameSumm>> adjustedData = Map.of("games", gameData);
            var result = serializer.toJson(adjustedData);
            res.status(200);
            return result;
        } catch (InvalidUserException e) {
            res.status(401);
            return new Gson().toJson(new ErrorHandler("Error: unauthorized"));
        } catch (DataAccessException e) {
            res.status(500);
            return new Gson().toJson(new ErrorHandler("Error: internal server error"));
        }
    }

    public String join(Request req, Response res) throws DataAccessException {
        try {
            var serializer = new Gson();
            JoinRequest data = serializer.fromJson(req.body(), JoinRequest.class);
            service.join(data.gameID(), data.playerColor(), req.headers("Authorization"));
            res.status(200);
            return "{}";
        } catch (InvalidInputException e) {
            res.status(400);
            return new Gson().toJson(new ErrorHandler("Error: game not found"));
        } catch (InvalidUserException e) {
            res.status(401);
            return new Gson().toJson(new ErrorHandler("Error: unauthorized"));
        } catch (AlreadyTakenException e) {
            res.status(403);
            return new Gson().toJson(new ErrorHandler("Error: color already taken"));
        } catch (BadRequestException e) {
            res.status(400);
            return new Gson().toJson(new ErrorHandler("Error: valid color not specifiied"));
        } catch (DataAccessException e) {
            res.status(500);
            return new Gson().toJson(new ErrorHandler("Error: Internal server error"));
        }
    }
}
