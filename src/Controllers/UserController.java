package src.Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import src.Database.DBConnection;
import src.Database.ExecuteQuery;
import src.Models.User;

public class UserController{

    private final static String dbPath = "bankApp.db";
    ExecuteQuery executeQuery = new ExecuteQuery();

    public UserController() {}

    public void createUser(User user) throws SQLException {
        String query = "INSERT INTO users (username, userEmail, password, datestamp) VALUES (?, ?, ?, ?)";
        executeQuery.executeQuery(query, user.getUsername(), user.getEmail(), user.getPassword(), user.getCreationDate());
    }


    public int getUserId(String email) throws SQLException {
        int userId = -1; // in case no user is found

        String query = "SELECT userId FROM users WHERE userEmail = ?";

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getInt("userId");
                }
            }
        } catch (SQLException Error) {
            System.err.println("Error getting user's ID: " + Error.getMessage());
            throw Error;
        }
        return userId;
    }

    public User getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE userId = ?";
        User user = null;
        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String user_email = resultSet.getString("userEmail");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    long creationDateMillis = resultSet.getLong("datestamp");

                    Date creationDate = new Date(creationDateMillis);
                    user = new User(username, user_email, password, creationDate);
                }
            }
        } catch (SQLException Error) {
            System.err.println("Error getting user: " + Error.getMessage());
            throw Error;
        }
        return user;
    }



    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                //   int user_id = resultSet.getInt("user_id");
                String user_email = resultSet.getString("userEmail");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                long creationDateMillis  = resultSet.getLong("datestamp");

                Date creationDate = new Date(creationDateMillis);
                User user = new User(username, user_email, password, creationDate);
                users.add(user);
            }
        }
        return users;
    }

    public boolean showUsers() throws SQLException {
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
        return true;
    }

    public void editUserUsername(int userId, String newUsername) throws  SQLException{
        String query = "UPDATE users SET username = ? WHERE userId = ?";

        try{
            ExecuteQuery executeQuery = new ExecuteQuery();
            executeQuery.executeQuery(query, newUsername, userId);

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
            ExecuteQuery executeQuery = new ExecuteQuery();
            executeQuery.executeQuery(query,newPassword, userId);

            System.out.println("Password updated successfully!");
        }
        catch(SQLException Error){
            System.err.println("Error updating password: " + Error.getMessage());
            throw Error;
        }
    }

    public void deleteUser(int userId) throws Exception {
        String query = "DELETE FROM users WHERE userId = ?";

        try (ExecuteQuery executeQuery = new ExecuteQuery()) {
            executeQuery.executeQuery(query, userId);
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
            verification = executeQuery.emailExists(query, userEmail);
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
            verification = executeQuery.passwordExists(query, userPassword);
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
            verification = executeQuery.checkUserIdExists(userId);
        }
        catch (SQLException Error){
            System.err.println("Error verifying user's ID: " + Error.getMessage());
            throw Error;
        }
        return verification;
    }
}