package market.ics.fiscalconfiguration.Driver.Response;

import java.util.List;

public class DateResponse {

    public String getDate(List<Byte> data) {
        byte [] time = new byte[3];
        time[0] = data.get(0);
        time[1] = data.get(1);
        time[2] = data.get(2);
        String result = byteArrayToString(time);

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


}
