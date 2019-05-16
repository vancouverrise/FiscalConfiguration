package market.ics.fiscalconfiguration.Driver.DataType;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SaleQuantity {
    private final BigDecimal value;

    public SaleQuantity(BigDecimal value) {
        if (value.scale() > 3) {
            this.value = value.setScale(3, RoundingMode.HALF_UP);
        } else {
            this.value = value;
        }

    }

    public Integer value() {
        return this.value.unscaledValue().intValue();
    }

    public Integer decimalDigits() {
        return this.value.scale();
    }

    public String toString() {
        return "SaleQuantity [value=" + this.value + "]";
    }
}
