package market.ics.fiscalconfiguration.Driver.Request.Payment;

public final class InsurancePayment implements PaymentType {
    public InsurancePayment() {
    }

    public Byte code() {
        return new Byte("7");
    }

    public String authorizationCode() {
        return null;
    }
}
