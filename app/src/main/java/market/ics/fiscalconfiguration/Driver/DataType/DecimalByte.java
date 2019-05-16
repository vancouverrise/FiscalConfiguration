package market.ics.fiscalconfiguration.Driver.DataType;

public final class DecimalByte {
    private final Byte aByte;

    public DecimalByte(Byte aByte) {
        this.aByte = aByte;
    }

    public DecimalByte(int aByte) {
        this.aByte = (byte)aByte;
    }

    public Byte toBCD() {
        int h = this.aByte / 10;
        int l = this.aByte % 10;
        return Byte.decode("0x" + h + l);
    }
}