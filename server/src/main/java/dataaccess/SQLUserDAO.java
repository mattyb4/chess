package dataaccess;

import model.UserData;

public class SQLUserDAO implements UserDAO {
    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public void createUser(UserData user) throws DataAccessException {

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public UserData getUserInfo(String username, String password) throws DataAccessException {
        return null;
    }
}
