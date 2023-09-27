package src.App;

import src.Database.DBConnection;
import src.Database.CreateTables;

import src.Controllers.UserController;
import src.Models.User;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        UserController cont = new UserController();
        //User user = new User("usariobacana", "userbacana@gmail.com", "user123");
        //cont.createUser(user);
        cont.showUsers();
        //cont.editUserUsername(3,"vai tomar no cu");
        //cont.editUserPassword(3, "filho da puta");
        //cont.deleteUser(3);
    }
}