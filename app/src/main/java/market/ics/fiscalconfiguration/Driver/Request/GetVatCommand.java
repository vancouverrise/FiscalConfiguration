package market.ics.fiscalconfiguration.Driver.Request;

import java.util.ArrayList;

import market.ics.fiscalconfiguration.Driver.CommandRequest;

public class GetVatCommand implements PrinterCommand {
    private static final Byte COMMAND_CODE = 44;

    public GetVatCommand() {
    }

    public CommandRequest getRequest(Byte orderNumber) {
        return new CommandRequest(orderNumber, COMMAND_CODE, new ArrayList());
    }
}
