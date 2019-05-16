package market.ics.fiscalconfiguration.Driver.DataType;

import java.nio.charset.Charset;
import java.util.List;

public class Sym {
    private final List<Byte> bytes;
    private final Charset charset;
    private static final Charset defaultCharset = Charset.forName("cp866");

    public Sym(List<Byte> bytes, Charset charset) {
        this.bytes = bytes;
        this.charset = charset;
    }

    public Sym(List<Byte> bytes) {
        this(bytes, defaultCharset);
    }

    public String toString() {
        if (this.bytes != null && this.bytes.size() != 0) {
            byte[] sb = new byte[this.bytes.size()];

            for(int i = 0; i < this.bytes.size(); ++i) {
                sb[i] = (Byte)this.bytes.get(i);
            }

            return new String(sb, this.charset);
        } else {
            return "";
        }
    }
}
