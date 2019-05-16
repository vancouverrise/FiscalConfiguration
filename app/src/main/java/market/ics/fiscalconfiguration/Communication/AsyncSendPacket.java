package market.ics.fiscalconfiguration.Communication;

import android.app.AlertDialog;
import android.os.AsyncTask;

import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

import market.ics.fiscalconfiguration.Driver.ResponseFromPrinter;
import market.ics.fiscalconfiguration.Driver.TaxInfo;
import market.ics.fiscalconfiguration.Driver.VatTransform;
import market.ics.fiscalconfiguration.Fragments.VariablesClass;
import market.ics.fiscalconfiguration.Fragments.fragment_info;
import market.ics.fiscalconfiguration.Fragments.fragment_operations;

import static market.ics.fiscalconfiguration.Fragments.fragment_info.cashbox;
import static market.ics.fiscalconfiguration.Fragments.fragment_info.tv_a;
import static market.ics.fiscalconfiguration.Fragments.fragment_info.tv_b;
import static market.ics.fiscalconfiguration.Fragments.fragment_info.tv_c;
import static market.ics.fiscalconfiguration.Fragments.fragment_info.tv_d;
import static market.ics.fiscalconfiguration.Fragments.fragment_info.tv_e;

public class AsyncSendPacket extends AsyncTask<Void, Void, Void> {

    private int commandNumber;
    private List<Byte> parameters;
    private AlertDialog.Builder alertDialog;
    private fragment_operations fragment_operations = new fragment_operations();


    public AsyncSendPacket(int COMMAND_NUMBER, List<Byte> PARAMETERS){
        this.commandNumber = COMMAND_NUMBER;
        this.parameters = PARAMETERS;


    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    public AsyncSendPacket(int COMMAND_NUMBER, List<Byte> PARAMETERS, long PASSWORD){
        this.commandNumber = COMMAND_NUMBER;
        this.parameters = PARAMETERS;

        for (byte b : Commands.getBytes(PASSWORD, 2)) {
            parameters.add(0, b);
        }
    }


    @Override
    protected Void doInBackground(Void... voids) {
        ResponseFromPrinter.decode(Commands.sendMessageBoss(commandNumber, parameters));
        return null;
    }




    @Override
    protected void onPostExecute(Void aVoid) {
        /*new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fragment_info.setSettax(true);

        }).start();*/



        switch (commandNumber){
            case 0:
                VariablesClass.Initialize();
                fragment_info.setManufacturer("Модель: " + VariablesClass.getManufacturer());
                fragment_info.setCutterMode(String.format("Обрезчик: %s", VariablesClass.getIscutter()));
                fragment_info.setManufactureDate(String.format("Изготовлен: %s", VariablesClass.getManufacturerDate()));
                fragment_info.setSerialnumber(String.format("Серийный номер: %s", VariablesClass.getSerialNumber()));
                fragment_info.setRomVersion(String.format("Прошивка: %s", VariablesClass.getRomVersion()));
                fragment_info.setIsFiscal(String.format("Фискализация: %s", VariablesClass.getIsfiscal()));
                fragment_info.setCashbox(String.format("Денежный ящик: %s", VariablesClass.getCashboxResponse()));
                cashbox.setText(String.format("Денежный ящик: %s", VariablesClass.getCashboxResponse()));
                VariablesClass.setPacket(null);
                break;


            case 44: VatTransform.VatTransform(VariablesClass.getPacket());
                switch (TaxInfo.getTotalTax()) {
                    case 1:
                        tv_a.setText("Ставка A: " + String.format("%.2f", TaxInfo.getTaxRates()[0]) + " %");
                        break;
                    case 2:
                        tv_a.setText("Ставка A: " + String.format("%.2f", TaxInfo.getTaxRates()[0]) + " %");
                        tv_b.setText("Ставка Б: " + String.format("%.2f", TaxInfo.getTaxRates()[1]) + " %");
                        break;
                    case 3:
                        tv_a.setText("Ставка A: " + String.format("%.2f", TaxInfo.getTaxRates()[0]) + " %");
                        tv_b.setText("Ставка Б: " + String.format("%.2f", TaxInfo.getTaxRates()[1]) + " %");
                        tv_c.setText("Ставка В: " + String.format("%.2f", TaxInfo.getTaxRates()[2]) + " %");
                        break;
                    case 4:
                        tv_a.setText("Ставка A: " + String.format("%.2f", TaxInfo.getTaxRates()[0]) + " %");
                        tv_b.setText("Ставка Б: " + String.format("%.2f", TaxInfo.getTaxRates()[1]) + " %");
                        tv_c.setText("Ставка В: " + String.format("%.2f", TaxInfo.getTaxRates()[2]) + " %");
                        tv_d.setText("Ставка Г: " + String.format("%.2f", TaxInfo.getTaxRates()[3]) + " %");
                        break;
                    case 5:
                        tv_a.setText("Ставка A: " + String.format("%.2f", TaxInfo.getTaxRates()[0]) + " %");
                        tv_b.setText("Ставка Б: " + String.format("%.2f", TaxInfo.getTaxRates()[1]) + " %");
                        tv_c.setText("Ставка В: " + String.format("%.2f", TaxInfo.getTaxRates()[2]) + " %");
                        tv_d.setText("Ставка Г: " + String.format("%.2f", TaxInfo.getTaxRates()[3]) + " %");
                        tv_e.setText("Ставка Д: " + String.format("%.2f", TaxInfo.getTaxRates()[4]) + " %");
                        break;
                }
            VariablesClass.setPacket(null);
            break;
            case 53:
                VariablesClass.setmodem();
            case 9:


                break;
            case 33:
                VariablesClass.setCashBoxSUmm(Commands.getNumber(ArrayUtils.subarray(ResponseFromPrinter.getNotListArray(), 5, 10)) * Math.pow(10, -2));
                tv_a.setText(String.valueOf(VariablesClass.getCashBoxSUmm()));
                alertDialog.setTitle("The Process");
                alertDialog.show();



        }
        super.onPostExecute(aVoid);
    }
}
