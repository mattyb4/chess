package dataaccess;

import model.AuthData;

import java.util.Collection;

import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {
    private Collection<AuthData> db;

    @Override
    public void createAuth(AuthData authData) {
        db.add(authData);
    }

}
