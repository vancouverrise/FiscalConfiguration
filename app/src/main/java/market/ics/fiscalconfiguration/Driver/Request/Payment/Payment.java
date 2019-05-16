package market.ics.fiscalconfiguration.Driver.Request.Payment;

public final class Payment implements PaymentType {
    private final String authorizationCode;

    public Payment(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public Byte code() {
        return new Byte("9");
    }

    public String authorizationCode() {
        return this.authorizationCode;
    }
}
