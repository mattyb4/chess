package dataaccess;

import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.DataAccessException;
import model.UserData;

public interface UserDAO {
    void clear() throws DataAccessException;
    void createUser(UserData user) throws DataAccessException, AlreadyTakenException;
    UserData getUser(String username) throws DataAccessException;
    UserData getUserInfo(String username, String password) throws DataAccessException;
}
