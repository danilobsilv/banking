package src.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExecuteQuery{

    private final String dbPath = "bankApp.db";

    public void executeQuery(String query, Object... params) throws SQLException {
        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.executeUpdate();
        }
    }
}