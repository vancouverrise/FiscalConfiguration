package market.ics.fiscalconfiguration.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import market.ics.fiscalconfiguration.R;

public class fragment_test extends Fragment {


    private TextView manufacturer, serialnumber, isFiscal, manufactureDate, romVersion, cutterMode, cashbox, modemIp, modemMask, modemGateway, tv_a, tv_b, tv_c, tv_d, tv_e;
    private FloatingActionButton sendMailFab, getInfoFab;
    private MaterialButton getinfo;
    public fragment_test(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_md_info, container, false);

        manufacturer = view.findViewById(R.id.tv_model);
        serialnumber = view.findViewById(R.id.tv_serial);
        isFiscal = view.findViewById(R.id.tv_fiscal);
        manufactureDate = view.findViewById(R.id.tv_manufacturing_date);
        romVersion = view.findViewById(R.id.tv_rom);
        cutterMode = view.findViewById(R.id.tv_cutter);
        modemIp = view.findViewById(R.id.tv_ip_modem);
        modemMask = view.findViewById(R.id.tv_mask_modem);
        modemGateway = view.findViewById(R.id.tv_gateway_modem);
        cashbox = view.findViewById(R.id.tv_cashbox);

        tv_a = view.findViewById(R.id.tv_tax_a);
        tv_b = view.findViewById(R.id.tv_tax_b);
        tv_c = view.findViewById(R.id.tv_tax_c);
        tv_d = view.findViewById(R.id.tv_tax_d);
        tv_e = view.findViewById(R.id.tv_tax_e);

        getinfo = view.findViewById(R.id.get_info_button1);

        getinfo.setOnClickListener(v -> getSome());



        return view;
    }

    private void getSome(){

        VariablesClass.Initialize();

        manufacturer.setText("Модель: IKC-E810T");
        manufactureDate.setText(String.format("Изготовлен: %s", VariablesClass.getManufacturerDate()));
        isFiscal.setText(String.format("Фискализация: %s", VariablesClass.getIsfiscal()));
        cutterMode.setText(String.format("Обрезчик: %s", VariablesClass.getIscutter()));
        romVersion.setText(String.format("Прошивка: %s", VariablesClass.getRomVersion()));
        cashbox.setText(String.format("Денежный ящик: %s", VariablesClass.getCashboxResponse()));

        tv_a.setText(String.format("Tax A: %s%%", VariablesClass.getVatA()));
        tv_b.setText(String.format("Tax B: %s%%", VariablesClass.getVatB()));
    }
}
