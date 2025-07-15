package service;

import dataaccess.*;
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

    public AuthData register(UserData userData) throws DataAccessException, AlreadyTakenException, BadRequestException {
        System.out.println("in register in UserService");
        if(userData.username() == null || userData.password() == null || userData.email() == null) {
            throw new BadRequestException("Error: bad request");
        }
        if (userDAO.getUser(userData.username()) != null) {
            throw new AlreadyTakenException("Error: Username already taken");
        }
        else {
            userDAO.createUser(userData);
            String authToken = UUID.randomUUID().toString();
            var authData = new AuthData(authToken, userData.username());
            authDAO.createAuth(authData);
            System.out.println("auth token created");
            return authData;
        }
    }

    public AuthData login(UserData userData) throws DataAccessException, InvalidUserException, BadRequestException {
        if(userData.username() == null || userData.password() == null) {
            throw new BadRequestException("Error: missing username or password");
        }
        if(userDAO.getUserInfo(userData.username(), userData.password()) == null) {
            throw new InvalidUserException("Error: invalid credentials");
        }
        else {
            String authToken = UUID.randomUUID().toString();
            var authData = new AuthData(authToken, userData.username());
            authDAO.createAuth(authData);
            return authData;
        }
    }

    public void logout(String authToken) throws DataAccessException, InvalidUserException {
        if(authDAO.getAuth(authToken) == null) {
            throw new InvalidUserException("Error: unauthorized");
        }
        else {
            authDAO.deleteAuth(authToken);
        }
    }

    public void clear() throws DataAccessException {
        userDAO.clear();
        authDAO.clear();
    }

}
