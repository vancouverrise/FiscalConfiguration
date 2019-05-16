package market.ics.fiscalconfiguration.Driver;

public class Attemp {

    int commandCount = 1;

    public byte nextCommandNumber() {
        if (this.commandCount >= 255) {
            this.commandCount = 0;
        }
        return (byte)(this.commandCount++);
    }

}
