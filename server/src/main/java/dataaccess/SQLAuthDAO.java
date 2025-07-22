package dataaccess;

import dataaccess.Exceptions.DataAccessException;
import model.AuthData;

import java.sql.SQLException;

public class SQLAuthDAO implements AuthDAO{

    public SQLAuthDAO() {
        String[] createStatements = {
                """
                CREATE TABLE IF NOT EXISTS auth (
                `username` varchar(256) NOT NULL,
                `authToken` varchar(256) NOT NULL,
                PRIMARY KEY (`authToken`),
                INDEX(username)
                )
                """
        };
        DatabaseStarter.startDB(createStatements);
    }
    @Override
    public void clear() throws DataAccessException {
        String statement = "TRUNCATE TABLE auth";
        try (var conn = DatabaseManager.getConnection();
             var ps = conn.prepareStatement(statement)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error clearing user table", e);
        }
    }

    @Override
    public void createAuth(AuthData authData) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO auth (username, authToken) VALUES (?,?)";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authData.username());
                ps.setString(2, authData.authToken());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException("Error adding auth data to db", e);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error accessing db");
        }
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, authToken FROM auth WHERE authToken = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String user = rs.getString("username");
                        return new AuthData(authToken, user);
                    } else {
                        return null; //authdata not found
                    }
                }

            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving user from db", e);
        }
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "DELETE FROM auth WHERE authToken = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting authToken from db", e);
        }
    }
}
