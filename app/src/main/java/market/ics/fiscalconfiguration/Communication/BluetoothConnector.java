package market.ics.fiscalconfiguration.Communication;

import android.bluetooth.BluetoothSocket;

import market.ics.fiscalconfiguration.MainActivity;

class BluetoothConnector {

    BluetoothSocket getBluetoothSocket() {
        return MainActivity.getBtSocket();
    }
}