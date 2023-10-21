package src.Database;

import src.env.ConfigReader;

import src.Controllers.InvestmentController;

import src.Models.Investment;

import java.io.IOException;


import java.sql.SQLException;

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
    Properties properties = ConfigReader.loadProperties();
    private final String dbPath = properties.getProperty("DATABASE_PATH");

    public BankReports() throws IOException {
        this.investmentController = new InvestmentController();
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
        } catch (SQLException Error) {
            System.err.println("Error getting investment forecast: " + Error.getMessage());
            throw Error;
        }
        return forecast;
    }


    /*
    public long getInterestLoanCalculations(){
        // todo
    }

    public long getBalanceInquires(){
        // todo
    }

    public long getFinancialReports(){
        // todo
    }
    */

}
