package market.ics.fiscalconfiguration.Driver.DataType;

import java.net.Socket;
import java.util.List;

public final class BytesNumber {
    private final List<Byte> bytes;

    public BytesNumber(List<Byte> bytes) {
        this.bytes = bytes;
    }

    public Long toLong() {
        if (this.bytes.isEmpty()) {
            return null;
        } else {
            this.validateLength(8);
            long result = 0L;

            for(int i = this.bytes.size() - 1; i >= 0; --i) {
                result <<= 8;
                result |= (long)((Byte)this.bytes.get(i) & 255);
            }

            return result;
        }
    }

    public Integer toInteger() {
        if (this.bytes.isEmpty()) {
            return null;
        } else {
            this.validateLength(4);
            int result = 0;

            for(int i = this.bytes.size() - 1; i >= 0; --i) {
                result <<= 8;
                result |= (Byte)this.bytes.get(i) & 255;
            }

            return result;
        }
    }


    private void validateLength(int maxBytes) {
        if (this.bytes.size() > maxBytes) {
            throw new NumberFormatException("Value out of range. Max number of allowed bytes is " + maxBytes + " .");
        }
    }
}