package market.ics.fiscalconfiguration.Driver;

import java.util.Arrays;
import java.util.Date;

public class TaxInfo {
    private static byte totalTax;
    private Date date;
    private static double[] taxRates;
    private byte decimals;
    private byte taxType;
    private boolean rateOfTurns;
    private double[] taxRatesE;

    public TaxInfo() {
    }

    public static byte getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(byte totalTax) {
        this.totalTax = totalTax;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static double[] getTaxRates() {
        return taxRates;
    }

    public void setTaxRates(double[] taxRates) {
        this.taxRates = taxRates;
    }

    public byte getDecimals() {
        return decimals;
    }

    public void setDecimals(byte decimals) {
        this.decimals = decimals;
    }

    public byte getTaxType() {
        return taxType;
    }

    public void setTaxType(byte taxType) {
        this.taxType = taxType;
    }

    public boolean isRateOfTurns() {
        return rateOfTurns;
    }

    public void setRateOfTurns(boolean rateOfTurns) {
        this.rateOfTurns = rateOfTurns;
    }

    public double[] getTaxRatesE() {
        return taxRatesE;
    }

    public void setTaxRatesE(double[] taxRatesE) {
        this.taxRatesE = taxRatesE;
    }

    @Override
    public String toString() {
        return "TaxInfo{" +
                "totalTax=" + totalTax +
                ", date=" + date +
                ", taxRates=" + Arrays.toString(taxRates) +
                ", decimals=" + decimals +
                ", taxType=" + taxType +
                ", rateOfTurns=" + rateOfTurns +
                ", taxRatesE=" + Arrays.toString(taxRatesE) +
                '}';
    }
}