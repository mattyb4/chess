package handler;

import com.google.gson.Gson;
import dataaccess.*;
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
        try {
            var serializer = new Gson();
            UserData data = serializer.fromJson(req.body(), UserData.class);
            AuthData authData = service.register(data);
            var result = serializer.toJson(authData);
            res.status(200);
            return result;
        } catch (AlreadyTakenException e) {
            res.status(403);
            return new Gson().toJson(new ErrorHandler("Error: Username already taken"));
        } catch (BadRequestException e) {
            res.status(400);
            return new Gson().toJson(new ErrorHandler("Error: Bad request"));
        }
    }
}
