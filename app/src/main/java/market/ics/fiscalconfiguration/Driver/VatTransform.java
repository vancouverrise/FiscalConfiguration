package market.ics.fiscalconfiguration.Driver;

import com.google.common.primitives.Bytes;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class VatTransform {

    List<Byte> temp = null;

    public static void VatTransform (List<Byte> vats){
        TaxInfo taxInfo = new TaxInfo();

        /*vats.add(0, (byte) -64);
        vats.add(0, (byte) 5);
        vats.add(0, (byte) 0);
        vats.add(0, (byte) 0);
*/
        byte[] readData = Bytes.toArray(vats);

        if (readData.length >= (1 + 3 + 0 + 1 + 0)) {
        byte totTax = (byte) readData[0];
            System.out.println("Total tax: " + totTax);

        Date date = new Date(
        2000 + Integer.parseInt(String.format("%02x", readData[3])) - 1900,
        Integer.parseInt(String.format("%02x", readData[2])) - 1,
        Integer.parseInt(String.format("%02x", readData[1])));

        byte[] tax = new byte[2 * totTax];
        System.out.println("TAX"  + Arrays.toString(tax));
        System.arraycopy(readData, 4, tax, 0, tax.length);
        double[] taxRates = new double[totTax];
        System.out.println(Arrays.toString(taxRates));
        byte[] currRate = new byte[2];
        for (int i = 0; i < tax.length; i += 2) {
        currRate[0] = tax[i];

        currRate[1] = tax[i + 1];

        taxRates[i / 2] = getNumber(currRate) / 100.00;
        System.out.println(Arrays.toString(taxRates));
        }

        byte status = (byte) readData[4 + 2 * totTax];
        byte decimals = (byte) (status & 0x0F);
        byte taxType = (byte) (status & 0x10);
        byte useGetTax = (byte) (status & 0x20);

        if (useGetTax == 0) {
        taxInfo.setTotalTax(totTax);
        taxInfo.setDate(date);
        taxInfo.setTaxRates(taxRates);
        taxInfo.setDecimals(decimals);
        taxInfo.setTaxType(taxType);
        taxInfo.setRateOfTurns(false);
        } else {

        System.arraycopy(readData, 5 + 2 * totTax, tax, 0, tax.length);
        double[] taxRatesE = new double[totTax];

        for (int i = 0; i < tax.length; i += 2) {
            currRate[0] = tax[i];
            currRate[1] = tax[i + 1];
            taxRatesE[i / 2] = getNumber(currRate) / 100.00;
        }

        taxInfo.setTotalTax(totTax);
        taxInfo.setDate(date);
        taxInfo.setTaxRates(taxRates);
        taxInfo.setDecimals(decimals);
        taxInfo.setTaxType(taxType);
        taxInfo.setRateOfTurns(false);
        taxInfo.setTaxRatesE(taxRatesE);
        }
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

    public static List<Byte> setTaxRate(Long password, int countTaxRate, List<Integer> taxRate, int doseDecimal, boolean typeVAT,
                                        boolean programTaxRate, List<Integer> rate, Integer eRate, boolean isSinglePOS) {
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

        return param;

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

    public static byte getStatusForTaxRate(int doseDecimal, boolean typeVat, boolean programTaxRate) {
        return (byte) Integer.parseInt("00" +
                ((programTaxRate) ? "1" : "0") +
                ((typeVat) ? "1" : "0") +
                String.format("%04d", Integer.valueOf(Integer.toBinaryString(doseDecimal))), 2);
    }


}