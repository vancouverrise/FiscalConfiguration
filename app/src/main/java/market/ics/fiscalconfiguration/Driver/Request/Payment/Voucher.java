package market.ics.fiscalconfiguration.Driver.Request.Payment;

public final class Voucher implements PaymentType {
    private final String authorizationCode;

    public Voucher(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public Byte code() {
        return new Byte("5");
    }

    public String authorizationCode() {
        return this.authorizationCode;
    }
}
