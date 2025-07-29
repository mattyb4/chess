package handler;

import com.google.gson.Gson;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.BadRequestException;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.InvalidUserException;
import model.AuthData;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;

public class UserHandler {
    private final UserService service;

    public UserHandler(UserService service) {
        this.service = service;
    }

    public String register(Request req, Response res) throws DataAccessException {
        try { //convert from Json to readable request
            var serializer = new Gson();
            UserData data = serializer.fromJson(req.body(), UserData.class);
            AuthData authData = service.register(data);
            var result = serializer.toJson(authData); //convert back into Json for output
            res.status(200);
            return result;
        } catch (AlreadyTakenException e) {
            res.status(403);
            return new Gson().toJson(new ErrorHandler("Error: Username already taken"));
        } catch (BadRequestException e) {
            res.status(400);
            return new Gson().toJson(new ErrorHandler("Error: Bad request"));
        } catch (DataAccessException e) {
            res.status(500);
            return new Gson().toJson(new ErrorHandler("Error: internal server error"));
        }
    }

    public String login(Request req, Response res) throws DataAccessException {
        try {
            var serializer = new Gson();
            UserData data = serializer.fromJson(req.body(), UserData.class);
            AuthData authData = service.login(data);
            var result = serializer.toJson(authData);
            res.status(200);
            return result;
        } catch (InvalidUserException e) {
            res.status(401);
            return new Gson().toJson(new ErrorHandler("Error: invalid credentials"));
        } catch (BadRequestException e) {
            res.status(400);
            return new Gson().toJson(new ErrorHandler("Error: missing username or password"));
        } catch (DataAccessException e) {
            res.status(500);
            return new Gson().toJson(new ErrorHandler("Error: internal server error"));
        }
    }

    public String logout(Request req, Response res) throws DataAccessException {
        try {
            String authToken = req.headers("Authorization");
            if (authToken == null || authToken.isBlank()) {
                res.status(401);
                return new Gson().toJson(new ErrorHandler("Error: missing auth token"));
            }
            service.logout(authToken);
            res.status(200);
            return "{}";
        } catch (InvalidUserException e) {
            res.status(401);
            return new Gson().toJson(new ErrorHandler("Error: unauthorized"));
        } catch (DataAccessException e) {
            res.status(500);
            return new Gson().toJson(new ErrorHandler("Error: internal server error"));
        }
    }
}
