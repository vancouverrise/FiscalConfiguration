package market.ics.fiscalconfiguration.Driver;

import com.google.common.primitives.Bytes;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import market.ics.fiscalconfiguration.Driver.DataType.ErrorICSE810T;
import market.ics.fiscalconfiguration.Fragments.VariablesClass;

public class ResponseFromPrinter {
    private static List<Byte> bytes;
    private static List<Byte> start;
    private static List<Byte> end;

    private static byte[] notListArray;

    private static List<Byte> receiverPacket = new ArrayList<>();

    public static void decode(List<Byte> bytes) {
        VariablesClass.setIfThereResponseFromPrinter(true);
        if (bytes == null) {
            System.out.println("Nothing to decode");
            VariablesClass.setIfThereResponseFromPrinter(false);
        }
        else {

            ResponseFromPrinter.bytes = bytes;
            start = Arrays.asList((byte) 16, (byte) 2);
            end = Arrays.asList((byte) 16, (byte) 3);

            dataBytes(bytes);
            showError(bytes);
            VariablesClass.setPacket(getData());

            ResponseFromPrinter.bytes = null;
            ResponseFromPrinter.start = null;
            ResponseFromPrinter.end = null;
        }
    }

    public static List<Byte> getData() {
        System.out.println("Packet: " + bytes);
        System.out.println("Data: " + bytes.subList(5, bytes.size()));
        notListArray = Bytes.toArray(bytes);
        return bytes.subList(5, bytes.size());

    }

    public static List<Byte> dataBytes(List<Byte> receiverPacket) {
        List<Byte> data = new ArrayList<>(receiverPacket);
        ResponseFromPrinter.receiverPacket = data;
        List<Byte> messageWithNoDLE = removeDLE();
        if (isValid(messageWithNoDLE)) {
            bytes = removeCheckSum(messageWithNoDLE);
            return removeCheckSum(messageWithNoDLE);
        } else {
            return null;
        }
    }

    private static List<Byte> removeCheckSum(List<Byte> messageWithCheckSum) {
        return messageWithCheckSum.subList(0, messageWithCheckSum.size() - 1);
    }

    private static List<Byte> removeDLE() {
        List<Byte> packetWithDLE = extractMessageWithCheckSum(receiverPacket);

        List<Byte> packetWithOutDLE = removeDLEDuplicates(packetWithDLE);
        System.out.println("Before DLE Transform: " + packetWithDLE);
        System.out.println("After DLE Transform " + packetWithOutDLE);
        return packetWithOutDLE;
    }

    private static List<Byte> extractMessageWithCheckSum(List<Byte> data) {
        int startIndex = Collections.indexOfSubList(data, start);
        int endIndex = Collections.lastIndexOfSubList(data, end);
        return data.subList(startIndex + 2, endIndex);
    }

    private static boolean isValid(List<Byte> bytes) {
        System.out.println("Control summ is: " + new CheckSumImpl(bytes).calculate());
        return (new CheckSumImpl(bytes)).calculate() == 0;
    }

    private static List<Byte> removeDLEDuplicates(List<Byte> temp) {
        List<Byte> data = new ArrayList<>(temp);
        List<Byte> responseData = new ArrayList();
        int i = Collections.indexOfSubList(data, Arrays.asList((byte) 16, (byte) 16));
        if (i < 0) {
            return data;
        } else {
            while (i >= 0) {
                List<Byte> repl = data.subList(i, i + 2);
                repl.clear();
                repl.add((byte) 16);
                responseData.addAll(data.subList(0, i + 1));
                List<Byte> remove = data.subList(0, i + 1);
                remove.clear();
                i = Collections.indexOfSubList(data, Arrays.asList((byte) 16, (byte) 16));
            }

            responseData.addAll(data);
            return responseData;
        }
    }

    private static void showError(List<Byte> packetForErrors) {
        if (bytes.size() >=5) {
            status(bytes.get(2));
            result((int) getNumber(bytes.get(3)));
            reserv(bytes.get(4));
            System.out.println("Error bytes: " + bytes.get(2) +" "+ bytes.get(3) +" "+ bytes.get(4));
        } else ErrorICSE810T.nullResponse();
    }

