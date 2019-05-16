package market.ics.fiscalconfiguration;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Window;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import market.ics.fiscalconfiguration.Fragments.ViewPagerAdapter;
import market.ics.fiscalconfiguration.Fragments.fragment_dump;
import market.ics.fiscalconfiguration.Fragments.fragment_info;
import market.ics.fiscalconfiguration.Fragments.fragment_operations;
import market.ics.fiscalconfiguration.Fragments.fragment_tax;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter btAdapter = null;
    private static BluetoothSocket btSocket = null;
    private static BluetoothDevice bluetoothDevice;
    ProgressDialog progressDialog;


    public static BluetoothSocket getBtSocket() {
        return btSocket;
    }

    private static final UUID   MY_UUID =   UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String address =   "00:18:E4:00:54:05";
    private static final String name = "MARKET";
    private static final String name2 = "MARKET1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(000000));


        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#BBDEFB"));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        TabLayout tableLayout = findViewById(R.id.tablayout_id);
        ViewPager viewPager = findViewById(R.id.viewpager_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager);

        adapter.AddFragment(new fragment_info(), "Принтер");
        adapter.AddFragment(new fragment_operations(),"Операции");
        adapter.AddFragment(new fragment_dump(),"Дамп");
        adapter.AddFragment(new fragment_tax(),"Ставки");
        //adapter.AddFragment(new fragment_sale(),"Продажа");

        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null)
        {
            AlertBox("Fatal Error", "Bluetooth Not supported. Aborting.");
            finish();
        }
        else {
            if (btAdapter.isEnabled()) {
                System.out.println("Great!");
            }
            else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);}
        }

        if (btAdapter.isEnabled()) {
            new Thread(() -> {
                Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();

                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice device : pairedDevices) {
                        switch (device.getName())
                        {
                            case name: bluetoothDevice = device;
                            break;
                            case name2: bluetoothDevice = device;
                            break;

                        }
                    }
                }

                if (bluetoothDevice == null){
                    System.out.println("GOVNO");
                    runOnUiThread(() -> {
                        new AlertDialog.Builder(this)
                                .setTitle("No Supporting Devices")
                                .setMessage( "Press OK to exit." )
                                .setPositiveButton("OK", (arg0, arg1) -> {
                                            finish();
                                        }
                                ).show();
                    });

                }
                else {

                    runOnUiThread(() -> {
                        progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setMessage("Подключение Bluetooth...");
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                    });

                    try {
                        btSocket = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
                    } catch (IOException e) {
                        System.out.println("Some shit!");
                    }

                    while (true) {
                        if (!btSocket.isConnected()) {
                            try {
                                if (btSocket != null) {
                                    System.out.println("Trying...");
                                    btSocket.connect();
                                }
                            } catch (IOException e) {
                                System.out.println(e);
                            }
                        }
                        if (btSocket.isConnected()) {
                            try {
                                Thread.sleep(350);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                            break;
                        }
                    }
                }
            }).start();
        }
        else {
            onRestart();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            if (btSocket != null) {
                btSocket.close();
            }
        } catch (IOException e2) {
            AlertBox("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
        }
        btAdapter = null;
    }

    public void AlertBox( String title, String message ){
        new AlertDialog.Builder(this)
                .setTitle( title )
                .setMessage( message + " Press OK to exit." )
                .setPositiveButton("OK", (arg0, arg1) -> {}).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (btSocket != null) {
                btSocket.close();
            }
        } catch (IOException e2) {
            AlertBox("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
        }
        btAdapter = null;
        btSocket = null;
        bluetoothDevice = null;
    }
}
