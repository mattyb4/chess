package dataaccess;

import dataaccess.exceptions.*;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class SQLUserDAO implements UserDAO {

    public SQLUserDAO() {
         String[] createStatements = {
                """
                CREATE TABLE IF NOT EXISTS user (
                `username` varchar(256) NOT NULL,
                `password` varchar(256) NOT NULL,
                `email` varchar(256),
                PRIMARY KEY (`username`),
                INDEX(password),
                INDEX(email)
                )
                """
        };
        DatabaseStarter.startDB(createStatements);
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM user WHERE username = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1,username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String user = rs.getString("username");
                        String password = rs.getString("password");
                        String email = rs.getString("email");
                        return new UserData(user, password, email);
                    } else {
                        return null; //user not found
                    }
                }

            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving user from db", e);
        }
    }

    @Override
    public void clear() throws DataAccessException {
        String statement = "TRUNCATE TABLE user";
        try (var conn = DatabaseManager.getConnection();
            var ps = conn.prepareStatement(statement)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error clearing user table", e);
        }
    }

    @Override
    public void createUser(UserData user) throws DataAccessException, AlreadyTakenException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO user (username, password, email) VALUES (?,?,?)";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, user.username());
                //encrypt password
                String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
                ps.setString(2, hashedPassword);
                ps.setString(3, user.email());
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new AlreadyTakenException("Username already taken");
            } catch (SQLException e) {
                throw new DataAccessException("Error adding user to db", e);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error accessing db", e);
        }
    }

    @Override
    public UserData getUserInfo(String username, String password) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM user WHERE username = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (!rs.next()) { //nothing in the table
                        return null;
                    }
                    String storedHash = rs.getString("password");
                    if(!BCrypt.checkpw(password, storedHash)) { //compares encrypted version of pw
                        return null;
                    }
                    String email = rs.getString("email");
                    return new UserData(username, storedHash, email);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve user info", e);
        }
    }
}
