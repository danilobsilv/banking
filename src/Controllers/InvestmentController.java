package src.Controllers;

import src.Database.DBConnection;
import src.Database.ExecuteQuery;

import src.Models.Investment;
import src.env.ConfigurationReader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class InvestmentController {

    Properties properties = ConfigurationReader.loadProperties();
    private final String dbPath = properties.getProperty("dbPath", null);
    ExecuteQuery executeQuery;

    public InvestmentController() throws IOException {
        this.executeQuery = new ExecuteQuery();
    }

    public void createInvestment(Investment investment) throws SQLException {
        String query = "INSERT INTO investments (investmentName, investmentAmount, annualReturnDate, startDate, endDate) VALUES (?, ?, ?, ?, ?)";

        try{
            this.executeQuery.executeQuery(query,investment.getInvestmentName(), investment.getInvestmentAmount(), investment.getAnnualReturnRate(), investment.getStartDate(), investment.getEndDate());
            System.out.println("Investment successfully created.");
        }
        catch (SQLException Error){
            System.err.println("Error creating investment: " + Error.getMessage());
            throw Error;
        }
    }

    public void deleteInvestment(int investmentId) throws SQLException{
        String query = "DELETE FROM investments WHERE investmentId = ?";

        try{
            this.executeQuery.executeQuery(query,investmentId);
            System.out.println("Investment successfully deleted.");
        }
        catch (SQLException Error){
            System.err.println("Error deleting investment: " + Error.getMessage());
            throw Error;
        }
    }

    public void updateInvestment(int investmentId, String investmentsColumn, String updateContent) throws SQLException{
        String query = "UPDATE investments SET %s = ? WHERE investmentId = ?";
        query = String.format(query, investmentsColumn);

        try{
            this.executeQuery.executeQuery(query, updateContent, investmentId);
            System.out.println("Update successfully completed.");
        }
        catch (SQLException Error){
            System.err.println("Error updating investiment: " + Error.getMessage());
            throw Error;
        }
    }

    public List<Investment> listInvestments() throws SQLException {
        List<Investment> investmentsArray = new ArrayList<>();
        String query = "SELECT * FROM investments";

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                investmentsArray.add(this.executeQuery.mapInvestmentsFromResultSet(resultSet));
            }
        }
        catch (SQLException Error){
            System.err.println("Error listing investments: " + Error.getMessage());
            throw Error;
        }
        return investmentsArray;
    }


    public Investment getInvestmentById(int investmentId) throws SQLException{
        String query = "SELECT * FROM investments WHERE investmentId = ?";

        try (Connection connection = DBConnection.getConnection(dbPath);
        PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,investmentId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return this.executeQuery.mapInvestmentsFromResultSet(resultSet);
                }
                else{
                    System.out.println("No investments were found with the given ID: " + investmentId);
                    return null;
                }
            }
        }
        catch (SQLException Error){
            System.err.println("Error getting investment: " + Error.getMessage());
            throw Error;
        }
    }
}
