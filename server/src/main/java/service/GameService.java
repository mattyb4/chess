package service;

import chess.ChessGame;
import dataaccess.*;
import model.GameData;

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

}