    private static void status(byte status) {
        if ((status & 0x00) != 0)
            ErrorICSE810T.status0();
        if ((status & 0x02) != 0)
            ErrorICSE810T.status1();
        if ((status & 0x04) != 0)
            ErrorICSE810T.status2();
        if ((status & 0x08) != 0)
            ErrorICSE810T.status3();
        if ((status & 0x10) != 0)
            ErrorICSE810T.status4();
        if ((status & 0x20) != 0)
            ErrorICSE810T.status5();
        if ((status & 0x40) != 0)
            ErrorICSE810T.status6();
        if ((status & 0x80) != 0)
            ErrorICSE810T.status7();
    }

    private static void reserv(byte reserv) {
        if ((reserv & 0x00) != 0)
            ErrorICSE810T.reserv0();
        if ((reserv & 0x02) != 0)
            ErrorICSE810T.reserv1();
        if ((reserv & 0x04) != 0)
            ErrorICSE810T.reserv2();
        if ((reserv & 0x08) != 0)
            ErrorICSE810T.reserv3((reserv & 0x08));
        if ((reserv & 0x10) != 0)
            ErrorICSE810T.reserv4();
        if ((reserv & 0x20) != 0)
            ErrorICSE810T.reserv5();
        if ((reserv & 0x40) != 0)
            ErrorICSE810T.reserv6();
        if ((reserv & 0x80) != 0)
            ErrorICSE810T.reserv7();
    }

    private static void result(int result) {
        switch (result) {
            case 0:
                ErrorICSE810T.result0();
                break;
            case 1:
                ErrorICSE810T.result1();
                break;
            case 2:
                ErrorICSE810T.result2();
                break;
            case 4:
                ErrorICSE810T.result4();
                break;
            case 6:
                ErrorICSE810T.result6();
                break;
            case 8:
                ErrorICSE810T.result8();
                break;
            case 10:
                ErrorICSE810T.result10();
                break;
            case 16:
                ErrorICSE810T.result16();
                break;
            case 19:
                ErrorICSE810T.result19();
                break;
            case 20:
                ErrorICSE810T.result20();
                break;
            case 21:
                ErrorICSE810T.result21();
                break;
            case 22:
                ErrorICSE810T.result22();
                break;
            case 23:
                ErrorICSE810T.result23();
                break;
            case 24:
                ErrorICSE810T.result24();
                break;
            case 25:
                ErrorICSE810T.result25();
                break;
            case 26:
                ErrorICSE810T.result26();
                break;
            case 27:
                ErrorICSE810T.result27();
                break;
            case 28:
                ErrorICSE810T.result28();
                break;
            case 30:
                ErrorICSE810T.result30();
                break;
            case 31:
                ErrorICSE810T.result31();
                break;
            case 32:
                ErrorICSE810T.result32();
                break;
            case 33:
                ErrorICSE810T.result33();
                break;
            case 34:
                ErrorICSE810T.result34();
                break;
            case 35:
                ErrorICSE810T.result35();
                break;
            case 36:
                ErrorICSE810T.result36();
                break;
            case 37:
                ErrorICSE810T.result37();
                break;
            case 38:
                ErrorICSE810T.result38();
                break;
            case 39:
                ErrorICSE810T.result39();
                break;
            case 40:
                ErrorICSE810T.result40();
                break;
            case 41:
                ErrorICSE810T.result41();
                break;
            case 42:
                ErrorICSE810T.result42();
                break;
            case 43:
                ErrorICSE810T.result43();
                break;
            case 44:
                ErrorICSE810T.result44();
                break;
            case 45:
                ErrorICSE810T.result45();
                break;
            case 46:
                ErrorICSE810T.result46();
                break;
            case 47:
                ErrorICSE810T.result47();
                break;
            case 48:
                ErrorICSE810T.result48();
        }
    }

    public static long getNumber(byte b) {
        return getNumber(new byte[]{b});
    }

    public static long getNumber(byte[] mas) {
        long number = (mas[0] & 0xFF);

        for (int i = 1; i < mas.length; i++)
            number += (mas[i] & 0xFF) * (long) (0x01 << (8 * i));

        return number;
    }

    public static byte[] getNotListArray() {
        return notListArray;
    }
}
