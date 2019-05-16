package market.ics.fiscalconfiguration.Driver.Response;

import java.util.List;

public final class GetModemParametrs {

    private final List<Byte> bytes;


    public GetModemParametrs(List<Byte> bytes) {
        this.bytes = bytes;
    }

    public String getIpAdress(){
        return    byteToDecimal(23) + "."
                + byteToDecimal(24) + "."
                + byteToDecimal(25) + "."
                + byteToDecimal(26);
    }

    public String getMask(){
        return    byteToDecimal(15) + "."
                + byteToDecimal(16) + "."
                + byteToDecimal(17) + "."
                + byteToDecimal(18);
    }

    public String getSubnet(){
        return    byteToDecimal(19) + "."
                + byteToDecimal(20) + "."
                + byteToDecimal(21) + "."
                + byteToDecimal(22);
    }

    public String getIpEq(){
        return    byteToDecimal(19) + "."
                + byteToDecimal(20) + "."
                + byteToDecimal(21) + "."
                + byteToDecimal(22);
    }

    private Integer byteToDecimal(int cellNumber){
        return Integer.valueOf(byteArrayToString((new Byte[]{bytes.get(cellNumber)})), 16);
    }

    private String byteArrayToString(Byte[] in) {
        char out[] = new char[in.length * 2];
        for (int i = 0; i < in.length; i++) {
            out[i * 2] = "0123456789ABCDEF".charAt((in[i] >> 4) & 15);
            out[i * 2 + 1] = "0123456789ABCDEF".charAt(in[i] & 15);
        }
        return new String(out);
    }
}