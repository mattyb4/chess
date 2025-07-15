package handler;

import dataaccess.DataAccessException;
import service.UserService;
import spark.Request;
import spark.Response;

public class ClearHandler {
    private final UserService service;

    public ClearHandler(UserService service) {
        this.service = service;
    }

    public String clear(Request req, Response res) throws DataAccessException {
        service.clear();
        return "{}";
    }
}
