package dataaccess;

import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryUserDAO implements UserDAO {
    private Collection<UserData> db; //simulate db without actually having one
    public MemoryUserDAO(){
        this.db = new ArrayList<>();
    }
    @Override
    public UserData getUser(String username) throws DataAccessException {
        for (UserData userInfo : db) {
            if(userInfo.username().equals(username)){
                return userInfo;
            }
        }
        //if for loop doesn't find user
        return null;
    }

    @Override
    public UserData getUserInfo(String username, String password) throws DataAccessException {
        return null;
    }

    @Override
    public void clear() throws DataAccessException {
        db.clear();
    }

    @Override
    public void createUser(UserData user) {
        db.add(user);
    }
}
