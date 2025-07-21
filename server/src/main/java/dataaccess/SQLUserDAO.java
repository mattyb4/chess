package dataaccess;

import com.google.gson.Gson;
import model.UserData;

import java.sql.SQLException;

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
    public UserData getUser(String username) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM user WHERE username =?";
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
        } catch (SQLException ex) {
            throw new DataAccessException("Error retrieving user from db", ex);
        }
    }

    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public void createUser(UserData user) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO user (username, password, email) VALUES (?,?,?)";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, user.username());
                ps.setString(2,user.password());
                ps.setString(3,user.email());
                ps.executeUpdate();
            } catch (SQLException ex) {
                throw new DataAccessException("Error adding user to db", ex);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error accessing db");
        }
    }

    @Override
    public UserData getUserInfo(String username, String password) throws DataAccessException {
        return null;
    }
}
