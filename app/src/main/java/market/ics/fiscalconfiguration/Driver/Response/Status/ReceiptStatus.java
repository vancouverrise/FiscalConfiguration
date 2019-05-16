package market.ics.fiscalconfiguration.Driver.Response.Status;

import java.util.Optional;

import market.ics.fiscalconfiguration.Driver.DataType.ByteBits;

public class ReceiptStatus {
    private final ByteBits bits;

    public ReceiptStatus(ByteBits bits) {
        this.bits = bits;
    }

    public boolean isOpen() {
        return this.bits.getSeventh();
    }

    public Optional<? extends Object> isSaleReceipt() {
        return this.isOpen() ? Optional.of(new Boolean(!this.bits.getForth())) : Optional.empty();
    }

    public Optional<? extends Object> isRefundReceipt() {
        return this.isOpen() ? Optional.of(new Boolean(this.bits.getForth())) : Optional.empty();
    }

    public Optional<? extends Object> isPaymentMode() {
        return this.isOpen() ? Optional.of(new Boolean(this.bits.getSecond())) : Optional.empty();
    }

    public String toString() {
        return "\tReceiptStatus\n\t{\n" + this.print("Is receipt open", this.isOpen()) + (this.isSaleReceipt().isPresent() && (Boolean)this.isSaleReceipt().get() ? "\t\tIs sale receipt" : "") + (this.isRefundReceipt().isPresent() && (Boolean)this.isRefundReceipt().get() ? "\t\tIs refund receipt" : "") + "\n" + (this.isPaymentMode().isPresent() ? this.print("Is payment mode", (Boolean)this.isPaymentMode().get()) : "") + "\t}";
    }

    private String print(String propText, boolean cond) {
        return "\t\t" + propText + ": " + (cond ? "yes" : "no") + "\n";
    }
}