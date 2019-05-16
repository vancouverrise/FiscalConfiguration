package market.ics.fiscalconfiguration.Driver;

import java.util.List;

public final class CommandResponse {

    private final List<Byte> bytes;


    public CommandResponse(List<Byte> bytes) {
        this.bytes = bytes;
    }

    public Byte getOrderNumber() {
        return (Byte)this.bytes.get(0);
    }

    public Byte getCommandCode() {
        return (Byte)this.bytes.get(1);
    }

    public List<Byte> getData() {
        System.out.println("Packet: " + bytes);
        System.out.println("Data: " + bytes.subList(5, bytes.size()));
        return  bytes.subList(5, bytes.size());

    }


}