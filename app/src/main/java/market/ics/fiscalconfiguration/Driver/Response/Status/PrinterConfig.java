package market.ics.fiscalconfiguration.Driver.Response.Status;

import market.ics.fiscalconfiguration.Driver.DataType.ByteBits;

public class PrinterConfig {
    private final ByteBits firstByte;
    private final ByteBits secondByte;

    public PrinterConfig(ByteBits firstByte, ByteBits secondByte) {
        this.firstByte = firstByte;
        this.secondByte = secondByte;
    }

    public boolean isUsingFees() {
        return this.firstByte.getFirst();
    }

    public boolean isCashDrawerOpen() {
        return this.firstByte.getThird();
    }

    public boolean isVATAddOn() {
        return this.firstByte.getFifth();
    }

    public boolean isVATEmbedded() {
        return !this.isVATAddOn();
    }

    public boolean isLogoPrinted() {
        return this.secondByte.getSecond();
    }

    public boolean isPaperCuttingForbidden() {
        return this.secondByte.getThird();
    }

    public boolean isPrintServiceReportReceiptMode() {
        return this.secondByte.getForth();
    }

    public boolean isFiscalized() {
        return this.secondByte.getFifth();
    }

    public String toString() {
        return "\tPrinterConfig\n\t{\n" + this.print("Using fees", this.isUsingFees()) + this.print("Cash drawer open", this.isCashDrawerOpen()) + this.print("VAT add-on", this.isVATAddOn()) + this.print("VAT embedded", this.isVATEmbedded()) + this.print("Logo printed", this.isLogoPrinted()) + this.print("Paper Cutting Forbidden", this.isPaperCuttingForbidden()) + this.print("Print Service Report Receipt Mode", this.isPrintServiceReportReceiptMode()) + this.print("Fiscalized", this.isFiscalized()) + "\t}";
    }

    private String print(String propText, boolean cond) {
        return "\t\t" + propText + ": " + (cond ? "yes" : "no") + "\n";
    }
}