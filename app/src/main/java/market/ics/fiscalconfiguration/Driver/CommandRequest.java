package market.ics.fiscalconfiguration.Driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandRequest {
    private List<Byte> bytes = new ArrayList();


    public CommandRequest(Byte orderNumber, Byte commandCode, List<Byte> parameters) {
        List<Byte> tempArray = new ArrayList<Byte>();
        tempArray.add(orderNumber);
        tempArray.add(commandCode);
        tempArray.addAll(parameters);
        this.bytes = tempArray;
    }

    public CommandRequest(Byte orderNumber, Byte commandCode, Byte... parameter) {
        this.bytes.add(orderNumber);
        this.bytes.add(commandCode);
        this.bytes.addAll(new ArrayList(Arrays.asList(parameter)));
    }

    public CommandRequest(Byte orderNumber, Byte commandCode, Byte DLE, List<Byte> parameters) {
        List<Byte> some = new ArrayList<>();
        some.add(orderNumber);
        some.add(commandCode);
        some.add(DLE);
        some.addAll(parameters);
        this.bytes = some;
        System.out.println(some);
    }

    public CommandRequest(Byte orderNumber, Byte commandCode, List<Byte> password, List<Byte> parameters) {
        List<Byte> some = new ArrayList<>();
        some.add(orderNumber);
        some.add(commandCode);
        System.out.println("SOOOME: " + some);
        System.out.println("SOOOME: " + some);
        System.out.println("SOOOME: " + some);
        some.addAll(password);
        some.addAll(parameters);
        this.bytes = some;
    }


    public List<Byte> getBytes() {
        return this.bytes;
    }
}