package market.ics.fiscalconfiguration.Driver.Request;


import java.util.ArrayList;
import java.util.List;

import market.ics.fiscalconfiguration.Driver.CommandRequest;
import market.ics.fiscalconfiguration.Driver.DataType.PrinterString;

public class LineDisplay implements PrinterCommand {
    private static final Byte COMMAND_CODE = 27;
    private int lineNumber;
    private PrinterString line;

    public LineDisplay(int lineNumber, PrinterString line) {
        this.lineNumber = lineNumber;
        this.line = line;
    }

    public CommandRequest getRequest(Byte orderNumber) {
        List<Byte> parameters = new ArrayList();
        parameters.add((byte)this.lineNumber);
        parameters.add((byte)this.line.length());
        parameters.addAll(this.line.bytes());
        return new CommandRequest(orderNumber, COMMAND_CODE, parameters);
    }

    public String toString() {
        return "LineDisplayCommand [lineNumber=" + this.lineNumber + ", line=" + this.line + "]";
    }
}
