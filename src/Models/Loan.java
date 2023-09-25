package src.Models;

import java.util.Date;

public class Loan {

    float loanAmount;
    float annualInterestRate;
    Integer loanTermMonths;
    Date loanDate;
    String loanStatus;

    public Loan(float loanAmount, float annualInterestRate,
                Integer loanTermMonths, Date loanDate, String loanStatus) {
        this.loanAmount = loanAmount;
        this.annualInterestRate = annualInterestRate;
        this.loanTermMonths = loanTermMonths;
        this.loanDate = loanDate;
        this.loanStatus = loanStatus;
    }

    public float getLoanAmount() {
        return loanAmount;
    }
    public void setLoanAmount(float loanAmount) {
        this.loanAmount = loanAmount;
    }
    public float getAnnualInterestRate() {
        return annualInterestRate;
    }
    public void setAnnualInterestRate(float annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }
    public Integer getLoanTermMonths() {
        return loanTermMonths;
    }
    public void setLoanTermMonths(Integer loanTermMonths) {
        this.loanTermMonths = loanTermMonths;
    }
    public Date getLoanDate() {
        return loanDate;
    }
    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }
    public String getLoanStatus() {
        return loanStatus;
    }
    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }
}