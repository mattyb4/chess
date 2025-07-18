package dataaccess;

import model.UserData;

public interface UserDAO {
    void clear() throws DataAccessException;
    void createUser(UserData user) throws DataAccessException;
    UserData getUser(String username) throws DataAccessException;
    UserData getUserInfo(String username, String password) throws DataAccessException;
}
