package src.Controllers;

import src.Database.ExecuteQuery;

import src.Models.Loan;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import src.Database.ExecuteQuery;

import src.enumerate.LoanStatusTypes;

public class LoanController {

    private final ExecuteQuery executeQuery;
    private final String dbPath = "bankApp.db";

    public LoanController(){
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

    public void deleteLoan(int loanId){
        // todo
    }

    public void updateLoanStatus(int loanId, LoanStatusTypes newStatus){
        // todo
    }

    public Loan getLoanById(int loanId){
        // todo
    }

    public List<Loan> getLoansByUser(int userId){
        List<Loan> loanList = new ArrayList<>();
        // todo
    }

    public List<Loan> getLoansByAccountId(int accountId){
        List<Loan> loanList = new ArrayList<>();
        // todo
    }

    public void calculateMonthlyPayment(Loan loan){
        // todo
    }

    public List<Loan> getActiveLoans(){
        List<Loan> loanList = new ArrayList<>();
        // todo
    }

    public List<Loan> getPaidLoans(){
        List<Loan> loanList = new ArrayList<>();
        // todo
    }
}
