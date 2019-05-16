package market.ics.fiscalconfiguration.Driver.Response;

import java.util.List;

import market.ics.fiscalconfiguration.Driver.DataType.Sym;

public final class ManufacturingDetails {
    private final String serialNumber;
    private final String manufacturingDate;
    private final String softwareVersion;

    public ManufacturingDetails(List<Byte> bytes) {
        String[] serNumAndDate = parse(bytes);
        this.serialNumber = serNumAndDate[0];
        this.manufacturingDate = serNumAndDate[1];
        this.softwareVersion = (new Sym(bytes.subList(bytes.size()-5, bytes.size()))).toString();
    }

    private String[] parse(List<Byte> bytes) {
        String serialNumberAndDate = (new Sym(bytes.subList(2, 21))).toString();
        return serialNumberAndDate.split(" ");
    }

    public String serialNumber() {
        return this.serialNumber;
    }

    public String getManufacturingDate() {
        return this.manufacturingDate;
    }

    public String softwareVersion() {
        return this.softwareVersion;
    }

    public String toString() {
        return "ManufacturingDetails{serialNumber='" + this.serialNumber + '\'' +", softwareVersion='" + this.softwareVersion + '\'' + '}';
    }
}
