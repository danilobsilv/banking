package src.Models;

import src.enumerate.TransactionType;

import java.util.Date;

public class Transaction {
    private float amount;
    private TransactionType transactionType;

    public Transaction(float amount, TransactionType transactionType){
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public float getAmount() {
        return this.amount;
    }

    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    @Override
    public String toString() {
        return "Transaction { " +
                "amount=" + amount +
                ", transactionType=" + transactionType +
                " }";
    }
}

