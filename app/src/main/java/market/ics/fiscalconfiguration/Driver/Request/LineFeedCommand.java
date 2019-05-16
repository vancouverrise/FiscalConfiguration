package market.ics.fiscalconfiguration.Driver.Request;

import java.util.ArrayList;

import market.ics.fiscalconfiguration.Driver.CommandRequest;



public final class LineFeedCommand implements PrinterCommand {
    private static final Byte COMMAND_CODE = 14;

    public LineFeedCommand() {
    }

    public CommandRequest getRequest(Byte orderNumber) {
        return new CommandRequest(orderNumber, COMMAND_CODE);
    }

}
