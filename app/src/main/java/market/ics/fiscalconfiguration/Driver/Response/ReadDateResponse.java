package market.ics.fiscalconfiguration.Driver.Response;

import java.util.List;

import market.ics.fiscalconfiguration.Driver.DataType.BCDByte;
import market.ics.fiscalconfiguration.Driver.DataType.BCDYear;

public class ReadDateResponse {
    private final List<Byte> bytes;

    public ReadDateResponse(List<Byte> bytes) {
        this.bytes = bytes;
    }

    public org.threeten.bp.LocalDate date() {
        BCDByte day = new BCDByte((Byte)this.bytes.get(0));
        BCDByte month = new BCDByte((Byte)this.bytes.get(1));
        BCDYear year = new BCDYear((Byte)this.bytes.get(2));
        return org.threeten.bp.LocalDate.of(year.toInteger(), month.toInteger(), day.toInteger());
    }



    public String toString() {
        return "ReadDateResponse [Date= " + this.date() + "]";
    }
}