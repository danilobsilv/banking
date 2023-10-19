package src.Database;

import src.Models.*;

import src.enumerate.AccountType;
import src.enumerate.InvestmentTypeNames;
import src.enumerate.LoanStatusTypes;
import src.enumerate.TransactionType;

import java.sql.*;

public class ExecuteQuery {
    final String dbPath = "bankApp.db";
    private void executeQueryInternal(String query, Object... params) throws SQLException {
        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            if (query.toLowerCase().trim().startsWith("select")) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                }
            } else {
                preparedStatement.executeUpdate();
            }
        }
    }

    public void executeQuery(String query, Object... params) throws SQLException {
        executeQueryInternal(query, params);
    }

    public boolean emailExists(String query, String email) throws SQLException {
        int count = 0;
        executeQueryInternal(query, email);
        return count > 0;
    }

    public boolean passwordExists(String query, String password) throws SQLException {
        int count = 0;
        executeQueryInternal(query, password);
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

    public Loan mapLoanFromResultSet(ResultSet resultSet) throws SQLException {
        float loanAmount = resultSet.getFloat("loanAmount");
        float annualInterestRate = resultSet.getFloat("annualInterestRate");
        int loanTermMonths = resultSet.getInt("loanTermMonths");
        long requestDate = resultSet.getLong("requestDate");
        LoanStatusTypes loanStatus = LoanStatusTypes.valueOf(resultSet.getString("loanStatus"));

        Date reqDate = new Date(requestDate);
        return new Loan(loanAmount,annualInterestRate,loanTermMonths, reqDate, loanStatus);
    }

    public Investment mapInvestmentsFromResultSet(ResultSet resultSet) throws SQLException {
        InvestmentTypeNames investmentName = InvestmentTypeNames.valueOf(resultSet.getString("investmentName"));
        float investmentAmount = resultSet.getFloat("investmentAmount");
        float annualReturnDate = resultSet.getFloat("annualReturnDate");
        long startDateSet = resultSet.getLong("startDate");
        long endDateSet = resultSet.getLong("endDate");

        Date startDate = new Date(startDateSet);
        Date endDate = new Date(endDateSet);
        return new Investment(investmentName, investmentAmount, annualReturnDate, startDate, endDate);
    }

    public Transaction mapTransactionFromResultSet(ResultSet resultSet) throws SQLException {
        float transactionAmount = resultSet.getFloat("transactionAmount");
        String transactionType = resultSet.getString("transactionType");
        TransactionType transactionTypeValue = TransactionType.valueOf(transactionType);

        return new Transaction(transactionAmount, transactionTypeValue);
    }


    public Transaction mapTransactionFromTransactionId(Integer transactionId) throws SQLException {
        String getTransactionQuery = "SELECT * FROM transactions WHERE transactionId = ?";

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(getTransactionQuery)) {
            preparedStatement.setInt(1, transactionId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    float transactionAmount = resultSet.getFloat("transactionAmount");
                    String transactionType = resultSet.getString("transactionType");

                    TransactionType transactionTypeValue = TransactionType.valueOf(transactionType);

                    return new Transaction(transactionAmount, transactionTypeValue);
                }
            }
        } catch (SQLException Error) {
            System.err.println("Error mapping transaction from transaction ID: " + Error.getMessage());
            throw Error;
        }
        return null;
    }
}