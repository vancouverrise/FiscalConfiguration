package market.ics.fiscalconfiguration.Driver.Response;

import java.util.List;

import market.ics.fiscalconfiguration.Driver.DataType.BytesNumber;

public final class GetBoxSumResponse {
    private final List<Byte> bytes;

    public GetBoxSumResponse(List<Byte> bytes) {
        this.bytes = bytes;
    }

    public Long amount() {
        return (new BytesNumber(this.bytes)).toLong();
    }

    public String toString() {
        if (this.amount() == 0){
            return "0,00 UAH";
        }
        return new StringBuilder(this.amount().toString()).insert(String.valueOf(this.amount()).length() -2, ",") + " UAH";

    }
}
