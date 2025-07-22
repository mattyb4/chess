package dataaccess;

import chess.ChessGame;
import dataaccess.Exceptions.DataAccessException;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryGameDAO implements GameDAO {
    private Collection<GameData> db;
    public MemoryGameDAO() {
        this.db = new ArrayList<>();
    }
    @Override
    public void clear() throws DataAccessException {
        db.clear();
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
        db.remove(getGame(game.gameID()));
        db.add(game);
    }

    @Override
    public String getUsername(String playerColor, int gameID) throws DataAccessException {
        var game = getGame(gameID);
        if(playerColor.equals("WHITE")) {
            return game.whiteUsername();
        }
        if (playerColor.equals("BLACK")) {
            return game.blackUsername();
        }
        else {
            return null;
        }
    }
}
