package market.ics.fiscalconfiguration.Driver.DataType;

import java.util.LinkedList;
import java.util.List;

public enum TaxGroup {
    A,
    B,
    C,
    D,
    E,
    EXEMPT;

    private TaxGroup() {
    }

    public byte toByte() {
        switch(this) {
            case A:
                return this.getByte("А");
            case B:
                return this.getByte("Б");
            case C:
                return this.getByte("В");
            case D:
                return this.getByte("Г");
            case E:
                return this.getByte("Д");
            case EXEMPT:
                return this.getByte("Е");
            default:
                throw new IllegalStateException("Enum with value " + this + "is not added to the switch statement.");
        }
    }

    private byte getByte(String letter) {
        return (Byte)(new PrinterString(letter)).bytes().get(0);
    }

    public static List<TaxGroup> getTaxGroupList(int size) {
        TaxGroup[] taxGroups = values();
        if (size > taxGroups.length) {
            return null;
        } else {
            List<TaxGroup> taxGroupList = new LinkedList();

            for(int i = 0; i < size; ++i) {
                taxGroupList.add(taxGroups[i]);
            }

            return taxGroupList;
        }
    }
}