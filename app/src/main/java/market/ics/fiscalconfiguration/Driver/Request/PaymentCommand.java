package market.ics.fiscalconfiguration.Driver.Request;

import java.util.ArrayList;
import java.util.List;

import market.ics.fiscalconfiguration.Driver.CommandRequest;
import market.ics.fiscalconfiguration.Driver.DataType.LongByte;
import market.ics.fiscalconfiguration.Driver.DataType.PrinterString;
import market.ics.fiscalconfiguration.Driver.Request.Payment.PaymentType;

public class PaymentCommand implements PrinterCommand {
    public final Byte COMMAND_CODE = 20;
    private final PaymentType paymentType;
    private final boolean isFiscal;
    private final Long amount;

    public PaymentCommand(PaymentType paymentType, boolean isFiscal, Long amount) {
        this.paymentType = paymentType;
        this.isFiscal = isFiscal;
        this.amount = amount;
    }

    public CommandRequest getRequest(Byte orderNumber) {
        return new CommandRequest(orderNumber, this.COMMAND_CODE, this.parameters());
    }

    private List<Byte> parameters() {
        ArrayList<Byte> parameters = new ArrayList();
        if (!this.isFiscal) {
            parameters.add(this.notFiscalBit());
        } else {
            parameters.add(this.paymentType.code());
        }

        parameters.addAll((new LongByte(this.amount)).asBytes(4));
        parameters.add((byte)0);
        if (this.paymentType.authorizationCode() != null) {
            parameters.add((byte)this.paymentType.authorizationCode().length());
            parameters.addAll((new PrinterString(this.paymentType.authorizationCode())).bytes());
        }

        return parameters;
    }

    private Byte notFiscalBit() {
        return (byte)(this.paymentType.code() | 64);
    }

    public String toString() {
        return "PaymentCommand [paymentType=" + this.paymentType + ", isFiscal=" + this.isFiscal + ", amount=" + this.amount + "]";
    }
}
