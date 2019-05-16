package market.ics.fiscalconfiguration.Driver.DataType;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

public class VatRate {
    private TaxGroup taxGroup;
    private BigDecimal value;

    public VatRate(TaxGroup taxGroup, BigDecimal value) {
        this.taxGroup = taxGroup;
        this.value = value;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public TaxGroup getTaxGroup() {
        return this.taxGroup;
    }



    public boolean isVatAtCharge() {
        return (new ByteBits((byte)(this.value.intValue() >> 8))).getEighth();
    }

    public boolean isChargeAtVat() {
        return (new ByteBits((byte)(this.value.intValue() >> 8))).getSeventh();
    }

    public void setTaxGroup(TaxGroup taxGroup) {
        this.taxGroup = taxGroup;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }



    public String toString() {
        return "Vat group " + this.taxGroup + " = " + this.value + ", is vat " + this.taxGroup + " at charge: " + this.isVatAtCharge();
    }


}
