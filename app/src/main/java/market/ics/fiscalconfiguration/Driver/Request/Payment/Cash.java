package market.ics.fiscalconfiguration.Driver.Request.Payment;

public final class Cash implements PaymentType {
    public Cash() {
    }

    public Byte code() {
        return new Byte("3");
    }

    public String authorizationCode() {
        return null;
    }

    public String toString() {
        return "Cash []";
    }
}
