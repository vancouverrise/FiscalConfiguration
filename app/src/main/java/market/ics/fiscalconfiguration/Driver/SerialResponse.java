package market.ics.fiscalconfiguration.Driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import market.ics.fiscalconfiguration.Communication.Commands;

public class SerialResponse {
    private final List<Byte> start;
    private final List<Byte> end;
    List<Byte> receiverPacket = new ArrayList<>();
    private List<Byte> response;
    private Commands socketSendNew = new Commands();



    public SerialResponse() {
        this.start = Arrays.asList((byte)16, (byte)2);
        this.end = Arrays.asList((byte)16,(byte) 3);
        this.response = new ArrayList();

    }


    public byte[] getAsBytes() {
        byte[] result = new byte[this.response.size()];

        for(int i = 0; i < this.response.size(); ++i) {
            result[i] = (Byte)this.response.get(i);
        }

        return result;
    }


    public List<Byte> dataBytes(List<Byte> receiverPacket) {
        List<Byte> data = new ArrayList<>(receiverPacket);
        this.receiverPacket = data;
        List<Byte> messageWithNoDLE = removeDLE();
        if (isValid(messageWithNoDLE)) {
            return this.removeCheckSum(messageWithNoDLE);
        } else {
            return null;
        }
    }



    private List<Byte> removeCheckSum(List<Byte> messageWithCheckSum) {
        return messageWithCheckSum.subList(0, messageWithCheckSum.size() - 1);
    }

    private List<Byte> removeDLE() {
        List<Byte> packetWithDLE = this.extractMessageWithCheckSum(receiverPacket);

        List<Byte> packetWithOutDLE = removeDLEDuplicates(packetWithDLE);
        System.out.println("Before DLE Transform: " + packetWithDLE);
        System.out.println("After DLE Transform " + packetWithOutDLE);
        return packetWithOutDLE;
    }

    private List<Byte> extractMessageWithCheckSum(List<Byte> data) {
        int startIndex = Collections.indexOfSubList(data, this.start);
        int endIndex = Collections.lastIndexOfSubList(data, this.end);
        return data.subList(startIndex + 2, endIndex);
    }

    private boolean isValid(List<Byte> bytes) {
        System.out.println("Control summ is: " + new CheckSumImpl(bytes).calculate());
        return (new CheckSumImpl(bytes)).calculate() == 0;
    }

    private List<Byte> removeDLEDuplicates(List<Byte> temp) {
        List<Byte> data = new ArrayList<>(temp);
        List<Byte> responseData = new ArrayList();
        int i = Collections.indexOfSubList(data, Arrays.asList((byte)16,(byte) 16));
        if (i < 0) {
            return data;
        } else {
            while(i >= 0) {
                List<Byte> repl = data.subList(i, i + 2);
                repl.clear();
                repl.add((byte)16);
                responseData.addAll(data.subList(0, i + 1));
                List<Byte> remove = data.subList(0, i + 1);
                remove.clear();
                i = Collections.indexOfSubList(data, Arrays.asList((byte) 16, (byte)16));
            }

            responseData.addAll(data);
            return responseData;
        }
    }


}
