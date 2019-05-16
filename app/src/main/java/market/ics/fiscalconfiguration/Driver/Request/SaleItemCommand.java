package market.ics.fiscalconfiguration.Driver.Request;

import java.util.List;

import market.ics.fiscalconfiguration.Driver.CommandRequest;
import market.ics.fiscalconfiguration.Driver.DataType.ItemLine;

public final class SaleItemCommand implements PrinterCommand {
    private static final Byte COMMAND_CODE = 18;
    private final ItemLine line;

    public SaleItemCommand(ItemLine line) {
        this.line = line;
    }

    public CommandRequest getRequest(Byte orderNumber) {
        return new CommandRequest(orderNumber, COMMAND_CODE, this.parameters());
    }

    private List<Byte> parameters() {
        return this.line.bytes();
    }

    public String toString() {
        return "SaleItemCommand [line=" + this.line + "]";
    }
}
