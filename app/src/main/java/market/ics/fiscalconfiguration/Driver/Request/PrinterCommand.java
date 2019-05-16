package market.ics.fiscalconfiguration.Driver.Request;

import market.ics.fiscalconfiguration.Driver.CommandRequest;

public interface PrinterCommand {
    CommandRequest getRequest(Byte temp);
}
