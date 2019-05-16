package market.ics.fiscalconfiguration.Driver.Request.Payment;

public final class Card implements PaymentType {
    private final String authorizationCode;

    public Card(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public Byte code() {
        return new Byte("0");
    }

    public String authorizationCode() {
        return this.authorizationCode;
    }
}
