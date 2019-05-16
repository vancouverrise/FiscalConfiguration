package market.ics.fiscalconfiguration.Communication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import market.ics.fiscalconfiguration.Driver.PacketBuilder;
import market.ics.fiscalconfiguration.Driver.Request.PrinterCommand;
import market.ics.fiscalconfiguration.MainActivity;

public  class AsyncSocket extends AsyncTask<PrinterCommand, Void, List<Byte>> {

    private PacketBuilder packet = new PacketBuilder();
    private ByteBuffer buffer;

    BluetoothConnector bl = new BluetoothConnector();


    private byte[] recPacket;
    private static short commandCounter = 1;
    private Context context;
    private boolean wasFail;

    public AsyncSocket() {
        super();
    }

    @Override
    protected List<Byte> doInBackground(PrinterCommand... printerCommands) {

        try {

            byte[] packet = this.packet.PacketBuilder((byte) commandCounter, printerCommands[0]);

            buffer = ByteBuffer.allocate(1024);

            /*System.out.println("Sending this: " + Arrays.toString(packet));*/

            InputStream inputStream = bl.getBluetoothSocket().getInputStream();
            OutputStream outputStream = bl.getBluetoothSocket().getOutputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);



            outputStream.write(packet);


            while (true) {
                int b;

                try {
                    b = bufferedInputStream.read();
                } catch (Exception e) {
                    System.out.println(" LA " + e.getMessage());
                    wasFail = true;
                    this.isCancelled();
                    break;
                }




                buffer.put((byte) b);
                if (buffer.get(0) == (byte) 6) {
                    Log.d("Packet", "Confirmed");

                    buffer.position(0);
                }
                if (buffer.get(0) == (byte) 22) {
                    Log.d("Packet", "Processing");
                    buffer.position(0);
                }
                if (buffer.position() > 10) {

                    if (    buffer.get(buffer.position() - 4) == (byte) 16 &&
                            buffer.get(buffer.position() - 3) == (byte) 3 &&
                            buffer.get(buffer.position() - 5) != (byte) 16)

                    {
                        break;
                    }
                }
            }



            if (buffer.hasArray()) {
                Log.d("Packet", "Transforming:");
                recPacket = buffer.array();
            }

            Log.d("Packet", "Confirmed");
       /*     System.out.println("Packer after Transforming into: " + Arrays.toString(recPacket));*/

            commandCounter++;


        } catch (Throwable t) {
            throw  new RuntimeException(t);
        }

        return Arrays.asList(ArrayUtils.toObject(recPacket));
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Byte> o) {
        super.onPostExecute(o);
       /* System.out.println("Some Testing: " + o);*/
    }

    public void reconnect(){

        try {
            bl.getBluetoothSocket().connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
