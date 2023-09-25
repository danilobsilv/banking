package src.Models;


import java.util.Date;

public class Transaction {

    float amount;
    String transactionType;
    Integer accountId;
    Date transactionDate;

    public Transaction(String transactionId, float amount, String transactionType, Integer accountId,
                       Date transactionDate) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.accountId = accountId;
        this.transactionDate = transactionDate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
