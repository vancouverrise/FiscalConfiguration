package market.ics.fiscalconfiguration.Driver;

import com.google.common.primitives.Bytes;

    import market.ics.fiscalconfiguration.Driver.Request.PrinterCommand;

public class PacketBuilder {
    private CommandRequest commandRequest;
    private SerialCommand serialCommand;
    private SerialCommand.Builder serialCommandBuilder;
    private byte[] packet;



    public byte[] PacketBuilder (byte nomer, PrinterCommand command)  {
        System.out.println("Some test before send");

        commandRequest = (command.getRequest(nomer));
        serialCommandBuilder = new SerialCommand.Builder(commandRequest);
        serialCommand = serialCommandBuilder.build();
        System.out.println(serialCommand.getBytes());
        packet = Bytes.toArray(serialCommand.getBytes());
        return packet;



    }
}