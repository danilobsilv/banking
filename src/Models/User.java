package src.Models;

import java.util.Date;

public class User{

    protected String username;
    protected String email;
    protected String password;

    protected Date creationDate;
    public User(String username, String email, String password, Date creationDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.creationDate = creationDate;
    }


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}