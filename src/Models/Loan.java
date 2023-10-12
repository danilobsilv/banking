package src.Models;

import java.util.Date;

import src.enumerate.LoanStatusTypes;

public class Loan {

    float loanAmount;
    float annualInterestRate;
    Integer loanTermMonths;
    Date requestDate;
    Date loanDate;
    LoanStatusTypes loanStatus;

    public Loan(float loanAmount, float annualInterestRate,
                Integer loanTermMonths, Date requestDate, LoanStatusTypes loanStatus) {
        this.loanAmount = loanAmount;
        this.annualInterestRate = annualInterestRate;
        this.loanTermMonths = loanTermMonths;
        this.requestDate = requestDate;
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

    public LoanStatusTypes getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(LoanStatusTypes loanStatus) {
        this.loanStatus = loanStatus;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public String toString() {
        return "Loan {" +
                "loanAmount = " + loanAmount +
                ", annualInterestRate = " + annualInterestRate +
                ", loanTermMonths = " + loanTermMonths +
                ", loanDate = " + loanDate +
                ", loanStatus ='" + loanStatus + '\'' +
                " }";
    }
}