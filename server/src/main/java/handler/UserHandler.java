package handler;

import com.google.gson.Gson;
import dataaccess.AlreadyTakenException;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;

public class UserHandler {
    UserService service;
    public String register(Request req, Response res) throws DataAccessException, AlreadyTakenException {
        var serializer = new Gson();
        UserData data = serializer.fromJson(req.body(), UserData.class);
        AuthData authData = service.register(data);
        var result = new Gson().toJson(authData);
        res.status(200);
        return result;
    }
}
