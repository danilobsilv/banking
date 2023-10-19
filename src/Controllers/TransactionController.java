package src.Controllers;

import src.Database.DBConnection;
import src.Database.ExecuteQuery;

import src.Models.Transaction;

import src.enumerate.TransactionType;
import src.env.ConfigurationReader;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class TransactionController {

    Properties properties = ConfigurationReader.loadProperties();
    private final String dbPath = properties.getProperty("DATABASE_PATH");
    ExecuteQuery executeQuery;
    public TransactionController() throws IOException {
        this.executeQuery = new ExecuteQuery();
    }

    public void createTransaction(Transaction transaction, Integer userId) throws SQLException{
        String query = "INSERT INTO transactions (transactionAmount, transactionType, userId) VALUES (?, ?, ?)";

        try{
            this.executeQuery.executeQuery(query, transaction.getAmount(), transaction.getTransactionType(), userId);
            System.out.println("Transaction successfully created!");
        }
        catch (SQLException Error){
            System.err.println("Error creating transaction: " + Error.getMessage());
            throw Error;
        }
    }

    public Transaction getTransactionByUserId(Integer userId) throws SQLException {
        Transaction transaction = null;
        String query = "SELECT * FROM transaction WHERE userId = ?";

        try(Connection connection = DBConnection.getConnection(dbPath);
            PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    transaction = this.executeQuery.mapTransactionFromResultSet(resultSet);
                }
            }
        }
        catch (SQLException Error){
            System.err.println("Error getting transaction: " + Error.getMessage());
            throw Error;
        }
        return transaction;
    }


    public Transaction getTransactionById(Integer transactionId) throws SQLException{
        Transaction transaction = null;
        String query = "SELECT * FROM transactions WHERE transactionId = ?";

        try(Connection connection = DBConnection.getConnection(dbPath);
            PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setInt(1, transactionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    transaction = this.executeQuery.mapTransactionFromResultSet(resultSet);
                }
            }
        }
        catch (SQLException Error){
            System.err.println("Error getting transaction: " + Error.getMessage());
            throw Error;
        }
        return transaction;
    }

    public List<Transaction> getTransactionsByType(TransactionType transactionType) throws SQLException {
        List<Transaction> transactionsArray = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE transactionType = ?";

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, transactionType.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    transactionsArray.add(this.executeQuery.mapTransactionFromResultSet(resultSet));
                }
            }
        } catch (SQLException Error) {
            System.err.println("Error getting transactions: " + Error.getMessage());
            throw Error;
        }
        return transactionsArray;
    }
}