package dataaccess;

import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

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
        String statement = "TRUNCATE TABLE auth";
        try (var conn = DatabaseManager.getConnection();
             var ps = conn.prepareStatement(statement)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Error clearing user table", ex);
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
            } catch (SQLException ex) {
                throw new DataAccessException("Error adding auth data to db", ex);
            }
        } catch (SQLException ex) {
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
                        return new AuthData(user, authToken);
                    } else {
                        return null; //user not found
                    }
                }

            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error retrieving user from db", ex);
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
        } catch (SQLException ex) {
            throw new DataAccessException("Error deleting authToken from db", ex);
        }
    }
}
