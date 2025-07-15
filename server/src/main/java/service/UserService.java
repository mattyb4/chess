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
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

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

    public void clear() throws DataAccessException {
        userDAO.clear();
        authDAO.clear();
    }

}
