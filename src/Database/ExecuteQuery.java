package src.Database;

import src.Models.User;
import src.Models.BankAccount;
import src.enumerate.AccountType;

import java.sql.*;

public class ExecuteQuery implements AutoCloseable{
    final String dbPath = "bankApp.db";
    public void executeQuery(String query, Object... params) throws SQLException {

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.executeUpdate();
        }
    }

        public boolean emailExists(String query, String email) throws SQLException {
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

    public boolean passwordExists(String query, String password) throws SQLException {
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

    public boolean userIdBelongsToEmail(String email, int userId) throws SQLException {
        int count = 0;
        String query = "SELECT COUNT(*) FROM users WHERE userEmail = ? AND userId = ?";

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException Error) {
            System.err.println("Error executing query: " + Error.getMessage());
            throw Error;
        }

        return count > 0;
    }



    public boolean checkUserIdExists(int userId) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE userId = ?";
        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    @Override
    public void close() throws Exception {
        // todo
    }

    public int executeIntQuery(String query, Object... params) throws SQLException {
        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        throw new SQLException("Query found no results.");
    }

    public User mapUserFromResultSet(ResultSet resultSet) throws SQLException {
        String user_email = resultSet.getString("userEmail");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        long creationDateMillis = resultSet.getLong("datestamp");

        Date creationDate = new Date(creationDateMillis);
        return new User(username, user_email, password, creationDate);
    }

    public BankAccount mapBankAccountFromResultSet(ResultSet resultSet) throws SQLException{
        String accountNUmber = resultSet.getString("accountNumber");
        float balance = resultSet.getFloat("balance");
        AccountType accountType = AccountType.valueOf(resultSet.getString("accountType"));
        long creationDateMillis = resultSet.getLong("creationDate");

        Date creationDate = new Date(creationDateMillis);
        return new BankAccount(accountNUmber, balance, accountType, creationDate);
    }

}