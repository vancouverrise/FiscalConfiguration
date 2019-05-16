package market.ics.fiscalconfiguration.Driver.DataType;

public final class BCDByte {
    private byte bcdByte;

    public BCDByte(byte bcdByte) {
        this.bcdByte = bcdByte;
    }

    public Integer toInteger() {
        byte high = (byte)(this.bcdByte & 240);
        high = (byte)(high >>> 4);
        high = (byte)(high & 15);
        byte low = (byte)(this.bcdByte & 15);
        return new Integer(new String((new StringBuffer()).append(high).append(low)));
    }
}
