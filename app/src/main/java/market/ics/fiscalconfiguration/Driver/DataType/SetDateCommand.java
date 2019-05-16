package market.ics.fiscalconfiguration.Driver.DataType;


import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

import market.ics.fiscalconfiguration.Driver.CommandRequest;
import market.ics.fiscalconfiguration.Driver.Request.PrinterCommand;

public class SetDateCommand implements PrinterCommand {
    private static final Byte COMMAND_CODE = 2;

    private int year;
    private int month;
    private int day;


    public SetDateCommand(int setday, int setmonth, int setyear) {
        this.day = setday;
        this.month = setmonth;
        this.year = setyear;
    }

    public CommandRequest getRequest(Byte orderNumber) {
        return new CommandRequest(orderNumber, COMMAND_CODE, this.parameters());
    }

    private List<Byte> parameters() {
        List<Byte> bytes = new ArrayList();
        bytes.add(convertToBCD(day));
        bytes.add(this.convertToBCD(month));
        bytes.add(this.convertToBCD(year % 100));
        return bytes;
    }

    private Byte convertToBCD(int twoDigitsInt) {
        return (new DecimalByte(twoDigitsInt)).toBCD();
    }
}