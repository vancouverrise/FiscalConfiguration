package market.ics.fiscalconfiguration.Driver.DataType;

import org.threeten.bp.LocalDate;

public final class BCDYear {
    private final BCDByte bcdByte;

    public BCDYear(Byte aByte) {
        this.bcdByte = new BCDByte(aByte);
    }

    public BCDYear(BCDByte bcdByte) {
        this.bcdByte = bcdByte;
    }

    public Integer toInteger() {
        return this.bcdByte.toInteger() + LocalDate.now().getYear() - LocalDate.now().getYear() % 100;
    }
}
