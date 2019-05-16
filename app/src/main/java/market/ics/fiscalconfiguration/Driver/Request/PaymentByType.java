package market.ics.fiscalconfiguration.Driver.Request;

import java.util.List;

public final class PaymentByType {
    private final List<Integer> payment;

    public PaymentByType(List<Integer> payment) {
        this.payment = payment;
    }

    public Integer getCardPayment() {
        return (Integer)this.payment.get(0);
    }

    public Integer getCreditPayment() {
        return (Integer)this.payment.get(1);
    }

    public Integer getReceiptPayment() {
        return (Integer)this.payment.get(2);
    }

    public Integer getCashPayment() {
        return (Integer)this.payment.get(3);
    }

    public Integer getCertificatPayment() {
        return (Integer)this.payment.get(4);
    }

    public Integer getVaucherPayment() {
        return (Integer)this.payment.get(5);
    }

    public Integer getElectronicMoneyPayment() {
        return (Integer)this.payment.get(6);
    }

    public Integer getInsurancePayment() {
        return (Integer)this.payment.get(7);
    }

    public Integer getOverPayment() {
        return (Integer)this.payment.get(8);
    }

    public Integer getPayment() {
        return (Integer)this.payment.get(9);
    }
}
