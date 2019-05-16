package market.ics.fiscalconfiguration.Driver.Request;

import java.util.ArrayList;
import java.util.List;

import market.ics.fiscalconfiguration.Driver.CommandRequest;
import market.ics.fiscalconfiguration.Driver.DataType.PrinterPassword;

public class GetModemCommand implements PrinterCommand {
    private static final Byte COMMAND_CODE = 53;
    private final PrinterPassword printerPassword;
    private List<Byte> tt = new ArrayList<>();



    public GetModemCommand(PrinterPassword printerPassword) {
        this.printerPassword = printerPassword;
    }


    public CommandRequest getRequest(Byte orderNumber) {
        tt.add((byte) 0x01);
        tt.add((byte) 0x00);
        tt.add((byte) 0x07);
        return new CommandRequest(orderNumber, COMMAND_CODE, this.printerPassword.bytes(), tt);


    }

}

