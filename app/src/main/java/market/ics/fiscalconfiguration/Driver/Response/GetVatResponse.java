package market.ics.fiscalconfiguration.Driver.Response;

import org.threeten.bp.LocalDate;

import java.math.BigDecimal;
import java.util.List;

import market.ics.fiscalconfiguration.Driver.DataType.BCDByte;
import market.ics.fiscalconfiguration.Driver.DataType.BCDYear;
import market.ics.fiscalconfiguration.Driver.DataType.BytesNumber;
import market.ics.fiscalconfiguration.Driver.DataType.TaxGroup;
import market.ics.fiscalconfiguration.Driver.DataType.VatIterator;
import market.ics.fiscalconfiguration.Driver.DataType.VatRate;
import market.ics.fiscalconfiguration.Driver.DataType.VatStatus;
import market.ics.fiscalconfiguration.Fragments.fragment_info;

public class GetVatResponse {
    private List<Byte> bytes;
    private TaxGroup tempVAT;
    private BigDecimal tempValue;



    public GetVatResponse(List<Byte> bytes) {

        this.bytes = bytes;
    }

    public LocalDate getProgrammingDate() {
        BCDByte day = new BCDByte(this.bytes.get(1));
        BCDByte month = new BCDByte(this.bytes.get(2));
        BCDYear year = new BCDYear(this.bytes.get(3));
        return LocalDate.of(year.toInteger(), month.toInteger(), day.toInteger());
    }

    public VatStatus getStatus() {
        Byte statusByte = this.bytes.get(4 + 2 * this.bytes.get(0));
        return new VatStatus(statusByte);
    }

    public List<VatRate> getVats() {
        int numberOfVats = this.bytes.get(0);
        List<Byte> vats = this.bytes.subList(4, 4 + 2 * numberOfVats);

        return this.getVats(numberOfVats, vats);
    }

    private List<VatRate> getVats(int numberOfVats, List<Byte> bytes) {
        VatIterator iterator = new VatIterator(numberOfVats);

        for(int i = 0; i < bytes.size(); i += 2) {
            if (iterator.hasNext()) {
                VatRate vat = iterator.next();


                vat.setValue(this.getVat(bytes.subList(i, i + 2)));
                iterator.replace(vat);
                System.out.println("Iterator + " + iterator.getVats());

                System.out.println("kekeke " + tempVAT);
                System.out.println("kekeke " + tempValue);
            }
        }


        return iterator.getVats();
    }

    private BigDecimal getVat(List<Byte> bytes) {
        return BigDecimal.valueOf((long)(new BytesNumber(bytes)).toInteger()).divide(BigDecimal.valueOf(100L));
    }

}