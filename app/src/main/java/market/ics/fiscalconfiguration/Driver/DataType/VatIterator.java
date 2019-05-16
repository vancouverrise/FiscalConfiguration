package market.ics.fiscalconfiguration.Driver.DataType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class VatIterator implements Iterator<VatRate> {
    private int index = 0;
    private List<VatRate> vats = new ArrayList();

    public VatIterator(int numberOfVats) {
        TaxGroup.getTaxGroupList(numberOfVats).forEach((v) -> this.vats.add(new VatRate(v, BigDecimal.ZERO)));
    }

    public boolean hasNext() {
        return this.index < this.vats.size();
    }

    public VatRate next() {
        return this.vats.get(this.index++);
    }

    public void replace(VatRate vat) {
        this.vats.set(this.index - 1, vat);
    }

    public List<VatRate> getVats() {
        return this.vats;
    }
}
