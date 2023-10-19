package src.Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import src.Database.DBConnection;
import src.Database.ExecuteQuery;

import src.Models.BankAccount;
import src.env.ConfigurationReader;

public class BankAccountController {

    Properties properties = ConfigurationReader.loadProperties();
    private final String dbPath = properties.getProperty("dbPath", null);
    private final ExecuteQuery executeQuery;


    public BankAccountController() throws IOException {
        this.executeQuery = new ExecuteQuery();
    }

    public void createBankAccount(BankAccount bankAccount, int userId) throws SQLException {
        String query = "INSERT INTO bankAccount (accountNumber, balance, accountType, creationDate, userId) VALUES (?, ?, ?, ?, ?)";

        try {
            this.executeQuery.executeQuery(query, bankAccount.getAccountNumber(), bankAccount.getBalance(), bankAccount.getAccountType(), bankAccount.getDateCreated(), userId);
            System.out.println("Bank Account successfully created!");
        } catch (SQLException Error) {
            System.err.println("Error creating Bank Account: " + Error.getMessage());
            throw Error;
        }
    }

    public void deleteAccount(int accountId) throws Exception {
        String query = "DELETE FROM bankAccount WHERE accountId = ?";

        try{
            this.executeQuery.executeQuery(query, accountId);
            System.out.println("Bank Account successfully deleted!");
        }
        catch (Exception Error){
            System.err.println("Error deleting Bank Account: " + Error.getMessage());
            throw Error;
        }
    }

    public List<BankAccount> getBankAccounts() throws SQLException{
        List<BankAccount> bankAccountArray = new ArrayList<>();
        String query = "SELECT * FROM bankAccount";

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()){

                while (resultSet.next()){
                    bankAccountArray.add(this.executeQuery.mapBankAccountFromResultSet(resultSet));
                }
        }
        catch (SQLException Error){
            System.err.println("Error getting bank accounts: " + Error.getMessage());
            throw Error;
        }
        return bankAccountArray;
    }

    public void showBankAccounts() throws SQLException {
        List<BankAccount> bankAccountsArray = getBankAccounts();

        if (bankAccountsArray.isEmpty()){
            System.out.println("No bank accounts were found.");
        }
        else{
            System.out.println("Bank Accounts list:\n ");
            for (BankAccount bankAccount: bankAccountsArray){
                System.out.println("Account number: " + bankAccount.getAccountNumber());
                System.out.println("Balance: R$ " + bankAccount.getBalance());
                System.out.println("Account type: " + bankAccount.getAccountType());
                System.out.println("Creation date: " + bankAccount.getDateCreated());
                System.out.println("-----------------------");
            }
        }
    }

    public BankAccount getAccountById(int bankAccountId) throws SQLException {
        String query = "SELECT * FROM BankAccount where accountId = ?";
        try(Connection connection = DBConnection.getConnection(dbPath);
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, bankAccountId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return this.executeQuery.mapBankAccountFromResultSet(resultSet);
                }
            }
        }
        catch (SQLException Error) {
            System.err.println("Error getting user: " + Error.getMessage());
            throw Error;
        }
        return null;
    }

    public void updateBalance(float amount, int userId) throws SQLException {
        try (Connection connection = DBConnection.getConnection(dbPath)) {
            String selectQuery = "SELECT balance FROM bankAccount WHERE userId = ?";
            float currentBalance = 0.0f;

            try (PreparedStatement selectQueryStatement = connection.prepareStatement(selectQuery)) {
                selectQueryStatement.setInt(1, userId);

                try (ResultSet resultSet = selectQueryStatement.executeQuery()) {
                    if (resultSet.next()) {
                        currentBalance = resultSet.getFloat("balance");
                    }
                }
            }
            float newBalance = currentBalance + amount;
            String updateQuery = "UPDATE bankAccount SET balance = ? WHERE userId = ?";

            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setFloat(1, newBalance);
                updateStatement.setInt(2, userId);
                updateStatement.executeUpdate();
                System.out.println("Balance updated successfully!");
            }
        } catch (SQLException Error) {
            System.err.println("Error updating balance: " + Error.getMessage());
            throw Error;
        }
    }

    public float getBalanceById(String accountNumber) throws SQLException{
        String query = "SELECT balance FROM bankAccount WHERE accountNumber = ?";
        float balance = 0.0f;

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query))  {
            preparedStatement.setString(1, accountNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getFloat("balance");
                }
            }
        }
        catch (SQLException Error){
            System.err.println("Error getting account's balance: " + Error.getMessage());
            throw Error;
        }
        return balance;
    }

}