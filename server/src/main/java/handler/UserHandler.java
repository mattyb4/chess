package handler;

import com.google.gson.Gson;
import dataaccess.AlreadyTakenException;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
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

    public String register(Request req, Response res) throws DataAccessException, AlreadyTakenException {
        var serializer = new Gson();
        UserData data = serializer.fromJson(req.body(), UserData.class);
        AuthData authData = service.register(data);
        var result = new Gson().toJson(authData);
        res.status(200);
        return result;
    }
}
