package src.Database;

import src.env.ConfigReader;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class BankReports {

    Properties properties = ConfigReader.loadProperties();
    private final String dbPath = properties.getProperty("DATABASE_PATH");

    public BankReports() throws IOException {
    }

    public BigDecimal getInvestmentForecast(int investmentId) throws SQLException {
        BigDecimal forecast = BigDecimal.ZERO;

        String query = "SELECT SUM(investmentAmount * POWER(1 + (annualReturnDate / 100), (endDate - startDate) / 365)) FROM investments WHERE investmentId = ?";

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, investmentId);  // Defina o valor do parâmetro investmentId
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    BigDecimal result = resultSet.getBigDecimal(1);

                    if (result != null && !result.toString().equalsIgnoreCase("Inf")) {
                        forecast = result;
                    }
                }
            }
        } catch (SQLException Error) {
            System.err.println("Error providing forecast: " + Error.getMessage());
            throw Error;
        }
        System.out.println("Investment forecast: " + forecast.toPlainString());
        return forecast;
    }

    public long getInterestLoanCalculations(){
        // todo
    }

    public long getBalanceInquires(){
        // todo
    }

    public long getFinancialReports(){
        // todo
    }
}
