package market.ics.fiscalconfiguration.Driver.Request;

import java.util.ArrayList;

import market.ics.fiscalconfiguration.Driver.CommandRequest;

public class ReadStatus implements PrinterCommand {
    private static final Byte COMMAND_CODE = 0;

    public ReadStatus() {
    }

    public CommandRequest getRequest(Byte orderNumber) {
        return new CommandRequest(orderNumber, COMMAND_CODE, new ArrayList());
    }


    public String toString() {
        return "ReadStatusCommand []";
    }
}
