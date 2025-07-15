package service;

import dataaccess.AlreadyTakenException;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import handler.RegisterRequest;
import handler.RegisterResult;
import model.AuthData;
import model.UserData;
import java.util.UUID;



public class UserService {
    UserDAO userDAO;
    AuthDAO authDAO;


    public AuthData register(UserData userData) throws DataAccessException, AlreadyTakenException {
        if (userDAO.getUser(userData.username()) != null) {
            throw new AlreadyTakenException("Error: Username already taken");
        }
        else {
            String authToken = UUID.randomUUID().toString();
            var authData = new AuthData(authToken, userData.username());
            authDAO.createAuth(authData);
            return authData;
        }
    }

}
