package market.ics.fiscalconfiguration.Driver.Request.Payment;

public final class ElectronicMoney implements PaymentType {
    private final String authorizationCode;

    public ElectronicMoney(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public Byte code() {
        return new Byte("6");
    }

    public String authorizationCode() {
        return this.authorizationCode;
    }
}
