package dataaccess;

import dataaccess.exceptions.DataAccessException;

import java.sql.SQLException;
//this method reduces duplicate code in SQLDAO classes
public class DatabaseStarter {
    public static void startDB(String[] createStatements) {
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
        } catch (SQLException | DataAccessException e) {
            System.out.println("could not connect to db");
            throw new RuntimeException(e);
        }
    }
}
