package market.ics.fiscalconfiguration.Driver.DataType;

import java.util.ArrayList;
import java.util.List;

public final class ItemLine {
    private static final long integerNegativeMask = -2147483648L;
    private final SaleQuantity quantity;
    private final Integer price;
    private final SaleItem saleItem;

    public ItemLine(SaleQuantity quantity, Integer price, SaleItem saleItem) {
        this.quantity = quantity;
        this.price = price;
        this.saleItem = saleItem;
    }

    public List<Byte> bytes() {
        List<Byte> bytes = new ArrayList();
        bytes.addAll((new LongByte(this.quantity.value())).asBytes(3));
        bytes.add(this.statusByte());
        bytes.addAll(this.price());
        bytes.addAll(this.saleItem.bytes());
        return bytes;
    }

    private List<Byte> price() {
        long absolutePrice = (long)this.price;
        if (this.price < 0) {
            absolutePrice = (long)Math.abs(this.price);
            absolutePrice |= -2147483648L;
        }

        return (new LongByte(absolutePrice)).asBytes(4);
    }

    private Byte statusByte() {
        byte status = this.quantity.decimalDigits().byteValue();
        byte printBarCodeByte = 0;
        byte printQuantityOne = 0;
        byte rez = (byte)(status | printBarCodeByte | printQuantityOne);
        return new Byte(rez);
    }

    public String toString() {
        return "ItemLine [quantity=" + this.quantity + ", price=" + this.price + ", saleItem=" + this.saleItem + "]";
    }
}
