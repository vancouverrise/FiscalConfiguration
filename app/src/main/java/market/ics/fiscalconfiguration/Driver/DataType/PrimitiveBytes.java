package market.ics.fiscalconfiguration.Driver.DataType;

import java.util.ArrayList;
import java.util.List;

public final class PrimitiveBytes {
    private final byte[] primitiveBytes;

    public PrimitiveBytes(byte[] primitiveBytes) {
        this.primitiveBytes = primitiveBytes;
    }

    public List<Byte> toListOfBytes() {
        List<Byte> bytes = new ArrayList();

        for(int i = 0; i < this.primitiveBytes.length; ++i) {
            bytes.add(this.primitiveBytes[i]);
        }

        return bytes;
    }
}
