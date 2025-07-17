package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
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
            return gameDAO.listGames().stream().map(game -> new GameSumm(
                            game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName())).toList();

        }
    }

    public void join(int gameID, String playerColor, String authToken)
            throws DataAccessException, InvalidUserException, AlreadyTakenException, InvalidInputException, BadRequestException {
        String newWhiteUsername = "";
        String newBlackUsername = "";
        AuthData authData = authDAO.getAuth(authToken);
        if(authData == null) {
            throw new InvalidUserException("Error: unauthorized");
        }
        if(playerColor == null || !playerColor.equals("WHITE") && !playerColor.equals("BLACK")){
            throw new BadRequestException("Error: valid color not specified");
        }
        if(gameDAO.getGame(gameID) == null) {
            throw new InvalidInputException("Error: game not found");
        }
        if (gameDAO.getUsername(playerColor, gameID) != null) {
            throw new AlreadyTakenException("Error: color already taken");
        }
        else {
            GameData currentGame = gameDAO.getGame(gameID);
            if(playerColor.equals("WHITE")) {
                newWhiteUsername = authData.username();
                newBlackUsername = gameDAO.getUsername("BLACK",gameID);
            }
            if(playerColor.equals("BLACK")) {
                newBlackUsername = authData.username();
                newWhiteUsername = gameDAO.getUsername("WHITE", gameID);
            }
            var updatedGame = new GameData(gameID, newWhiteUsername, newBlackUsername, currentGame.gameName(), currentGame.game());
            gameDAO.updateGame(updatedGame);
        }
    }

    public void clear() throws DataAccessException {
        gameDAO.clear();
    }

}
