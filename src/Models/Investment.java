package src.Models;

import java.util.Date;

public class Investment {

    String investmentName;
    float annualReturnRate;
    Date startDate;
    Date endDate;

    public Investment(String investmentName, float annualReturnRate,
                       Date startDate, Date endDate) {
        this.investmentName = investmentName;
        this.annualReturnRate = annualReturnRate;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public String getInvestmentName() {
        return investmentName;
    }

    public void setInvestmentName(String investmentName) {
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
}

