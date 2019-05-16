package market.ics.fiscalconfiguration.Driver.Request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import market.ics.fiscalconfiguration.Driver.CommandRequest;
import market.ics.fiscalconfiguration.Driver.DataType.PrinterPassword;
import market.ics.fiscalconfiguration.Driver.VatTransform;

public final class SetVatRates implements PrinterCommand {

    private static final Byte COMMAND_CODE = 25;

    public SetVatRates() {}


    public CommandRequest getRequest(Byte orderNumber) {
        return new CommandRequest(orderNumber, COMMAND_CODE, this.parameters());
    }

    private List<Byte> parameters() {
        List<Integer> tax = new ArrayList<>();
        tax.add(0,  9999);
        tax.add(1,  5999);
        return VatTransform.setTaxRate(0L, 2, tax, 3, false, false, Collections.singletonList(0), 0, false);
    }
}


