package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.UserData;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

public class SQLGameDAO implements GameDAO {

    public SQLGameDAO() {
        String[] createStatements = {
                """
                CREATE TABLE IF NOT EXISTS game (
                `gameID` INT NOT NULL AUTO_INCREMENT,
                `whiteUsername` varchar(256),
                `blackUsername` varchar(256),
                `gameName` varchar(256),
                `gameData` TEXT,
                PRIMARY KEY (`gameID`)
                )
                """
        };
        try {DatabaseManager.createDatabase(); }
        catch (DataAccessException e) {
            System.out.println("could not create db");
            throw new RuntimeException(e);
        }
        try (var conn = DatabaseManager.getConnection()) {
            for(var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException | DataAccessException ex) {
            System.out.println("could not connect to db");
            throw new RuntimeException(ex);
        }
    }
    @Override
    public void clear() throws DataAccessException {
        String statement = "TRUNCATE TABLE game";
        try (var conn = DatabaseManager.getConnection();
             var ps = conn.prepareStatement(statement)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Error clearing user table", ex);
        }
    }

    @Override
    public int createGame(String gameName) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO game (gameName, gameData) VALUES (?, ?)";
            try (var ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, gameName);
                ps.setString(2, new Gson().toJson(new ChessGame()));
                ps.executeUpdate();
                try (var rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    } else {
                        throw new DataAccessException("Failed to return GameID");
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error creating game in db");
        }
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT whiteUsername, blackUsername, gameName, gameData FROM game WHERE gameID = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String whiteUsername = rs.getString("whiteUsername");
                        String blackUsername = rs.getString("blackUsername");
                        String gameName = rs.getString("gameName");
                        String gameData = rs.getString("gameData");
                        var chessGame = new Gson().fromJson(gameData, ChessGame.class);
                        return new GameData(gameID, whiteUsername, blackUsername, gameName, chessGame);
                    } else {
                        return null; //game not found
                    }
                }

            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error retrieving user from db", ex);
        }
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
