package src.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTables {

    public void createTables() {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:bankApp.db");
            System.out.println("Database connection established.");

            statement = connection.createStatement();

            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "userId INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "userEmail TEXT NOT NULL, " +
                    "username TEXT NOT NULL," +
                    "password TEXT NOT NULL," +
                    "datestamp TEXT NOT NULL)";
            statement.executeUpdate(createUsersTable);

            String createBankAccountTable = "CREATE TABLE IF NOT EXISTS bankAccount (" +
                    "accountId INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "accountNumber TEXT UNIQUE NOT NULL," +
                    "balance REAL NOT NULL," +
                    "accountType TEXT NOT NULL," +
                    "creationDate TEXT NOT NULL," +
                    "userId INTEGER NOT NULL," +
                    "FOREIGN KEY (userId) REFERENCES users(userId) ON DELETE CASCADE" +
                    ")";
            statement.executeUpdate(createBankAccountTable);

            String createInvestmentsTable = "CREATE TABLE IF NOT EXISTS investments (" +
                    "investmentId INTEGER PRIMARY KEY NOT NULL," +
                    "investmentName TEXT NOT NULL," +
                    "investmentAmount REAL NOT NULL," +
                    "annualReturnDate TEXT NOT NULL" +
                    "startDate TEXT NOT NULL" +
                    "endDate TEXT NOT NULL)";
            statement.executeUpdate(createInvestmentsTable);

            String createUserInvestmentsTable = "CREATE TABLE IF NOT EXISTS userInvestments (" +
                    "investmentId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "userId INTEGER UNIQUE NOT NULL," +
                    "startDate TEXT NOT NULL," +
                    "endDate TEXT NOT NULL," +
                    "FOREIGN KEY (investmentId) REFERENCES investments(investmentId)," +
                    "FOREIGN KEY (userId) REFERENCES users(userId))";
            statement.executeUpdate(createUserInvestmentsTable);

            String createInvestmentsBankAccountTable = "CREATE TABLE IF NOT EXISTS investmentBankAccount (" +
                    "investmentId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "accountId INTEGER UNIQUE NOT NULL," +
                    "FOREIGN KEY (investmentId) REFERENCES investments(investmentId)," +
                    "FOREIGN KEY (accountId) REFERENCES bankAccount(accountId))";
            statement.executeUpdate(createInvestmentsBankAccountTable);

            String createLoanTable = "CREATE TABLE IF NOT EXISTS loan (" +
                    "loanId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "loanAmount INTEGER NOT NULL," +
                    "annualInterestRate REAL NOT NULL," +
                    "loanTermMonths INTEGER NOT NULL," +
                    "requestDate TEXT NOT NULL," +
                    "loanStatus TEXT NOT NULL," +
                    "accountId INTEGER NOT NULL," +
                    "userId INTEGER NOT NULL," +
                    "FOREIGN KEY (accountId) REFERENCES bankAccount(accountId)," +
                    "FOREIGN KEY (userId) REFERENCES users(userId))";
            statement.executeUpdate(createLoanTable);

            String createTransactionsTable = "CREATE TABLE IF NOT EXISTS transactions(" +
                    "transactionId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "transactionAmount REAL NOT NULL," +
                    "transactionType TEXT NOT NULL," +
                    "userId INTEGER NOT NULL," +
                    "FOREIGN KEY (userId) REFERENCES users(userId))";
            statement.executeUpdate(createTransactionsTable);

            String createTransactionsBankAccountTable = "CREATE TABLE IF NOT EXISTS transactionsBankAccount(" +
                    "transactionId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "accountId INTEGER UNIQUE NOT NULL," +
                    "transferDate TEXT NOT NULL," +
                    "FOREIGN KEY (transactionId) REFERENCES transactions(transactionId), " +
                    "FOREIGN KEY (accountId) REFERENCES bankAccount(accountId))";
            statement.executeUpdate(createTransactionsBankAccountTable);

            System.out.println("Tables created successfully.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error when creating tables: " + e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
