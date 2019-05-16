package market.ics.fiscalconfiguration.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import market.ics.fiscalconfiguration.Communication.Commands;
import market.ics.fiscalconfiguration.R;

public class fragment_dump extends Fragment {

    private TextInputLayout dump_adress, dump_page, dump_quantity;
    private TextView dump_result;
    private TextInputEditText adress, quantity;
    public fragment_dump() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dump, container, false);
        // TextEdit
        dump_adress     = view.findViewById(R.id.dump_memory);
        dump_page       = view.findViewById(R.id.dump_adress);
        dump_quantity   = view.findViewById(R.id.dump_quantity);
        // Button
        Button dump_get = view.findViewById(R.id.dump_button);
        Button dump_get_version = view.findViewById(R.id.getVersionButton);
        // TextView
        dump_result     = view.findViewById(R.id.dump_result);
        adress = view.findViewById(R.id.address);
        quantity = view.findViewById(R.id.edit_quantity);

        adress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String arg = s.toString();
                if(!arg.equals(arg.toUpperCase())){
                    arg = arg.toUpperCase();
                    adress.setText(arg);
                    adress.setSelection(adress.getText().length());
            }
        }});

        dump_get_version.setVisibility(View.VISIBLE);
        dump_get_version.setBackgroundColor(Color.TRANSPARENT);

        dump_get_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commands.readDump("61FC", 16, 50);
                dump_result.setText(VariablesClass.getPacket().toString());
                DynamicToast.makeError(getContext(), "1337!", 1).show();
            }
        });


        quantity.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER){
                if (dump_adress.getEditText().getText().toString().equals("") || dump_page.getEditText().getText().toString().equals("") || dump_quantity.getEditText().getText().toString().equals("")){
                    Toast.makeText(getContext(), "Введите все значения!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Commands.readDump(Objects.requireNonNull(dump_adress.getEditText()).getText().toString(), Integer.parseInt(String.valueOf(Objects.requireNonNull(dump_page.getEditText()).getText())), Integer.parseInt(String.valueOf(Objects.requireNonNull(dump_quantity.getEditText()).getText())));
                    dump_result.setText(VariablesClass.getPacket().toString());
                }
            }
            return false;
        });

        dump_get.setOnClickListener(v -> {
            if (dump_adress.getEditText().getText().toString().equals("") || dump_page.getEditText().getText().toString().equals("") || dump_quantity.getEditText().getText().toString().equals("")){
                DynamicToast.makeWarning(getContext(), "Введите все значения!", 1).show();
            }


            if (dump_adress.getEditText().getText().toString().contains(" ") || dump_page.getEditText().getText().toString().contains(" ") || dump_quantity.getEditText().getText().toString().contains(" ")){
                DynamicToast.makeWarning(getContext(), "Введите верные значения!", 1).show();
            }
            else {
                Commands.readDump(Objects.requireNonNull(dump_adress.getEditText()).getText().toString(), Integer.parseInt(String.valueOf(Objects.requireNonNull(dump_page.getEditText()).getText())), Integer.parseInt(String.valueOf(Objects.requireNonNull(dump_quantity.getEditText()).getText())));
                dump_result.setText(VariablesClass.getPacket().toString());
                DynamicToast.makeSuccess(getContext(), "OK!", 1).show();
            }
        });
        return view;
    }


}


