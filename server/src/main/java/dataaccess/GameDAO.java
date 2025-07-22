package dataaccess;

import dataaccess.exceptions.DataAccessException;
import model.GameData;

import java.util.Collection;

public interface GameDAO {
    void clear() throws DataAccessException;
    int createGame(String gameName) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    Collection<GameData> listGames() throws DataAccessException;
    void updateGame(GameData game) throws DataAccessException;
    String getUsername(String playerColor, int gameID) throws DataAccessException;
}
