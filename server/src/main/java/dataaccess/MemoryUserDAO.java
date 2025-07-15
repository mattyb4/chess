package dataaccess;

import model.UserData;

import java.util.Collection;

public class MemoryUserDAO implements UserDAO {
    private Collection<UserData> db; //simulate db without actually having one

    @Override
    public UserData getUser(String username) throws DataAccessException {
        for (UserData userInfo : db) {
            if(userInfo.username().equals(username)){
                return userInfo;
            }
        }
        //if for loop doesn't find user
        throw new DataAccessException("could not find user");
    }

    @Override
    public UserData getUserInfo(String username, String password) throws DataAccessException {
        return null;
    }

    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public void createUser(UserData user) {
        db.add(user);
    }
}
