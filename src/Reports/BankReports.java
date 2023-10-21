package src.Reports;

import src.env.ConfigReader;

import src.Controllers.InvestmentController;
import src.Controllers.LoanController;

import src.Models.Investment;
import src.Models.Loan;

import java.io.IOException;

import java.sql.SQLException;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import java.util.Date;
import java.util.Properties;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BankReports {

    InvestmentController investmentController;
    LoanController loanController;
    Properties properties = ConfigReader.loadProperties();
    private final String dbPath = properties.getProperty("DATABASE_PATH");

    public BankReports() throws IOException {
        this.investmentController = new InvestmentController();
        this.loanController = new LoanController();
    }


    // formula = Investment Forecast = initial value x (1 + annual return rate / 100) ^ duration
    public double getInvestmentForecast(int investmentId) throws SQLException {
        double forecast = 0;

        try {
            Investment investment = this.investmentController.getInvestmentById(investmentId);

            if (investment != null) {
                Date startDate = investment.getStartDate();
                Date endDate = investment.getEndDate();

                // convert from java.sql.Date to java.util.Date
                java.util.Date startUtilDate = new java.util.Date(startDate.getTime());
                java.util.Date endUtilDate = new java.util.Date(endDate.getTime());

                Instant startInstant = startUtilDate.toInstant();
                Instant endInstant = endUtilDate.toInstant();

                // convert from Instant to LocalDate
                LocalDate startLocalDate = startInstant.atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate endLocalDate = endInstant.atZone(ZoneId.systemDefault()).toLocalDate();

                // calculates duration in days
                long durationInDays = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);

                double initialValue = investment.getInvestmentAmount();
                double returnRate = investment.getAnnualReturnRate();

                // investment forecast formula
                BigDecimal forecastBigDecimal = BigDecimal.valueOf(initialValue * Math.pow(1 + returnRate / 100, durationInDays));

                // two decimal places
                int scale = 2;

                // round up
                RoundingMode roundingMode = RoundingMode.HALF_UP;

                // Truncate decimal places
                forecastBigDecimal = forecastBigDecimal.setScale(scale, roundingMode);

                // convert the result to double
                forecast = forecastBigDecimal.doubleValue();
            }
            else{
                System.out.println("No investment forecast find for this Id: " + investmentId);
            }
        } catch (SQLException Error) {
            System.err.println("Error getting investment forecast: " + Error.getMessage());
            throw Error;
        }
        return forecast;
    }


    // Pagamentos Mensais (E): E = P * (r * (1 + r)^n) / ((1 + r)^n - 1)
    public double getInterestLoanCalculations(int loanId) throws SQLException {
        double interestLoan = 0;

        try {
            Loan loan = this.loanController.getLoanById(loanId);

            if (loan != null) {
                double loanAmount = loan.getLoanAmount();
                double annualInterestRate = loan.getAnnualInterestRate();
                int loanTermMonths = loan.getLoanTermMonths();

                // calculates monthly payments
                double monthlyInterestRate = (annualInterestRate / 100) / 12;
                double monthlyPayment = (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -loanTermMonths));

                // Calculates total interest paid
                double totalInterestPaid = (monthlyPayment * loanTermMonths) - loanAmount;

                // rounds the result to two decimal places
                BigDecimal interestBigDecimal = BigDecimal.valueOf(totalInterestPaid);
                interestBigDecimal = interestBigDecimal.setScale(2, RoundingMode.HALF_UP);

                // converts the result from BigDecimal to double
                interestLoan = interestBigDecimal.doubleValue();
            } else {
                System.out.println("No loans were found with the given Id: " + loanId);
            }
        } catch (SQLException Error) {
            System.err.println("Error getting the interest loan calculation: " + Error.getMessage());
            throw Error;
        }

        return interestLoan;
    }
}
