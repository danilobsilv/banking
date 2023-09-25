package src.Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import src.Database.DBConnection;
import src.Database.ExecuteQuery;

import src.Models.User;

public class UserController{

    private final static String dbPath = "bankApp.db";
    ExecuteQuery executeQuery = new ExecuteQuery();

    public UserController() {
    }

    public void createUser(User user) throws SQLException {
        String query = "INSERT INTO users (username, user_email, password) " +
                "VALUES (?, ?, ?)";
        executeQuery.executeQuery(query, user.getUsername(), user.getEmail(), user.getPassword());
    }

    public int getUserId(String email) throws SQLException {
        int userId = -1; // in case no user is found

        String query = "SELECT user_id FROM users WHERE user_email = ?";

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getInt("user_id");
                }
            }
        } catch (SQLException Error) {
            throw Error;
        }
        return userId;
    }



    public static List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection connection = DBConnection.getConnection(dbPath);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                //   int user_id = resultSet.getInt("user_id");
                String user_email = resultSet.getString("user_email");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                User user = new User(username, user_email, password);
                users.add(user);
            }
        }
        return users;
    }

    public void showUsers() {
        try {
            List<User> users = getUsers();

            if (users.isEmpty()) {
                System.out.println("No user was found.");
            }
            else {
                System.out.println("Users list:");
                for (User user : users) {
                    System.out.println("Username: " + user.getUsername());
                    System.out.println("Email: " + user.getEmail());
                    System.out.println("Password: " + user.getPassword());
                    System.out.println("-----------------------");
                }
            }
        }
        catch (SQLException e) {
            System.err.println("Erro ao exibir usuários: " + e.getMessage());
        }
    }
}
