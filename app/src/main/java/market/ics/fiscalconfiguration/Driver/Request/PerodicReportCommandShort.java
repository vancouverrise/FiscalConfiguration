package market.ics.fiscalconfiguration.Driver.Request;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

import market.ics.fiscalconfiguration.Driver.CommandRequest;
import market.ics.fiscalconfiguration.Driver.DataType.DecimalByte;
import market.ics.fiscalconfiguration.Driver.DataType.PrinterPassword;

public final class PerodicReportCommandShort implements PrinterCommand {
    private static final Byte COMMAND_CODE = 26;
    private final PrinterPassword printerPassword;
    private final LocalDate firstDate;
    private final LocalDate lastDate;

    public PerodicReportCommandShort(PrinterPassword printerPassword, LocalDate firstDate, LocalDate lastDate) {
        this.printerPassword = printerPassword;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
    }

    public CommandRequest getRequest(Byte orderNumber) {
        return new CommandRequest(orderNumber, COMMAND_CODE, this.parameters());
    }

    private List<Byte> parameters() {
        List<Byte> bytes = new ArrayList();
        bytes.addAll(this.printerPassword.bytes());
        bytes.add(this.convertToBCD(this.firstDate.getDayOfMonth()));
        bytes.add(this.convertToBCD(this.firstDate.getMonthValue()));
        bytes.add(this.convertToBCD(this.firstDate.getYear() % 100));
        bytes.add(this.convertToBCD(this.lastDate.getDayOfMonth()));
        bytes.add(this.convertToBCD(this.lastDate.getMonthValue()));
        bytes.add(this.convertToBCD(this.lastDate.getYear() % 100));
        return bytes;
    }

    private Byte convertToBCD(int twoDigitsInt) {
        return (new DecimalByte(twoDigitsInt)).toBCD();
    }

    public String toString() {
        return "PeriodicReportCommand [firstDate=" + this.firstDate + ", lastDate=" + this.lastDate + "]";
    }
}
