package market.ics.fiscalconfiguration.Driver.DataType;

import java.util.ArrayList;
import java.util.List;

public final class LongByte {
    private final Long value;

    public LongByte(Long value) {
        this.value = value;
    }

    public LongByte(Integer value) {
        this.value = value.longValue();
    }

    public List<Byte> asBytes(int numberOfBytes) {
        List<Byte> bytes = new ArrayList();
        long longValue = this.value;

        for(int i = numberOfBytes; i > 0; --i) {
            bytes.add((byte)((int)(longValue & 255L)));
            longValue >>= 8;
        }

        return bytes;
    }
}
