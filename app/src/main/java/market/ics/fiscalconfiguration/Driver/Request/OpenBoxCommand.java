package market.ics.fiscalconfiguration.Driver.Request;

import java.util.ArrayList;
import java.util.List;

import market.ics.fiscalconfiguration.Driver.CommandRequest;

public final class OpenBoxCommand implements PrinterCommand {
    private static final Byte COMMAND_CODE = 29;
    private final Integer duration;

    public OpenBoxCommand(Integer duration) {
        this.duration = duration == null ? null : duration > 510 ? 510 : (duration < 0 ? 0 : duration);
    }

    public OpenBoxCommand() {
        this((Integer)null);
    }

    public CommandRequest getRequest(Byte orderNumber) {
        return new CommandRequest(orderNumber, COMMAND_CODE, this.parameters());
    }

    private List<Byte> parameters() {
        List<Byte> bytes = new ArrayList();
        if (this.duration != null) {
            bytes.add((byte)(this.duration / 2));
        }

        return bytes;
    }

    public String toString() {
        return "OpenBoxCommand [duration=" + this.duration + "]";
    }
}
