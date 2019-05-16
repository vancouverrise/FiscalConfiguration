package market.ics.fiscalconfiguration.Driver.DataType;

public class VatStatus {
    private static final int NUMBER_OF_DECIMALS = 2;
    private Byte status = new Byte((byte)0);

    public VatStatus(Byte status) {
        this.status = status;
    }

    public VatStatus(int decimalPlaces, boolean isVatEmbedded) {
        this.setDecimalPlaces(decimalPlaces);
        this.setEmbedded(isVatEmbedded);
    }

    public int getDecimalPlaces() {
        return this.status & 15;
    }

    public void setDecimalPlaces(int decimalPlaces) {
        Byte decimals = (byte)(decimalPlaces & 15);
        this.status = (byte)(this.status | decimals);
    }

    public boolean isEmbedded() {
        return !(new ByteBits(this.status)).getFifth();
    }

    public void setEmbedded(boolean isEmbedded) {
        if (!isEmbedded) {
            this.status = (byte)(this.status | 16);
        }

    }

    public boolean isChargePresent() {
        return (new ByteBits(this.status)).getSixth();
    }

    public void setChargePresent(boolean isChargePresent) {
        if (isChargePresent) {
            this.status = (byte)(this.status | 32);
        }

    }


    private byte getDecimals(byte status) {
        byte decimals = (byte)(this.getDecimalPlaces() & 15);
        status |= decimals;
        if (status == 0) {
            status = (byte)(status | 2);
        }

        return status;
    }

    private byte getVatType(byte status) {
        if (!this.isEmbedded()) {
            status = (byte)(status | 16);
        }

        return status;
    }

    private byte getProgramChargeRates(byte status) {
        if (this.isChargePresent()) {
            status = (byte)(status | 32);
        }

        return status;
    }
}