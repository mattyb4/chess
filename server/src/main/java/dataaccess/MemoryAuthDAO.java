package dataaccess;

import dataaccess.exceptions.DataAccessException;
import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryAuthDAO implements AuthDAO {
    private Collection<AuthData> db; //simulate db without actually having one
    public MemoryAuthDAO(){
        this.db = new ArrayList<>();
    }

    @Override
    public void clear() throws DataAccessException {
        db.clear();
    }

    @Override
    public void createAuth(AuthData authData) {
        db.add(authData);
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        for (AuthData token : db) {
            if(token.authToken().equals(authToken)) {
                return token;
            }
        }
        //if for loop doesn't find matching token
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        AuthData token = getAuth(authToken);
        db.remove(token);
    }

}
