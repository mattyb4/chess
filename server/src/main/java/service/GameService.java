package service;

import chess.ChessGame;
import dataaccess.*;
import model.GameData;
import model.GameSumm;

import java.util.Collection;

public class GameService {
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public GameService(GameDAO gameDAO, AuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public GameData create(String gameName, String authToken) throws DataAccessException, BadRequestException, InvalidUserException {
        if(gameName == null) {
            throw new BadRequestException("Error: no game name");
        }
        if(authDAO.getAuth(authToken) == null) {
            throw new InvalidUserException("Error: unauthorized");
        }
        else {
            int gameID = gameDAO.createGame(gameName);
            var newGame = new GameData(gameID,null,null,gameName, new ChessGame());
            return newGame;
        }
    }

    public Collection<GameSumm> listAllGames(String authToken) throws DataAccessException, InvalidUserException {
        if(authDAO.getAuth(authToken) == null) {
            throw new InvalidUserException("Error: unauthorized");
        }
        else {
            return gameDAO.listGames().stream()
                    .map(game -> new GameSumm(
                            game.gameID(),
                            game.whiteUsername(),
                            game.blackUsername(),
                            game.gameName()
                    ))
                    .toList();

        }
    }

}
