package market.ics.fiscalconfiguration.Driver.Request;

import java.util.ArrayList;
import java.util.List;

import market.ics.fiscalconfiguration.Driver.CommandRequest;
import market.ics.fiscalconfiguration.Driver.DataType.DecimalByte;

public class SetTimeCommand implements PrinterCommand {
    private static final Byte COMMAND_CODE = 4;

    private int hour;
    private int minutes;
    private int seconds = 0;

    public SetTimeCommand(int setHour, int setMinutes) {

        this.hour = setHour;
        this.minutes = setMinutes;

    }

    public CommandRequest getRequest(Byte orderNumber) {
        return new CommandRequest(orderNumber, COMMAND_CODE, this.parameters());
    }

    private List<Byte> parameters() {
        List<Byte> bytes = new ArrayList();
        bytes.add(this.convertToBCD(hour));
        bytes.add(this.convertToBCD(minutes));
        bytes.add(this.convertToBCD(seconds));
        return bytes;
    }

    private Byte convertToBCD(int twoDigitsInt) {
        return (new DecimalByte(twoDigitsInt)).toBCD();
    }
}
