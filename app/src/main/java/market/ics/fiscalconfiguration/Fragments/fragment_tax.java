package market.ics.fiscalconfiguration.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import market.ics.fiscalconfiguration.Communication.Commands;
import market.ics.fiscalconfiguration.R;

public class fragment_tax extends Fragment {

    private static int taxcount = 0;
    private Switch switch_use_sostav, switch_sbor_na_nds, switch_nds_na_sbor;
    private static TextView taxA, taxB, taxC, taxD, taxE, taxF;
    private Spinner taxSpinner;
    private EditText TAX, TAX_SBOR;

    private static int A_VAT_RATE, B_VAT_RATE, C_VAT_RATE, D_VAT_RATE, E_VAT_RATE;
    private static int A_TAX_RATE, B_TAX_RATE, C_TAX_RATE, D_TAX_RATE, E_TAX_RATE;

    private static boolean A_NDS_NA_SBOR = false;
    private static boolean A_SBOR_NA_NDS = false;

    private static boolean B_NDS_NA_SBOR = false;
    private static boolean B_SBOR_NA_NDS = false;

    private static boolean C_NDS_NA_SBOR = false;
    private static boolean C_SBOR_NA_NDS = false;

    private static boolean D_NDS_NA_SBOR = false;
    private static boolean D_SBOR_NA_NDS = false;

    private static boolean E_NDS_NA_SBOR = false;
    private static boolean E_SBOR_NA_NDS = false;


