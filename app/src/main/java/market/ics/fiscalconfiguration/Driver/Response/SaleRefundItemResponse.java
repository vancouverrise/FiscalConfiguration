package market.ics.fiscalconfiguration.Driver.Response;

import java.util.List;

import market.ics.fiscalconfiguration.Driver.DataType.BytesNumber;

public class SaleRefundItemResponse {
    private final List<Byte> bytes;

    public SaleRefundItemResponse(List<Byte> bytes) {
        this.bytes = bytes;
    }

    public Long itemPrice() {
        return (new BytesNumber(this.bytes.subList(0, 4))).toLong();
    }

    public Long receiptSum() {
        return (new BytesNumber(this.bytes.subList(4, 8))).toLong();
    }

    public String toString() {
        return "SaleResponse [price= " + this.itemPrice() + ", " + "receiptSum= " + this.receiptSum() + "]";
    }
}
