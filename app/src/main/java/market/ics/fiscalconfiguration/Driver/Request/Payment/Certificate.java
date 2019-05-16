package market.ics.fiscalconfiguration.Driver.Request.Payment;

public final class Certificate implements PaymentType {
    private final String authorizationCode;

    public Certificate(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public Byte code() {
        return new Byte("4");
    }

    public String authorizationCode() {
        return this.authorizationCode;
    }
}
