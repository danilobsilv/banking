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

import src.Models.User;
import src.env.ConfigurationReader;

public class UserController{

    Properties properties = ConfigurationReader.loadProperties();
    private final String dbPath = properties.getProperty("dbPath", null);
    ExecuteQuery executeQuery;

    public UserController() throws IOException {
        this.executeQuery = new ExecuteQuery();
    }

    public void createUser(User user) throws SQLException {
        String query = "INSERT INTO users (username, userEmail, password, datestamp) VALUES (?, ?, ?, ?)";
        try{
            this.executeQuery.executeQuery(query, user.getUsername(), user.getEmail(), user.getPassword(), user.getCreationDate());
        }
        catch (SQLException Error){
            System.err.println("Error creating user: " + Error.getMessage());
            throw Error;
        }
    }


    public User getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE userId = ?";
            try (Connection connection = DBConnection.getConnection(dbPath);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return this.executeQuery.mapUserFromResultSet(resultSet);
                }
            }
        } catch (SQLException Error) {
            System.err.println("Error getting user: " + Error.getMessage());
            throw Error;
        }
        return null;
    }

    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                users.add(this.executeQuery.mapUserFromResultSet(resultSet));
            }
        }
        return users;
    }

    public void showUsers() throws SQLException {
        try {
            List<User> users = getUsers();

            if (users.isEmpty()) {
                System.out.println("No user was found.");
            }
            else {
                System.out.println("Users list:\n");
                for (User user : users) {
                    System.out.println("Username: " + user.getUsername());
                    System.out.println("Email: " + user.getEmail());
                    System.out.println("Password: " + user.getPassword());
                    System.out.println("-----------------------");
                }
            }
        }
        catch (SQLException Error) {
            System.err.println("Error on showing users: " + Error.getMessage());
            throw Error;
        }
    }

    public void editUserUsername(int userId, String newUsername) throws  SQLException{
        String query = "UPDATE users SET username = ? WHERE userId = ?";

        try{
            this.executeQuery.executeQuery(query, newUsername, userId);
            System.out.println("Username updated successfully!");
        }
        catch (SQLException Error){
            System.err.println("Error updating username: " + Error.getMessage());
            throw Error;
        }
    }

    public void editUserPassword(int userId, String newPassword) throws SQLException{
        String query = "UPDATE users SET password = ? WHERE userId = ?";

        try{
            this.executeQuery.executeQuery(query,newPassword, userId);
            System.out.println("Password updated successfully!");
        }
        catch(SQLException Error){
            System.err.println("Error updating password: " + Error.getMessage());
            throw Error;
        }
    }

    public void deleteUser(int userId) throws Exception {
        String query = "DELETE FROM users WHERE userId = ?";

        try {
            this.executeQuery.executeQuery(query, userId);
            System.out.println("User successfully deleted!");
        } catch (Exception Error) {
            System.err.println("Error deleting user: " + Error.getMessage());
            throw Error;
        }
    }

    public boolean verifyUserEmail(String userEmail) throws SQLException {
        boolean verification = false;
        String query = "SELECT COUNT(*) FROM users WHERE userEmail = ?";
        try {
            verification = this.executeQuery.emailExists(query, userEmail);
        } catch (SQLException Error) {
            System.err.println("Error verifying user's email: " + Error.getMessage());
            throw Error;
        }
            return verification;
    }

    public boolean verifyUserPassword(String userPassword) throws SQLException{
        boolean verification = false;
        String query = "SELECT COUNT(*) FROM users WHERE password = ?";
        try{
            verification = this.executeQuery.passwordExists(query, userPassword);
        }
        catch (SQLException Error){
            System.err.println("Error verifying user's password: " + Error.getMessage());
            throw Error;
        }
        return verification;
    }

    public boolean verifyUserId(int userId) throws SQLException{
        boolean verification = false;
        String query = "SELECT COUNT(*) FROM users WHERE userId = ?";
        try{
            verification = this.executeQuery.checkUserIdExists(userId);
        }
        catch (SQLException Error){
            System.err.println("Error verifying user's ID: " + Error.getMessage());
            throw Error;
        }
        return verification;
    }
}