package market.ics.fiscalconfiguration.Driver.DataType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class SaleItem {
    private final PrinterString name;
    private final BigInteger code;
    private final TaxGroup taxGroup;
    private BigInteger maxCodeValue = new BigInteger("281474976710655");

    public SaleItem(PrinterString name, BigInteger code, TaxGroup taxGroup) {
        this.name = name;
        this.code = code;
        this.taxGroup = taxGroup;
        this.validate();
    }

    private void validate() {
        if (this.maxCodeValue.compareTo(this.code) < 0) {
            throw new RuntimeException("Item code could not be more than " + this.maxCodeValue);
        }
    }

    public List<Byte> bytes() {
        List<Byte> bytes = new ArrayList();

        bytes.add(this.taxGroup.toByte());
        this.addName(bytes);
        bytes.addAll(this.codeToListOfBytes());
        return bytes;
    }

    private Collection<? extends Byte> codeToListOfBytes() {
        List<Byte> bytes = new ArrayList();
        byte[] rowBytes = this.code.toByteArray();
        int maxArrayLength = rowBytes.length < 7 ? rowBytes.length : 6;

        for(int i = maxArrayLength - 1; i >= 0; --i) {
            bytes.add(rowBytes[i]);
        }

        return bytes;
    }



    private void addName(List<Byte> bytes) {
        if (this.name != null) {
            bytes.add((byte)this.name.length());
            bytes.addAll(this.name.bytes());
        } else {
            bytes.add((byte)-1);
        }

    }

    public String toString() {
        return "SaleItem [name=" + this.name + ", code=" + this.code + ", taxGroup=" + this.taxGroup + "]";
    }
}