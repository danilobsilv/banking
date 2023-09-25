package src.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTables {

    public void createTables(){
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:bankApp.db");
            System.out.println("Database connection established.");

            statement = connection.createStatement();

            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_email TEXT NOT NULL, " +
                    "username TEXT NOT NULL," +
                    "password TEXT NOT NULL)";
            statement.executeUpdate(createUsersTable);

            String createBankAccountTable = "CREATE TABLE IF NOT EXISTS bankAccount (" +
                    "account_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "account_number TEXT UNIQUE NOT NULL," +
                    "balance REAL NOT NULL," +
                    "account_type TEXT NOT NULL," +
                    "creation_date TEXT NOT NULL," +
                    "user_id INTEGER NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE" +
                    ")";
            statement.executeUpdate(createBankAccountTable);

            String createInvestmentsTable = "CREATE TABLE IF NOT EXISTS investments (" +
                    "investment_id INTEGER PRIMARY KEY NOT NULL," +
                    "investment_name TEXT NOT NULL," +
                    "investment_amount REAL NOT NULL," +
                    "annual_return_date TEXT NOT  NULL)";
            statement.executeUpdate(createInvestmentsTable);

            String createUserInvestmentsTable = "CREATE TABLE IF NOT EXISTS userInvestments (" +
                    "investment_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "user_id INTEGER UNIQUE  NOT NULL,"+
                    "start_date TEXT NOT NULL," +
                    "end_date TEXT NOT NULL,"+
                    "FOREIGN KEY (investment_id) REFERENCES investments(investment_id),"+
                    "FOREIGN KEY (user_id) REFERENCES users(user_id))";
            statement.executeUpdate(createUserInvestmentsTable);

            String createInvestmentsBankAccountTable = "CREATE TABLE IF NOT EXISTS investmentBankAccount (" +
                    "investiment_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "account_id INTEGER UNIQUE NOT NULL," +
                    "FOREIGN KEY (investiment_id) REFERENCES investments(investment_id)," +
                    "FOREIGN KEY (account_id) REFERENCES bankAccount(account_id))";
            statement.executeUpdate(createInvestmentsBankAccountTable);

            String createLoanTable = "CREATE TABLE IF NOT EXISTS loan (" +
                    "loan_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "loan_amount INTEGER NOT NULL," +
                    "annual_interest_rate REAL NOT NULL," +
                    "loan_term_months INTEGER NOT NULL," +
                    "request_date TEXT NOT NULL," +
                    "loan_date TEXT NOT NULL," +
                    "loan_status TEXT NOT NULL," +
                    "account_id INTEGER NOT NULL," +
                    "user_id INTEGER NOT NULL," +
                    "FOREIGN KEY (account_id) REFERENCES bankAccount(account_id)," +
                    "FOREIGN KEY (user_id) REFERENCES users(user_id))";
            statement.executeUpdate(createLoanTable);

            String createTransactionsTable = "CREATE TABLE IF NOT EXISTS transactions(" +
                    "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "transaction_amount REAL NOT NULL," +
                    "transaction_type TEXT NOT NULL," +
                    "user_id INTEGER NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES users(user_id))";
            statement.executeUpdate(createTransactionsTable);

            String createTransactionsBankAccountTable = "CREATE TABLE IF NOT EXISTS transactionsBankAccount(" +
                    "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "account_id INTEGER UNIQUE NOT NULL," +
                    "transfer_date TEXT NOT NULL," +
                    "FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id), " +
                    "FOREIGN KEY (account_id) REFERENCES bankAccount(account_id))";
            statement.executeUpdate(createTransactionsBankAccountTable);

            System.out.println("Tables created successfully.");
        }
        catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error when creating tables: " + e.getMessage());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro closing connection: " + e.getMessage());
            }
        }
    }
}
