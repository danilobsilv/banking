package src.Models;

import src.enumerate.AccountType;

import java.util.Date;

public class BankAccount {

    String accountNumber;
    Float balance;
    AccountType accountType;
    Date dateCreated;

    public BankAccount(String accountNumber, Float balance, AccountType accountType, Date dateCreated) {
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

    public AccountType getAccountType() { // Correção aqui
        return accountType;
    }

    public void setAccountType(AccountType accountType) { // Correção aqui
        this.accountType = accountType;
    }
}
