package market.ics.fiscalconfiguration.Driver.Response;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import market.ics.fiscalconfiguration.Driver.DataType.BCDByte;
import market.ics.fiscalconfiguration.Driver.DataType.BCDYear;
import market.ics.fiscalconfiguration.Driver.DataType.ByteBits;
import market.ics.fiscalconfiguration.Driver.DataType.BytesNumber;

public class TimeRepsone {

    List<Byte> VatResponse = new ArrayList<Byte>();
    static List<Byte> VatRates = new ArrayList<Byte>();

    static int a;
    static int b;
    public static float zz;
    public static float bb;




    public String getTime(List<Byte> data) {
        byte [] time = new byte[3];
        time[0] = data.get(0);
        time[1] = data.get(1);
        time[2] = data.get(2);

        String result = addBracks(byteArrayToString(time)).toString();
        return result;
    }

    public String byteArrayToString(byte[] in) {
        char out[] = new char[in.length * 2];
        for (int i = 0; i < in.length; i++) {
            out[i * 2] = "0123456789ABCDEF".charAt((in[i] >> 4) & 15);
            out[i * 2 + 1] = "0123456789ABCDEF".charAt(in[i] & 15);
        }
        return new String(out);
    }

    public StringBuffer addBracks(String time){
        StringBuffer sb = new StringBuffer(time);
        sb.insert(2, ":");
        sb.insert(5, ":");
        return sb;
    }

    public void getVatResponseDate(List<Byte> input) {
        this.VatResponse = input;
        BCDByte day = new BCDByte(this.VatResponse.get(1));
        BCDByte month = new BCDByte(this.VatResponse.get(2));
        BCDYear year = new BCDYear(this.VatResponse.get(3));
        System.out.println(LocalDate.of(year.toInteger(), month.toInteger(), day.toInteger()));
    }

    public void getVatRates(List<Byte> input){
        VatRates = input;
        List<Byte> temp = new ArrayList<>();
        temp.add(0, VatRates.get(4));
        temp.add(1, VatRates.get(5));
        BytesNumber a = new BytesNumber(temp);



    }

    public static void checkVatsNormalno(List <Byte> input){
        VatRates = input;
        if (VatRates.get(0) == 2){
            List<Byte> temp = new ArrayList<>();
            temp.add(0, VatRates.get(4));
            temp.add(1, VatRates.get(5));


            a = new BytesNumber(temp).toInteger() ;
            temp.clear();
            temp.add(0, VatRates.get(6));
            temp.add(1, VatRates.get(7));
            b = new BytesNumber(temp).toInteger() ;
            System.out.println("Final; logica " + a + " " + b);
            temp.clear();
            temp.add(0,VatRates.get(8));
            ByteBits bb = new ByteBits(temp.get(0));
            System.out.println("Byte" + bb.getSixth());
            System.out.println("Byte" + bb.getFirst());
            System.out.println("Byte" + bb.getSecond());
            System.out.println("Byte" + bb.getThird());
            System.out.println("Byte" + bb.getForth());
            System.out.println("Byte" + bb.getFifth());
        }
         zz = a / 100;
        bb = b / 100;
        System.out.println(zz + " " + bb);
        System.out.println();

    }

    public static String getZz(){
        return String.valueOf(zz);
    }

    public static String getBb() {
        return String.valueOf(bb);
    }
}
