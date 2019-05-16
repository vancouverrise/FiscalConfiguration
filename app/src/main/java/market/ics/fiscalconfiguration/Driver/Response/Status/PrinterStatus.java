package market.ics.fiscalconfiguration.Driver.Response.Status;

import market.ics.fiscalconfiguration.Driver.DataType.ByteBits;

public final class PrinterStatus {
    private final ByteBits firstByte;
    private final ByteBits secondByte;

    public PrinterStatus(ByteBits firstByte, ByteBits secondByte) {
        this.firstByte = firstByte;
        this.secondByte = secondByte;
    }

    public boolean isWorkShiftStarted() {
        return this.firstByte.getSixth();
    }

    public boolean isLastCommandFailed() {
        return this.secondByte.getSixth();
    }

    public boolean isOnLineMode() {
        return this.secondByte.getSeventh();
    }

    public String toString() {
        return "\tPrinterStatus\n\t{\n" + this.print("Work Shift Started", this.isWorkShiftStarted()) + this.print("Last Command Failed", this.isLastCommandFailed()) + this.print("On Line Mode", this.isOnLineMode()) + "\t}";
    }

    private String print(String propText, boolean cond) {
        return "\t\t" + propText + ": " + (cond ? "yes" : "no") + "\n";
    }
}
