package market.ics.fiscalconfiguration.Driver.Request;

import market.ics.fiscalconfiguration.Driver.CommandRequest;

public class GetStatusCommand implements PrinterCommand {
    private static final Byte COMMAND_CODE = 0;

    public CommandRequest getRequest(Byte orderNumber) {
        return new CommandRequest(orderNumber, COMMAND_CODE);
    }
}
