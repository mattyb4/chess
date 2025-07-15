package handler;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;

public class UserHandler {
    UserService service;
    public String register(Request req, Response res) {
        var serializer = new Gson();
        UserData data = serializer.fromJson(req.body(), UserData.class);
        var registerRequest = new RegisterRequest(data.username(),data.password(),data.email());
        AuthData authData =
    }
}
