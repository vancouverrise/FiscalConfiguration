package market.ics.fiscalconfiguration.Driver.Request;

import market.ics.fiscalconfiguration.Driver.CommandRequest;
import market.ics.fiscalconfiguration.Driver.DataType.PrinterPassword;

public final class PrintZReportCommand implements PrinterCommand {
    private static final Byte COMMAND_CODE = 13;
    private final PrinterPassword printerPassword;

    public PrintZReportCommand(PrinterPassword printerPassword) {
        this.printerPassword = printerPassword;
    }

    public CommandRequest getRequest(Byte orderNumber) {
        return new CommandRequest(orderNumber, COMMAND_CODE, this.printerPassword.bytes());
    }

    public String toString() {
        return "PrintZReportCommand []";
    }
}
