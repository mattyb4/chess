package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.List;

public class SQLGameDAO implements GameDAO {
    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public int createGame(String gameName) throws DataAccessException {
        return 0;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return List.of();
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {

    }

    @Override
    public String getUsername(String playerColor, int gameID) throws DataAccessException {
        return null;
    }
}
