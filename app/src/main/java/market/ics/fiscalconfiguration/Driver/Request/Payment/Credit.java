package market.ics.fiscalconfiguration.Driver.Request.Payment;

public final class Credit implements PaymentType {
    public Credit() {
    }

    public Byte code() {
        return new Byte("1");
    }

    public String authorizationCode() {
        return null;
    }
}
