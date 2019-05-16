package market.ics.fiscalconfiguration.Driver.Request;

import market.ics.fiscalconfiguration.Driver.CommandRequest;
import market.ics.fiscalconfiguration.Driver.DataType.PrinterPassword;

public final class PrintDailyReportCommand implements PrinterCommand {
    private static final Byte COMMAND_CODE = 9;
    private final PrinterPassword printerPassword;

    public PrintDailyReportCommand(PrinterPassword printerPassword) {
        this.printerPassword = printerPassword;
    }

    public CommandRequest getRequest(Byte orderNumber) {
        return new CommandRequest(orderNumber, COMMAND_CODE, this.printerPassword.bytes());
    }
}
