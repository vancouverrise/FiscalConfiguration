package market.ics.fiscalconfiguration.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import market.ics.fiscalconfiguration.Communication.Commands;
import market.ics.fiscalconfiguration.Driver.DataType.ItemLine;
import market.ics.fiscalconfiguration.Driver.DataType.PrinterString;
import market.ics.fiscalconfiguration.Driver.DataType.SaleItem;
import market.ics.fiscalconfiguration.Driver.DataType.SaleQuantity;
import market.ics.fiscalconfiguration.Driver.DataType.TaxGroup;
import market.ics.fiscalconfiguration.Driver.Request.OpenBoxCommand;
import market.ics.fiscalconfiguration.Driver.Request.Payment.Card;
import market.ics.fiscalconfiguration.Driver.Request.PaymentCommand;
import market.ics.fiscalconfiguration.Driver.Request.SaleItemCommand;
import market.ics.fiscalconfiguration.Driver.SerialResponse;
import market.ics.fiscalconfiguration.R;

public class fragment_sale extends Fragment {

    private EditText name, article, price, quantity;
    TextView summa, summa2;
    Commands bs = new Commands();
    String select;

    TaxGroup taxgroup;

    private double paymentForScreen;
    private int paymentForCommand;

    private final SerialResponse serialResponse = new SerialResponse();

    public fragment_sale() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, container, false);

        Spinner spinner = view.findViewById(R.id.spinner);
        name = view.findViewById(R.id.article_name);
        article = view.findViewById(R.id.article_barcode);
        price = view.findViewById(R.id.article_price);
        quantity = view.findViewById(R.id.article_quantity);
        summa = view.findViewById(R.id.tv_summ);


        final String[] str = new String[]{"A", "B", "C", "D", "E"};


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, str);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button button = view.findViewById(R.id.scanTovar);
        Button oplata = view.findViewById(R.id.oplata);
        Button cancel = view.findViewById(R.id.cansel_receipt);



        button.setOnClickListener(v -> {
        paymentForScreen += (Double.parseDouble(price.getText().toString()) * Integer.parseInt(quantity.getText().toString()));
        int lenght = String.valueOf(price.getText()).length();

        if (lenght ==4){
                price.setText(price.getText() + "0");
            }

        String y = String.valueOf(price.getText()).replace(".", "");
        paymentForCommand += Integer.parseInt(y);
            System.out.println("this payment " + paymentForCommand);


        summa.setText(getString(R.string.Summa) + paymentForScreen + " грн.");



            if (spinner.getSelectedItem().toString().equals("A")){
                taxgroup = TaxGroup.A;
            }
            else if(spinner.getSelectedItem().toString().equals("B")){
                taxgroup = TaxGroup.B;
            }
            bs.sendMessage(new SaleItemCommand
                    (new ItemLine
                            (new SaleQuantity
                                    (new BigDecimal(String.valueOf(quantity.getText()))), paymentForCommand, new SaleItem(
                                    new PrinterString(name.getText().toString()), new BigInteger(String.valueOf(article.getText())), taxgroup))));


        });



        oplata.setOnClickListener(v -> bs.sendMessage(new PaymentCommand(new Card(null), false, (long) paymentForCommand)));
        cancel.setOnClickListener(v -> bs.sendMessage(new OpenBoxCommand(200)));


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select = spinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return view;
    }
}


