package market.ics.fiscalconfiguration.Driver.Response.Status;

import market.ics.fiscalconfiguration.Driver.DataType.ByteBits;

public final class Status {

    private final ReceiptStatus receiptStatus;
    private final PrinterConfig printerConfig;
    private final PrinterStatus printerStatus;

    public Status(ByteBits firstByte, ByteBits secondByte) {
        this.receiptStatus = new ReceiptStatus(firstByte);
        this.printerConfig = new PrinterConfig(firstByte, secondByte);
        this.printerStatus = new PrinterStatus(firstByte, secondByte);
    }

    public ReceiptStatus receiptStatus() {
        return this.receiptStatus;
    }

    public PrinterConfig printerConfig() {
        return this.printerConfig;
    }

    public PrinterStatus printerStatus() {
        return this.printerStatus;
    }

    public String toString() {
        return "Status{\n" + this.printerConfig() + this.printerStatus() + "\n}";
    }
}
