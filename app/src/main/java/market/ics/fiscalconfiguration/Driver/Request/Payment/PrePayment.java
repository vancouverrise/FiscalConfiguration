package market.ics.fiscalconfiguration.Driver.Request.Payment;

public final class PrePayment implements PaymentType {
    private final String authorizationCode;

    public PrePayment(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public Byte code() {
        return new Byte("8");
    }

    public String authorizationCode() {
        return this.authorizationCode;
    }
}
