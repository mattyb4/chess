package dataaccess;

import model.AuthData;

import java.util.Collection;

import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {
    private Collection<AuthData> db;

    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public void createAuth(AuthData authData) {
        db.add(authData);
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }

}
