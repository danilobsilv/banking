package src.Database;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExecuteQuery {
    public void executeQuery(String query, Object... params) throws SQLException {
        final String dbPath = "bankApp.db";
        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.executeUpdate();
        }
    }

    public boolean emailExists(String dbPath, String query, String email) throws SQLException {
        int count = 0;

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    count = ((ResultSet) resultSet).getInt(1);
                }
            }
        }
        return count > 0;
    }

    public boolean passwordExists(String dbPath, String query, String password) throws SQLException {
        int count = 0;

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    count = ((ResultSet) resultSet).getInt(1);
                }
            }
        }
        return count > 0;
    }
}