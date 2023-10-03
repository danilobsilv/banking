package src.Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import src.Database.DBConnection;
import src.Database.ExecuteQuery;
import src.Models.BankAccount;
import src.enumerate.AccountType;

import java.sql.SQLException;
import java.util.Date;

public class BankAccountController {
    private final String dbPath = "bankApp.db";

    ExecuteQuery executeQuery = new ExecuteQuery();

    public BankAccountController() {
    }

    public void createBankAccount(BankAccount bankAccount, String userEmail, int userId) throws SQLException {
        String query = "INSERT INTO bankAccount (accountNumber, balance, accountType, creationDate, userId) VALUES (?, ?, ?, ?, ?)";

        try {
            executeQuery.executeQuery(query, bankAccount.getAccountNumber(), bankAccount.getBalance(), bankAccount.getAccountType(), bankAccount.getDateCreated(), userId);
            System.out.println("Bank Account successfully created!");
        } catch (SQLException Error) {
            System.err.println("Error creating Bank Account: " + Error.getMessage());
            throw Error;
        }
    }

    public void deleteAccount(){
        // todo
    }
}