package market.ics.fiscalconfiguration.Driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SerialCommand {
    private final CommandRequest request;
    private final CheckSum checksum;

    private final List<Byte> start;
    private final List<Byte> end;

    private SerialCommand(SerialCommand.Builder builder, CommandRequest request, CheckSum checksum) {
        this.start = Arrays.asList((byte) 16, (byte) 2);
        this.end = Arrays.asList((byte) 16, (byte) 3);
        this.request = request;
        this.checksum = checksum;
    }

    public byte[] getAsBytes() {
        List<Byte> commandBytes = this.getBytes();
        byte[] result = new byte[commandBytes.size()];

        for (int i = 0; i < commandBytes.size(); ++i) {
            result[i] = (Byte) commandBytes.get(i);
        }
        return result;
    }


    public List<Byte> getBytes() {
        List<Byte> commandBytes = new ArrayList();
        commandBytes.addAll(this.start);
        commandBytes.addAll(this.dleEscapedRequest());
        commandBytes.addAll(this.dleEscapedCheckSum());
        commandBytes.addAll(this.end);
        return commandBytes;
    }

    private Collection<? extends Byte> dleEscapedRequest() {
        List<Byte> escapedBytes = new ArrayList();
        List<Byte> bytes = this.request.getBytes();
        Iterator tempIterator = bytes.iterator();

        while (tempIterator.hasNext()) {
            Byte aByte = (Byte) tempIterator.next();
            escapedBytes.add(aByte);
            if (aByte.equals((byte)16)) {
                escapedBytes.add(aByte);
            }
        }

        return escapedBytes;
    }

    private Collection<? extends Byte> dleEscapedCheckSum() {
        List<Byte> escapedBytes = new ArrayList();
        Byte checkSum = this.checksum.calculate();
        escapedBytes.add(checkSum);
        if (checkSum.equals(16)) {
            escapedBytes.add(checkSum);
        }

        return escapedBytes;

    }

    public static class Builder {
        private final CommandRequest request;
        private final CheckSum checksum;

        public Builder(CommandRequest request, CheckSum checksum) {
            this.request = request;
            this.checksum = checksum;
        }

        public Builder(CommandRequest request) {
            this(request, new CheckSumImpl(request));
        }

        public SerialCommand build() {
            return new SerialCommand(this, this.request, this.checksum);

        }
    }
}
