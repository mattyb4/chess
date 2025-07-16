package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MemoryGameDAO implements GameDAO {
    private Collection<GameData> db;
    public MemoryGameDAO() {
        this.db = new ArrayList<>();
    }
    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public int createGame(String gameName) throws DataAccessException {
        int newID = 1;
        while (newID < 10000) {
            boolean taken = false;
            for (GameData game : db) {
                if (game.gameID() == newID) {
                    taken = true;
                    break;
                }
            }
            if(!taken) {
                GameData newGame = new GameData(newID, null, null, gameName, new ChessGame());
                db.add(newGame);
                return newID;
            }
            newID++;
        }
        return 0;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        for (GameData game : db) {
            if (game.gameID() == gameID) {
                return game;
            }
        }
        //if for loop doesn't find game
        return null;
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return db;
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {

    }

    @Override
    public GameData getUsername(String playerColor) throws DataAccessException {
        return null;
    }
}
