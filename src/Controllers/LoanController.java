package src.Controllers;

import src.Database.DBConnection;
import src.Database.ExecuteQuery;

import src.Models.Loan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import src.enumerate.LoanStatusTypes;
import src.env.ConfigurationReader;


public class LoanController {

    Properties properties = ConfigurationReader.loadProperties();
    private final String dbPath = properties.getProperty("dbPath", null);
    private final ExecuteQuery executeQuery;

    public LoanController() throws IOException {
        this.executeQuery = new ExecuteQuery();
    }

    public void createLoan(Loan loan, Integer accountId, Integer userId) throws SQLException {
        String query = "INSERT INTO loan (loanAmount, annualInterestRate, loanTermMonths, requestDate, loanStatus, accountId, userId) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            this.executeQuery.executeQuery(query, loan.getLoanAmount(), loan.getAnnualInterestRate(), loan.getLoanTermMonths(), loan.getRequestDate(), loan.getLoanStatus(), accountId, userId);
            System.out.println("Loan successfully created.");
        }
        catch (SQLException Error){
            System.err.println("Error creating loan: " + Error.getMessage());
            throw Error;

        }
    }

    public void deleteLoan(int loanId) throws SQLException {
        String query = "DELETE FROM loan WHERE loanId = ?";

        try{
            this.executeQuery.executeQuery(query, loanId);
            System.out.println("Loan successfully deleted!");
        }
        catch (SQLException Error){
            System.err.println("Error deleting loan: " + Error.getMessage());
            throw Error;
        }
    }


    public void updateLoanStatus(int loanId, LoanStatusTypes newStatus) throws SQLException{
        String query = "UPDATE loan SET loanStatus = ? WHERE loanId = ?";
        try{
            this.executeQuery.executeQuery(query, newStatus, loanId);
            System.out.println("Loan status successfully updated.");
        } catch (SQLException Error) {
            System.err.println("Error updating loan status: " + Error.getMessage());
            throw Error;
        }
    }

    public Loan getLoanById(int loanId) throws SQLException {
        String query = "SELECT * FROM loan WHERE loanId = ?";
        Loan loan = null;

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, loanId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return this.executeQuery.mapLoanFromResultSet(resultSet);
                } else {
                    System.out.println("No loans were found with the given Id = " + loanId);
                }
            }
        } catch (SQLException Error) {
            System.err.println("Error getting loan: " + Error.getMessage());
            throw Error;
        }

        return loan;
    }

    public List<Loan> getLoansByUser(int userId) throws SQLException {
        List<Loan> loanArray = new ArrayList<>();
        String query = "SELECT * FROM loan WHERE userId = ?";

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    loanArray.add(this.executeQuery.mapLoanFromResultSet(resultSet));
                } else {
                    System.out.println("No loans were found for this Id: " + userId);
                }
            } catch (SQLException Error) {
                System.err.println("Error getting bank accounts: " + Error.getMessage());
                throw Error;
            }
            return loanArray;
        }
    }

    public List<Loan> getLoansByAccountId(int accountId) throws SQLException {
        List<Loan> loanArray = new ArrayList<>();
        String query = "SELECT * FROM loan WHERE accountId = ?";

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    loanArray.add(this.executeQuery.mapLoanFromResultSet(resultSet));
                } else {
                    System.out.println("No loans were found for this account Id: " + accountId);
                }
            } catch (SQLException Error) {
                System.err.println("Error getting loans: " + Error.getMessage());
                throw Error;
            }
            return loanArray;
        }
    }

    public float calculateMonthlyPaymentForLoan(int loanId) throws SQLException {
        String query = "SELECT loanAmount, annualInterestRate, loanTermMonths FROM loan WHERE loanId = ?";

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, loanId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    float loanAmount = resultSet.getFloat("loanAmount");
                    float annualInterestRate = resultSet.getFloat("annualInterestRate");
                    int loanTermMonths = resultSet.getInt("loanTermMonths");

                    float monthlyPayment = calculateMonthlyPayment(loanAmount, annualInterestRate, loanTermMonths);

                    return monthlyPayment;
                }
            }
        } catch (SQLException error) {
            System.err.println("Error calculating monthly payment for loan: " + error.getMessage());
            throw error;
        }

        return 0.0f;
    }

    private float calculateMonthlyPayment(float loanAmount, float annualInterestRate, int loanTermMonths) {
        double monthlyInterestRate = annualInterestRate / 12.0;
        double denominator = Math.pow(1 + monthlyInterestRate, loanTermMonths) - 1;
        double monthlyPayment = (loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTermMonths)) / denominator;

        return (float) monthlyPayment;
    }

    public List<Loan> getLoanStatus(LoanStatusTypes loanStatus) throws SQLException {
        List<Loan> loanArray = new ArrayList<>();
        String query = "SELECT * FROM loan WHERE loanStatus = ?";

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, loanStatus.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    loanArray.add(this.executeQuery.mapLoanFromResultSet(resultSet));
                }
            }
        } catch (SQLException Error) {
            System.err.println("Error getting transactions: " + Error.getMessage());
            throw Error;
        }
        return loanArray;
    }
}