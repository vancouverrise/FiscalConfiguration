package market.ics.fiscalconfiguration.Driver.Request;

import market.ics.fiscalconfiguration.Driver.CommandRequest;
import market.ics.fiscalconfiguration.Driver.DataType.PrinterPassword;


public class SetCutterMode implements PrinterCommand {
    private static final Byte COMMAND_CODE = 46;


    public CommandRequest getRequest(Byte orderNumber) {
        return new CommandRequest(orderNumber, COMMAND_CODE);
    }
}
