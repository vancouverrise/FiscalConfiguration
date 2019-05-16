package market.ics.fiscalconfiguration.Communication;

import com.google.common.io.BaseEncoding;

import org.apache.commons.lang3.ArrayUtils;
import org.threeten.bp.LocalDate;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import market.ics.fiscalconfiguration.Driver.DataType.DecimalByte;
import market.ics.fiscalconfiguration.Driver.DataType.WorkByte;
import market.ics.fiscalconfiguration.Driver.PacketBuilder;
import market.ics.fiscalconfiguration.Driver.Request.PrinterCommand;
import market.ics.fiscalconfiguration.Driver.ResponseFromPrinter;
import market.ics.fiscalconfiguration.Fragments.VariablesClass;

import static market.ics.fiscalconfiguration.Fragments.fragment_info.tv_a;

public class Commands extends Thread {


    private PacketBuilder PACKET = new PacketBuilder();
    private ByteBuffer buffer;

    BluetoothConnector bl = new BluetoothConnector();


    private byte[] RECEIVED_PACKET;

    private static short COMMAND_COUNTER = 1;

    private static InputStream in;
    private static OutputStream out;

    private static BufferedInputStream BUFFERINPUTSTREAM;

    private static int count = 0;
    private static int index;


    public static List<Byte> sendMessageBoss(int commandNumber, List<Byte> parameters) {
        ByteBuffer BUFFER_NEW = ByteBuffer.allocate(1024);
        List<Byte> commandToSend = new ArrayList<>();
        commandToSend.add(WorkByte.DLE.getValue());
        commandToSend.add(WorkByte.STX.getValue());
        commandToSend.add((byte) COMMAND_COUNTER++);
        commandToSend.add((byte) commandNumber);

        if (parameters != null) {
            commandToSend.addAll(createParametersArray(parameters));
        }
        System.out.println("great!");
        commandToSend.add((byte) 0x00);
        commandToSend.add(WorkByte.DLE.getValue());
        commandToSend.add(WorkByte.ETX.getValue());

        //CheckSum
        commandToSend.set(commandToSend.size() - 3, getCheckSum(commandToSend));


        int listCurrentSize = commandToSend.size();
        for (int pos = 2; pos < listCurrentSize - 2; pos++) {
            if (commandToSend.get(pos).equals(WorkByte.DLE.getValue())) {
                commandToSend.add(pos + 1, WorkByte.DLE.getValue());
                pos++;
                listCurrentSize++;
            }
        }

        BluetoothConnector bl = new BluetoothConnector();


        if (!bl.getBluetoothSocket().isConnected()){
           return null;
        }

        try {
            in = bl.getBluetoothSocket().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out = bl.getBluetoothSocket().getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            out.flush();
            System.out.println("Sending this: " + commandToSend);
            out.write(ArrayUtils.toPrimitive(commandToSend.toArray(new Byte[commandToSend.size()])));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BUFFERINPUTSTREAM = new BufferedInputStream(in);

        while (true) {
            int b = 0;
            try {
                b = BUFFERINPUTSTREAM.read();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Something wrong" + e);
            }

            BUFFER_NEW.put((byte) b);
            if ( BUFFER_NEW.get(0) == (byte) 6) {
                System.out.println("Confirmed");
                BUFFER_NEW.position(0);
            }
            if ( BUFFER_NEW.get(0) == (byte) 22) {
                System.out.println("Processing....");
                BUFFER_NEW.position(0);
            }
            if ( BUFFER_NEW.position() > 10) {

                if (BUFFER_NEW.get( BUFFER_NEW.position() - 4) == (byte) 16 &&  BUFFER_NEW.get( BUFFER_NEW.position() - 3) == (byte) 3 && BUFFER_NEW.get(BUFFER_NEW.position() - 5) != (byte) 16)

                {
                    break;
                }
            }
        }
        byte[] RECEIVED_PACKET_NEW = new byte[0] ;


        if (BUFFER_NEW.hasArray()) {
            System.out.println("Buffer before CLEAR: " + Arrays.toString(BUFFER_NEW.array()));

            System.out.println("Packet Transforming...");
            RECEIVED_PACKET_NEW = BUFFER_NEW.array();
        }

        BUFFER_NEW.clear();

        System.out.println("Buffer after CLEAR: " + Arrays.toString(BUFFER_NEW.array()));

        index = RECEIVED_PACKET_NEW.length;


        while (RECEIVED_PACKET_NEW[index-1] == (byte)0){
            index--;
        }

        byte[] finalPacket = new byte[index];

        for (Byte i: RECEIVED_PACKET_NEW){
            if (count < index) {
                finalPacket[count] = i;
                count++;
            }
        }

        index = 0;
        count = 0;

        System.out.println("Packer after1 Transforming: " + Arrays.toString(finalPacket));

        COMMAND_COUNTER++;

        return Arrays.asList(ArrayUtils.toObject(finalPacket));

    }

    public static void sendCustomer(boolean isTop, String line) {

        List<Byte> param = new ArrayList<Byte>();
        param.add((byte) ((isTop) ? 0 : 1));
        param.add((byte) line.length());

        try {
            for (byte b : replaceLetter(line).getBytes("IBM866"))
                param.add(b);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ResponseFromPrinter.decode(sendMessageBoss(27, param));
    }

    /**
     * Code 9
     * Print day report.
     * X - report.
     * @param password report password.
     */
    public static void dayReport(long password) {
        List<Byte> param = new ArrayList<>();
        for (byte b : getBytes(password, 2))
            param.add(b);
        ResponseFromPrinter.decode(sendMessageBoss(9, param));
    }


    public static void getDate() {
        ResponseFromPrinter.decode(sendMessageBoss(1, null));
    }

    public static void setDate(int day, int month, int year){
        int tempYear = year;
        int tempMonth = month;
        int tempDay = day;
        System.out.println(tempDay +" "+ tempMonth +""+ tempYear);

        List<Byte> parameters = new ArrayList();
        parameters.add(convertToBCD(tempDay));
        parameters.add(convertToBCD(tempMonth));
        parameters.add(convertToBCD(tempYear % 100));

        ResponseFromPrinter.decode(sendMessageBoss(2, parameters));
    }

    public static void getTime(){
        ResponseFromPrinter.decode(sendMessageBoss(3, null));
    }

    public static void setTime(int hours, int minutes) {
        List<Byte> parameters = new ArrayList();
        parameters.add(convertToBCD(hours));
        parameters.add(convertToBCD(minutes));
        parameters.add(convertToBCD(0));

        ResponseFromPrinter.decode(sendMessageBoss(4, parameters));
    }

    private static Byte convertToBCD(int twoDigitsInt) {
        return (new DecimalByte(twoDigitsInt)).toBCD();
    }
    /**
     * Code 13
     * Printing and registration of the day report on financial transactions with resetting to zero of day registers.
     * Z - report.
     * @param password report password.
     */
    public static void dayClrReport(long password) {
        List<Byte> param = new ArrayList<>();
        for (byte b : getBytes(password, 2))
            param.add(b);
        ResponseFromPrinter.decode(sendMessageBoss(13, param));
    }


    /**
     * Code 14
     * <p>
     * Command for promotion of the paper for one line.
     */
    public static void lineFeed() {
        ResponseFromPrinter.decode(sendMessageBoss(14, null));
    }

    /**
     * Code 25
     * <p>
     * Set tax rate.
     */
    public static void setTaxRate(Long password, int countTaxRate, List<Integer> taxRate, int doseDecimal, boolean typeVAT,
                           boolean programTaxRate, List<Integer> rate, Integer eRate) {
        List<Byte> param = new ArrayList<Byte>();

        for (byte b : getBytes(password, 2))
            param.add(b);

        param.add((byte) countTaxRate);

        for (int tax : taxRate)
            for (byte b : getBytes(tax, 2))
                param.add(b);

        param.add(getStatusForTaxRate(doseDecimal, typeVAT, programTaxRate));


        for (int tax : rate)
            for (byte b : getBytes(tax, 2))

                param.add(b);


        for (byte b : getBytes(eRate, 2))
            param.add(b);

        ResponseFromPrinter.decode(sendMessageBoss(25, param));
    }

    /**
     * Code 26
     * <p>
     * Short report from fiscal memory for the period.
     *
     * @param password report password.
     * @param firstDate     begin date (DD/MM/YY).
     * @param lastDate       end date (DD/MM/YY).
     */
    public static void periodicReportShort(Long password, LocalDate firstDate, LocalDate lastDate) {
        List<Byte> param = new ArrayList<Byte>();

        for (byte b : getBytes(password, 2))
            param.add(b);

        param.add(convertToBCD(firstDate.getDayOfMonth()));
        param.add(convertToBCD(firstDate.getMonthValue()));
        param.add(convertToBCD(firstDate.getYear() % 100));
        param.add(convertToBCD(lastDate.getDayOfMonth()));
        param.add(convertToBCD(lastDate.getMonthValue()));
        param.add(convertToBCD(lastDate.getYear() % 100));

        ResponseFromPrinter.decode(sendMessageBoss(26, param));

    }

    /**
     * Code 17
     * <p>
     * Short report from fiscal memory for the period.
     *
     * @param password report password.
     * @param firstDate     begin date (DD/MM/YY).
     * @param lastDate       end date (DD/MM/YY).
     */
    public static void periodicReport(Long password, LocalDate firstDate, LocalDate lastDate) {
        List<Byte> param = new ArrayList<Byte>();

        for (byte b : getBytes(password, 2))
            param.add(b);

        param.add(convertToBCD(firstDate.getDayOfMonth()));
        param.add(convertToBCD(firstDate.getMonthValue()));
        param.add(convertToBCD(firstDate.getYear() % 100));
        param.add(convertToBCD(lastDate.getDayOfMonth()));
        param.add(convertToBCD(lastDate.getMonthValue()));
        param.add(convertToBCD(lastDate.getYear() % 100));
        ResponseFromPrinter.decode(sendMessageBoss(17, param));
    }

    /**
     * Code 32
     * <p>
     * Tax number and software version printing.
     */
    public static void printVer() {
        ResponseFromPrinter.decode(sendMessageBoss(32, null));

    }




    /**
     * Code 33
     * <p>
     * Command receives the amount of cash in a strongbox.
     *
     * @return the amount of cash.

     */
    public static void getBox() {
        ResponseFromPrinter.decode(sendMessageBoss(33, null));
        VariablesClass.setCashBoxSUmm(Commands.getNumber(ArrayUtils.subarray(ResponseFromPrinter.getNotListArray(), 5, 10)) * Math.pow(10, -2));
        tv_a.setText(String.valueOf(VariablesClass.getCashBoxSUmm()));
    }


    /**
     * Code 46
     * <p>
     * Enable/Disable paper cut.
     */
    public static void changeCutter() {
        ResponseFromPrinter.decode(sendMessageBoss(46, null));
    }



    public static void readDump(String memoryAdress, int memoryPage, int lenght) {
        List<Byte> parameters = new ArrayList();
        String memoryParce = memoryAdress;


        byte[] a = BaseEncoding.base16().decode(memoryParce.substring(2, 4));
        byte[] b = BaseEncoding.base16().decode(memoryParce.substring(0, 2));

        parameters.add(a[0]);
        parameters.add(b[0]);


        parameters.add((byte) Integer.parseInt(String.valueOf(memoryPage), 10));
        parameters.add((byte) Integer.parseInt(String.valueOf(lenght), 10));


        ResponseFromPrinter.decode(sendMessageBoss(28, parameters));

    }

    public List<Byte> sendMessage (PrinterCommand command){

        try {
            byte[] packet = this.PACKET.PacketBuilder((byte) COMMAND_COUNTER, command);
            buffer = ByteBuffer.allocate(1024);

            System.out.println("Sending this: " + Arrays.toString(packet));

            InputStream in = bl.getBluetoothSocket().getInputStream();
            OutputStream out = bl.getBluetoothSocket().getOutputStream();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(in);

            out.write(packet);


            while (true) {
                int b = bufferedInputStream.read();

                buffer.put((byte) b);
                if (buffer.get(0) == (byte) 6) {
                    System.out.println("Confirmed");
                    buffer.position(0);
                }
                if (buffer.get(0) == (byte) 22) {
                    System.out.println("Processing....");
                    buffer.position(0);
                }
                if (buffer.position() > 10) {

                    if (buffer.get(buffer.position() - 4) == (byte) 16 && buffer.get(buffer.position() - 3) == (byte) 3 && buffer.get(buffer.position() - 5) != (byte) 16)

                    {
                        break;
                    }
                }
            }

            if (buffer.hasArray()) {
                System.out.println("Packet Transforming...");
                RECEIVED_PACKET = buffer.array();
            }

            System.out.println("Packer after Transforming: " + Arrays.toString(RECEIVED_PACKET));

            COMMAND_COUNTER++;


        } catch (IOException e) {
            e.printStackTrace();
        }

        return Arrays.asList(ArrayUtils.toObject(RECEIVED_PACKET));
    }


    public List<Byte> getFiscalResult() {
        return Arrays.asList(ArrayUtils.toObject(RECEIVED_PACKET));
    }

    public static List<Byte> createParametersArray(List<Byte> in) {
        List<Byte> out = new ArrayList<Byte>();

        for (Byte el : in) {
            out.add(el);
        }

        return out;
    }

    public static byte getCheckSum(List<Byte> buf) {
        int i, n;
        byte lobyte, cs;
        long sum, res;

        n = buf.size() - 3;
        sum = 0;
        cs = 0x00;
        lobyte = 0x00;

        for (i = 2; i < n; i++)
            sum += buf.get(i);

        do {
            res = sum + cs;
            cs++;
            lobyte = (byte) (res & 0xFF);
        }
        while (lobyte != 0x00);

        return (byte) (cs - 1);
    }

    public static byte[] getBytes(long v, int b) {
        byte[] writeBuffer = new byte[b];
        if (b == 6) {
            writeBuffer[5] = (byte) ((v >>> 40) & 0xFF);
            writeBuffer[4] = (byte) ((v >>> 32) & 0xFF);
            writeBuffer[3] = (byte) ((v >>> 24) & 0xFF);
            writeBuffer[2] = (byte) ((v >>> 16) & 0xFF);
            writeBuffer[1] = (byte) ((v >>> 8) & 0xFF);
            writeBuffer[0] = (byte) ((v >>> 0) & 0xFF);
        }
        if (b == 4) {
            writeBuffer[3] = (byte) ((v >>> 24) & 0xFF);
            writeBuffer[2] = (byte) ((v >>> 16) & 0xFF);
            writeBuffer[1] = (byte) ((v >>> 8) & 0xFF);
            writeBuffer[0] = (byte) ((v >>> 0) & 0xFF);
        }
        if (b == 3) {
            writeBuffer[2] = (byte) ((v >>> 16) & 0xFF);
            writeBuffer[1] = (byte) ((v >>> 8) & 0xFF);
            writeBuffer[0] = (byte) ((v >>> 0) & 0xFF);
        }
        if (b == 2) {
            writeBuffer[1] = (byte) ((v >>> 8) & 0xFF);
            writeBuffer[0] = (byte) ((v >>> 0) & 0xFF);
        }
        return writeBuffer;
    }

    private static String replaceLetter(String in) {
        return in.replaceAll("І", "I").replaceAll("і", "i");
    }

    public static long getNumber(byte b) {
        return getNumber(new byte[]{b});
    }

    static long getNumber(byte[] mas) {
        long number = (mas[0] & 0xFF);

        for (int i = 1; i < mas.length; i++)
            number += (mas[i] & 0xFF) * (long) (0x01 << (8 * i));

        return number;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static byte getStatusForTaxRate(int doseDecimal, boolean typeVat, boolean programTaxRate) {
        return (byte) Integer.parseInt("00" + ((programTaxRate) ? "1" : "0") + ((typeVat) ? "1" : "0") + String.format("%04d", Integer.valueOf(Integer.toBinaryString(doseDecimal))), 2);
    }

}