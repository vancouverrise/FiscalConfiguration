package market.ics.fiscalconfiguration.Driver.Response;

import java.util.List;

import market.ics.fiscalconfiguration.Driver.DataType.Sym;

public final class FiscalConfiguration {

    private final List<Byte> bytes;

    public FiscalConfiguration(List<Byte> bytes) {
        this.bytes = bytes;
    }


    public String fiscalNumber() {
        return (new Sym(this.bytes.subList(26, 36))).toString();
    }

    public String taxPayerAttributesLineOne() {
        return (new Sym(this.bytes.subList(this.firstLineStartIndex(), this.firstLineEndIndex()))).toString();
    }

    public String taxPayerAttributesLineTwo() {
        return (new Sym(this.bytes.subList(this.secondLineStartIndex(), this.secondLineEndIndex()))).toString();
    }

    public String taxPayerAttributesLineThree() {
        return (new Sym(this.bytes.subList(this.thirdLineStartIndex(), this.thirdLineEndIndex()))).toString();
    }

    public String taxPayerTaxNumberLine() {
        return (new Sym(this.bytes.subList(this.forthLineStartIndex(), this.forthLineEndIndex()))).toString();
    }

    private int forthLineEndIndex() {
        return this.forthLineStartIndex() + (Byte)this.bytes.get(this.thirdLineEndIndex());
    }

    private int forthLineStartIndex() {
        return this.thirdLineEndIndex() + 1;
    }

    private int thirdLineEndIndex() {
        return this.thirdLineStartIndex() + (Byte)this.bytes.get(this.secondLineEndIndex());
    }

    private int thirdLineStartIndex() {
        return this.secondLineEndIndex() + 1;
    }

    private int firstLineStartIndex() {
        return 37;
    }

    private int firstLineEndIndex() {
        return this.firstLineStartIndex() + (this.bytes.get(firstLineStartIndex() -1));
    }

    private int secondLineStartIndex() {
        return this.firstLineEndIndex() + 1;
    }

    private int secondLineEndIndex() {
        return this.secondLineStartIndex() + (Byte)this.bytes.get(this.firstLineEndIndex());
    }

    public String toString() {
        return  "fiscalNumber=" + this.fiscalNumber() + "taxPayerAttributesLineOne=" + this.taxPayerAttributesLineOne() + "taxPayerAttributesLineTwo=" + this.taxPayerAttributesLineTwo() + "taxPayerAttributesLineThree=" + this.taxPayerAttributesLineThree() + "taxPayerTaxNumberLine=" + this.taxPayerTaxNumberLine() + '}';
    }
}
