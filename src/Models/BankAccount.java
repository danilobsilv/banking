package src.Models;

import java.util.Date;

public class BankAccount {

    String accountNumber;
    Float balance;
    String accountType;
    Date dateCreated;

    public BankAccount(String accountNumber, Float balance, String accountType, Date dateCreated) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
        this.dateCreated = dateCreated;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