    public fragment_tax() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tax_layout, container, false);
        taxSpinner = view.findViewById(R.id.tax_spinner);
        String[] arraySpinner = new String[] {"Ставка А", "Ставка Б", "Ставка В", "Ставка Г", "Ставка Д", "Ставка Е"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        taxSpinner.setAdapter(adapter);

        taxA        = view.findViewById(R.id.TAX_A_TV);
        taxB        = view.findViewById(R.id.TAX_B_TV);
        taxC        = view.findViewById(R.id.TAX_C_TV);
        taxD        = view.findViewById(R.id.TAX_D_TV);
        taxE        = view.findViewById(R.id.TAX_E_TV);
        //taxF        = view.findViewById(R.id.TAX_F_TV);

        Button addtax = view.findViewById(R.id.add_tax_to_list);
        Button sendtax = view.findViewById(R.id.sendTaxToFP);

        switch_use_sostav  = view.findViewById(R.id.switch1);
        switch_sbor_na_nds = view.findViewById(R.id.SWITCH_SBOR_NDS);
        switch_nds_na_sbor = view.findViewById(R.id.SWITCH_NDS_SBOR);

        TAX         = view.findViewById(R.id.set_tax_a_NDS);
        TAX_SBOR    = view.findViewById(R.id.set_tax_a_SBOR);

        /**
         * ViseVersa switches
         */
        switch_sbor_na_nds.setOnClickListener(v -> switch_nds_na_sbor.setChecked(false));
        switch_nds_na_sbor.setOnClickListener(v -> switch_sbor_na_nds.setChecked(false));



        addtax.setOnClickListener(v -> {
        taxcount++;
        if (taxcount == 5){
            Toast.makeText(getContext(), "To much tax", Toast.LENGTH_SHORT).show();
        }
            switch (taxSpinner.getSelectedItem().toString()) {

                case "Ставка А":
                    Toast.makeText(getContext(), "Добавленно", Toast.LENGTH_SHORT).show();
                    if (!switch_use_sostav.isChecked()) {
                        taxA.setText("Ставка А: " + TAX.getText().toString() + "%");
                        A_VAT_RATE = Integer.parseInt(TAX.getText().toString());
                    }
                    if (switch_use_sostav.isChecked() && switch_sbor_na_nds.isChecked() && !switch_nds_na_sbor.isChecked()) {
                        taxA.setText("Ставка А: " + TAX.getText().toString() + "% " + TAX_SBOR.getText().toString() + "% " + "[НДС на сбор]");
                        A_VAT_RATE = Integer.parseInt(TAX.getText().toString());
                        A_TAX_RATE = Integer.parseInt(TAX_SBOR.getText().toString());
                        A_SBOR_NA_NDS = true;
                    }
                    if (switch_use_sostav.isChecked() && switch_nds_na_sbor.isChecked() && !switch_sbor_na_nds.isChecked()) {
                        taxA.setText("Ставка А: " + TAX.getText().toString() + "% " + TAX_SBOR.getText().toString() + "% " + "[Cбор на НДС]");
                        A_VAT_RATE = Integer.parseInt(TAX.getText().toString());
                        A_TAX_RATE = Integer.parseInt(TAX_SBOR.getText().toString());
                        A_NDS_NA_SBOR = true;
                    }
                    break;
                case "Ставка Б":
                    Toast.makeText(getContext(), "Добавленно", Toast.LENGTH_SHORT).show();
                    if (!switch_use_sostav.isChecked()) {
                        taxB.setText("Ставка Б: " + TAX.getText().toString() + "%");
                        B_VAT_RATE = Integer.parseInt(TAX.getText().toString());
                    }
                    if (switch_use_sostav.isChecked() && switch_sbor_na_nds.isChecked() && !switch_nds_na_sbor.isChecked()) {
                        taxB.setText("Ставка Б: " + TAX.getText().toString() + "% " + TAX_SBOR.getText().toString() + "% " + "[НДС на сбор]");
                        B_VAT_RATE = Integer.parseInt(TAX.getText().toString());
                        B_TAX_RATE = Integer.parseInt(TAX_SBOR.getText().toString());
                        B_SBOR_NA_NDS = true;
                    }
                    if (switch_use_sostav.isChecked() && switch_nds_na_sbor.isChecked() && !switch_sbor_na_nds.isChecked()) {
                        taxB.setText("Ставка Б: " + TAX.getText().toString() + "% " + TAX_SBOR.getText().toString() + "% " + "[Cбор на НДС]");
                        B_VAT_RATE = Integer.parseInt(TAX.getText().toString());
                        B_TAX_RATE = Integer.parseInt(TAX_SBOR.getText().toString());
                        B_NDS_NA_SBOR = true;
                    }
                    break;
                case "Ставка В":
                    Toast.makeText(getContext(), "Добавленно", Toast.LENGTH_SHORT).show();
                    if (!switch_use_sostav.isChecked()) {
                        taxC.setText("Ставка В: " + TAX.getText().toString() + "%");
                        C_VAT_RATE = Integer.parseInt(TAX.getText().toString());
                    }
                    if (switch_use_sostav.isChecked() && switch_sbor_na_nds.isChecked() && !switch_nds_na_sbor.isChecked()) {
                        taxC.setText("Ставка В: " + TAX.getText().toString() + "% " + TAX_SBOR.getText().toString() + "% " + "[НДС на сбор]");
                        C_VAT_RATE = Integer.parseInt(TAX.getText().toString());
                        C_TAX_RATE = Integer.parseInt(TAX_SBOR.getText().toString());
                        C_SBOR_NA_NDS = true;
                    }
                    if (switch_use_sostav.isChecked() && switch_nds_na_sbor.isChecked() && !switch_sbor_na_nds.isChecked()) {
                        taxC.setText("Ставка В: " + TAX.getText().toString() + "% " + TAX_SBOR.getText().toString() + "% " + "[Cбор на НДС]");
                        C_VAT_RATE = Integer.parseInt(TAX.getText().toString());
                        C_TAX_RATE = Integer.parseInt(TAX_SBOR.getText().toString());
                        C_NDS_NA_SBOR = true;
                    }

                    break;
                case "Ставка Г":
                    Toast.makeText(getContext(), "Добавленно", Toast.LENGTH_SHORT).show();
                    if (!switch_use_sostav.isChecked()) {
                        taxD.setText("Ставка Г: " + TAX.getText().toString() + "%");
                        D_VAT_RATE = Integer.parseInt(TAX.getText().toString());
                    }
                    if (switch_use_sostav.isChecked() && switch_sbor_na_nds.isChecked() && !switch_nds_na_sbor.isChecked()) {
                        taxD.setText("Ставка Г: " + TAX.getText().toString() + "% " + TAX_SBOR.getText().toString() + "% " + "[НДС на сбор]");
                        D_VAT_RATE = Integer.parseInt(TAX.getText().toString());
                        D_TAX_RATE = Integer.parseInt(TAX_SBOR.getText().toString());
                        D_SBOR_NA_NDS = true;
                    }
                    if (switch_use_sostav.isChecked() && switch_nds_na_sbor.isChecked() && !switch_sbor_na_nds.isChecked()) {
                        taxD.setText("Ставка Г: " + TAX.getText().toString() + "% " + TAX_SBOR.getText().toString() + "% " + "[Cбор на НДС]");
                        D_VAT_RATE = Integer.parseInt(TAX.getText().toString());
                        D_TAX_RATE = Integer.parseInt(TAX_SBOR.getText().toString());
                        D_NDS_NA_SBOR = true;
                    }
                    break;
                case "Ставка Д":
                    Toast.makeText(getContext(), "Добавленно", Toast.LENGTH_SHORT).show();
                    if (!switch_use_sostav.isChecked()) {
                        taxE.setText("Ставка Д: " + TAX.getText().toString() + "%");
                        E_VAT_RATE = Integer.parseInt(TAX.getText().toString());
                    }
                    if (switch_use_sostav.isChecked() && switch_sbor_na_nds.isChecked() && !switch_nds_na_sbor.isChecked()) {
                        taxE.setText("Ставка Д: " + TAX.getText().toString() + "% " + TAX_SBOR.getText().toString() + "% " + "[НДС на сбор]");
                        E_VAT_RATE = Integer.parseInt(TAX.getText().toString());
                        E_TAX_RATE = Integer.parseInt(TAX_SBOR.getText().toString());
                        E_SBOR_NA_NDS = true;
                    }
                    if (switch_use_sostav.isChecked() && switch_nds_na_sbor.isChecked() && !switch_sbor_na_nds.isChecked()) {
                        taxE.setText("Ставка Д: " + TAX.getText().toString() + "% " + TAX_SBOR.getText().toString() + "% " + "[Cбор на НДС]");
                        E_VAT_RATE = Integer.parseInt(TAX.getText().toString());
                        E_TAX_RATE = Integer.parseInt(TAX_SBOR.getText().toString());
                        E_NDS_NA_SBOR = true;
                    }
                    break;

            }
        });

        sendtax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTax();
            }
        });
       return view;
    }

    public static void setTax(){
        List<Integer> vatRate = new ArrayList<>();
        List<Integer> taxRate = new ArrayList<>();

        switch (taxcount){
            case 1:
                vatRate.add(A_VAT_RATE);
                if (A_NDS_NA_SBOR){
                    A_TAX_RATE |= (1 << 14);
                    System.out.println("Send this: " + A_TAX_RATE);
                    taxRate.add(A_TAX_RATE);
                }
                if (A_SBOR_NA_NDS){
                    A_TAX_RATE |= (1 << 15);
                    System.out.println("Send this: " + A_TAX_RATE);
                    taxRate.add(A_TAX_RATE);
                }
                break;
            case 2:
                vatRate.add(A_VAT_RATE);
                if (A_NDS_NA_SBOR){
                    A_TAX_RATE |= (1 << 14);
                    System.out.println("Send this: " + A_TAX_RATE);
                    taxRate.add(A_TAX_RATE);
                }
                if (A_SBOR_NA_NDS){
                    A_TAX_RATE |= (1 << 15);
                    System.out.println("Send this: " + A_TAX_RATE);
                    taxRate.add(A_TAX_RATE);
                }
                vatRate.add(B_VAT_RATE);
                if (B_NDS_NA_SBOR){
                    B_TAX_RATE |= (1 << 14);
                    System.out.println("Send this: " + B_TAX_RATE);
                    taxRate.add(B_TAX_RATE);
                }
                if (B_SBOR_NA_NDS){
                    B_TAX_RATE |= (1 << 15);
                    System.out.println("Send this: " + B_TAX_RATE);
                    taxRate.add(B_TAX_RATE);
                }
                break;
            case 3:
                vatRate.add(A_VAT_RATE);
                if (A_NDS_NA_SBOR){
                    A_TAX_RATE |= (1 << 14);
                    System.out.println("Send this: " + A_TAX_RATE);
                    taxRate.add(A_TAX_RATE);
                }
                if (A_SBOR_NA_NDS){
                    A_TAX_RATE |= (1 << 15);
                    System.out.println("Send this: " + A_TAX_RATE);
                    taxRate.add(A_TAX_RATE);
                }
                vatRate.add(B_VAT_RATE);
                if (B_NDS_NA_SBOR){
                    B_TAX_RATE |= (1 << 14);
                    System.out.println("Send this: " + B_TAX_RATE);
                    taxRate.add(B_TAX_RATE);
                }
                if (B_SBOR_NA_NDS){
                    B_TAX_RATE |= (1 << 15);
                    System.out.println("Send this: " + B_TAX_RATE);
                    taxRate.add(B_TAX_RATE);
                }
                vatRate.add(C_VAT_RATE);
                if (C_NDS_NA_SBOR){
                    C_TAX_RATE |= (1 << 14);
                    System.out.println("Send this: " + C_TAX_RATE);
                    taxRate.add(C_TAX_RATE);
                }
                if (C_SBOR_NA_NDS){
                    C_TAX_RATE |= (1 << 15);
                    System.out.println("Send this: " + C_TAX_RATE);
                    taxRate.add(C_TAX_RATE);
                }
                break;
            case 4:
                vatRate.add(A_VAT_RATE);
                if (A_NDS_NA_SBOR){
                    A_TAX_RATE |= (1 << 14);
                    System.out.println("Send this: " + A_TAX_RATE);
                    taxRate.add(A_TAX_RATE);
                }
                if (A_SBOR_NA_NDS){
                    A_TAX_RATE |= (1 << 15);
                    System.out.println("Send this: " + A_TAX_RATE);
                    taxRate.add(A_TAX_RATE);
                }
                vatRate.add(B_VAT_RATE);
                if (B_NDS_NA_SBOR){
                    B_TAX_RATE |= (1 << 14);
                    System.out.println("Send this: " + B_TAX_RATE);
                    taxRate.add(B_TAX_RATE);
                }
                if (B_SBOR_NA_NDS){
                    B_TAX_RATE |= (1 << 15);
                    System.out.println("Send this: " + B_TAX_RATE);
                    taxRate.add(B_TAX_RATE);
                }
                vatRate.add(C_VAT_RATE);
                if (C_NDS_NA_SBOR){
                    C_TAX_RATE |= (1 << 14);
                    System.out.println("Send this: " + C_TAX_RATE);
                    taxRate.add(C_TAX_RATE);
                }
                if (C_SBOR_NA_NDS){
                    C_TAX_RATE |= (1 << 15);
                    System.out.println("Send this: " + C_TAX_RATE);
                    taxRate.add(C_TAX_RATE);
                }
                vatRate.add(D_VAT_RATE);
                if (D_NDS_NA_SBOR){
                    D_TAX_RATE |= (1 << 14);
                    System.out.println("Send this: " + D_TAX_RATE);
                    taxRate.add(D_TAX_RATE);
                }
                if (D_SBOR_NA_NDS){
                    D_TAX_RATE |= (1 << 15);
                    System.out.println("Send this: " + D_TAX_RATE);
                    taxRate.add(D_TAX_RATE);
                }
                break;
            case 5:
                vatRate.add(A_VAT_RATE);
                if (A_NDS_NA_SBOR){
                    A_TAX_RATE |= (1 << 14);
                    System.out.println("Send this: " + A_TAX_RATE);
                    taxRate.add(A_TAX_RATE);
                }
                if (A_SBOR_NA_NDS){
                    A_TAX_RATE |= (1 << 15);
                    System.out.println("Send this: " + A_TAX_RATE);
                    taxRate.add(A_TAX_RATE);
                }
                vatRate.add(B_VAT_RATE);
                if (B_NDS_NA_SBOR){
                    B_TAX_RATE |= (1 << 14);
                    System.out.println("Send this: " + B_TAX_RATE);
                    taxRate.add(B_TAX_RATE);
                }
                if (B_SBOR_NA_NDS){
                    B_TAX_RATE |= (1 << 15);
                    System.out.println("Send this: " + B_TAX_RATE);
                    taxRate.add(B_TAX_RATE);
                }
                vatRate.add(C_VAT_RATE);
                if (C_NDS_NA_SBOR){
                    C_TAX_RATE |= (1 << 14);
                    System.out.println("Send this: " + C_TAX_RATE);
                    taxRate.add(C_TAX_RATE);
                }
                if (C_SBOR_NA_NDS){
                    C_TAX_RATE |= (1 << 15);
                    System.out.println("Send this: " + C_TAX_RATE);
                    taxRate.add(C_TAX_RATE);
                }
                vatRate.add(D_VAT_RATE);
                if (D_NDS_NA_SBOR){
                    D_TAX_RATE |= (1 << 14);
                    System.out.println("Send this: " + D_TAX_RATE);
                    taxRate.add(D_TAX_RATE);
                }
                if (D_SBOR_NA_NDS){
                    D_TAX_RATE |= (1 << 15);
                    System.out.println("Send this: " + D_TAX_RATE);
                    taxRate.add(D_TAX_RATE);
                }
                vatRate.add(E_VAT_RATE);
                if (E_NDS_NA_SBOR){
                    E_TAX_RATE |= (1 << 14);
                    System.out.println("Send this: " + E_TAX_RATE);
                    taxRate.add(E_TAX_RATE);
                }
                if (E_SBOR_NA_NDS){
                    E_TAX_RATE |= (1 << 15);
                    System.out.println("Send this: " + E_TAX_RATE);
                    taxRate.add(E_TAX_RATE);
                }
                break;
        }

        /**
         * Send packet
         */

        Commands.setTaxRate(0L, taxcount, vatRate, 3, false, true, taxRate, 1000);

        A_VAT_RATE = 0;
        A_TAX_RATE = 0;
        A_NDS_NA_SBOR = false;
        A_SBOR_NA_NDS = false;

        B_VAT_RATE = 0;
        B_TAX_RATE = 0;
        B_NDS_NA_SBOR = false;
        B_SBOR_NA_NDS = false;

        C_VAT_RATE = 0;
        C_TAX_RATE = 0;
        C_NDS_NA_SBOR = false;
        C_SBOR_NA_NDS = false;

        D_VAT_RATE = 0;
        D_TAX_RATE = 0;
        D_NDS_NA_SBOR = false;
        D_SBOR_NA_NDS = false;

        E_VAT_RATE = 0;
        E_TAX_RATE = 0;
        E_NDS_NA_SBOR = false;
        E_SBOR_NA_NDS = false;

        taxcount = 0;

        taxA.setText("Ставка А:");
        taxB.setText("Ставка Б:");
        taxC.setText("Ставка В:");
        taxD.setText("Ставка Г:");
        taxE.setText("Ставка Д:");

    }

    public static int flipBit(int value, int bitIndex) {
        return value ^ (1 << bitIndex-1); // put your implementation here
    }
}


