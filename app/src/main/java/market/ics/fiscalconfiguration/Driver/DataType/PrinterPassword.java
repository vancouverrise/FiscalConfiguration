package market.ics.fiscalconfiguration.Driver.DataType;

import java.util.Arrays;
import java.util.List;

public class PrinterPassword {
    private final byte first;
    private final byte second;

    public PrinterPassword(byte first, byte second) {
        this.first = first;
        this.second = second;
    }

    public PrinterPassword(int password) {
        List<Byte> bytes = (new LongByte(password)).asBytes(2);
        this.first = (Byte) bytes.get(1);
        this.second = (Byte) bytes.get(0);
    }

    public List<Byte> bytes() {
        return Arrays.asList(this.second, this.first);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            PrinterPassword that = (PrinterPassword) o;
            if (this.first != that.first) {
                return false;
            } else {
                return this.second == that.second;
            }
        } else {
            return false;
        }
    }
}
