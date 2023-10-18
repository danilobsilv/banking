package src.Models;

import src.enumerate.InvestmentTypeNames;

import java.util.Date;

public class Investment {

    InvestmentTypeNames investmentName;
    float investmentAmount;
    float annualReturnRate;
    Date startDate;
    Date endDate;

    public Investment(InvestmentTypeNames investmentName, float investmentAmount, float annualReturnRate,
                      Date startDate, Date endDate) {
        this.investmentName = investmentName;
        this.investmentAmount = investmentAmount;
        this.annualReturnRate = annualReturnRate;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public InvestmentTypeNames getInvestmentName() {
        return investmentName;
    }

    public void setInvestmentName(InvestmentTypeNames investmentName) {
        this.investmentName = investmentName;
    }

    public float getAnnualReturnRate() {
        return annualReturnRate;
    }

    public void setAnnualReturnRate(float annualReturnRate) {
        this.annualReturnRate = annualReturnRate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getInvestmentAmount() {
        return investmentAmount;
    }

    public void setInvestmentAmount(float investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    @Override
    public String toString() {
        return "Investment{" +
                "investmentName='" + investmentName + '\'' +
                ", investmentAmount=" + investmentAmount +
                ", annualReturnRate=" + annualReturnRate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}

