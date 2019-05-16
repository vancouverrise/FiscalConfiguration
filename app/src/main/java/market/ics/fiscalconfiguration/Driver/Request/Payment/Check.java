package market.ics.fiscalconfiguration.Driver.Request.Payment;

public final class Check implements PaymentType {
    private final String authorizationCode;

    public Check(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public Byte code() {
        return new Byte("2");
    }

    public String authorizationCode() {
        return this.authorizationCode;
    }
}
