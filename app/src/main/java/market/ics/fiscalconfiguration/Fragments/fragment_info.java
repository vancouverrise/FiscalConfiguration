package market.ics.fiscalconfiguration.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import market.ics.fiscalconfiguration.Communication.AsyncSendPacket;
import market.ics.fiscalconfiguration.R;

public class fragment_info extends Fragment {


    public static TextView manufacturer, serialnumber, isFiscal, manufactureDate, romVersion, cutterMode, cashbox, modemIp, modemMask, modemGateway, tv_a, tv_b, tv_c, tv_d, tv_e;
    public static MaterialButton buttonget, settax, gettax;
    private FloatingActionButton sendMailFab, getInfoFab;

    public fragment_info() {}



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        buttonget = view.findViewById(R.id.get_info_button1);

        gettax = view.findViewById(R.id.getTaxButton);

        /**
         *  Status Textviews
         */
        manufacturer = view.findViewById(R.id.tv_model);
        serialnumber = view.findViewById(R.id.tv_serial);
        isFiscal = view.findViewById(R.id.tv_fiscal);
        manufactureDate = view.findViewById(R.id.tv_manufacturing_date);
        romVersion = view.findViewById(R.id.tv_rom);
        cutterMode = view.findViewById(R.id.tv_cutter);
        cashbox = view.findViewById(R.id.tv_cashbox);

        /**
         *  Modem Textviews
         */
        modemIp = view.findViewById(R.id.tv_ip_modem);
        modemMask = view.findViewById(R.id.tv_mask_modem);
        modemGateway = view.findViewById(R.id.tv_gateway_modem);

        /**
         *  Tax Textviews
         */
        tv_a = view.findViewById(R.id.tv_tax_a);
        tv_b = view.findViewById(R.id.tv_tax_b);
        tv_c = view.findViewById(R.id.tv_tax_c);
        tv_d = view.findViewById(R.id.tv_tax_d);
        tv_e = view.findViewById(R.id.tv_tax_e);

        gettax.setOnClickListener(v -> {
            sentHelpSms();
        });


        buttonget.setOnClickListener(v -> {
            new AsyncSendPacket(0, null).execute();
          new AsyncSendPacket(44, null).execute();
        });

        buttonget.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               sentHelpSms();
            return false;
            }
        });


        return view;

    }
    private void getSome(){


        modemIp.setText(String.format("IP: %s", VariablesClass.getModemIp()));
        modemGateway.setText(String.format("Gateway: %s", VariablesClass.getModemGateway()));
        modemMask.setText(String.format("Mask: %s", VariablesClass.getModemMask()));


    }

    private void SendHelpMail(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"Y.Kulinchenko@ics-market.com.ua"});
        i.putExtra(Intent.EXTRA_SUBJECT, VariablesClass.getManufacturer() + " " + VariablesClass.getSerialNumber());
        i.putExtra(Intent.EXTRA_TEXT   , "Фискализация: " + VariablesClass.getIsfiscal() + "\n" +
                "Прошивка: " + VariablesClass.getRomVersion());
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There are no email clients installed..", Toast.LENGTH_SHORT).show();
        }
    }



    private void sentHelpSms(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setPackage("com.viber.voip");
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, (
                        manufacturer.       getText().toString() +
                "\n" +  serialnumber.       getText().toString() +
                "\n" +  isFiscal.           getText().toString() +
                "\n" +  manufactureDate.    getText().toString() +
                "\n" +  romVersion.         getText().toString() +
                                "\n" + cutterMode .getText().toString() +
                                 "\n" + cashbox.getText().toString()));
        startActivity(i);


    }

    public static void setManufacturer(String text) {
        fragment_info.manufacturer.setText(text);
    }

    public static void setSerialnumber(String text) {
        fragment_info.serialnumber.setText(text);
    }

    public static  void setIsFiscal(String text) {
        fragment_info.isFiscal.setText(text);
    }

    public static void setManufactureDate(String text) {
       fragment_info.manufactureDate.setText(text);
    }

    public static void setRomVersion(String text) {
       fragment_info.romVersion.setText(text);
    }

    public static void setCutterMode(String text) {
       fragment_info.cutterMode.setText(text);
    }

    public static void setCashbox(String text) {
       fragment_info.cashbox.setText(text);
    }

    public static void setSettax(boolean OnOff) {
        fragment_info.settax.setClickable(OnOff);
    }




}

