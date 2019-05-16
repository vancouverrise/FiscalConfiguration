package market.ics.fiscalconfiguration.Driver.DataType;

import java.nio.charset.Charset;
import java.util.List;

public class PrinterString {
    private static final Byte CLOSE_BRACE = 125;
    private static final Byte OPEN_BRACE = 123;
    private static final Byte CLOSE_SQUARE = 93;
    private static final Byte OPEN_SQUARE = 91;
    private static final Byte APOSTROPHE = 39;
    private static final Byte MAX_CONTROL_BYTES_VALUE = 32;
    private static final Byte QUOTATION_MARK = 34;
    private static final Byte SPACE = 32;
    private static final Byte TILDE = 126;
    private static final Byte GRAVE_ACCENT = 96;
    private static final Byte LESS_THEN = 60;
    private static final Byte GREATER_THEN = 62;
    private static final Byte OPEN_BRACKET = 40;
    private static final Byte CLOSE_BRACKET = 41;
    private static final Charset CHARSET = Charset.forName("Cp866");
    private static final String UNICODE_CYRILLIC_SMALL_LETTER_BYELORUSSIAN_UKRAINIAN_I = "і";
    private static final String UNICODE_CYRILLIC_CAPITAL_LETTER_BYELORUSSIAN_UKRAINIAN_I = "І";
    private final List<Byte> stringBytes;

    private PrinterString(List<Byte> stringBytes) {
        this.stringBytes = stringBytes;
        this.replaceForbiddenCharacters();
    }

    public PrinterString(String stringValue) {
        this((new PrimitiveBytes(toValidEncodedByteArray(stringValue))).toListOfBytes());
    }

    public List<Byte> bytes() {
        return this.stringBytes;
    }

    public int length() {
        return this.stringBytes.size();
    }

    public PrinterString substring(int beginIndex, int endIndex) {
        return new PrinterString(this.stringBytes.subList(beginIndex, endIndex));
    }

    private void replaceForbiddenCharacters() {
        for(int i = 0; i < this.stringBytes.size(); ++i) {
            this.replaceControlBytesWithSpace(i);
            this.replaceQuotationMarkWithTilde(i);
            this.replaceApostropheWithGraveAccent(i);
            this.replaceOpenSquareWithLessThan(i);
            this.replaceCloseSquareWithGreaterThan(i);
            this.replaceOpenBraceWithOpenBracket(i);
            this.replaceCloseBraceWithCloseBracket(i);
        }

    }

    private void replaceCloseBraceWithCloseBracket(int i) {
        if (this.stringBytes.get(i) == CLOSE_BRACE) {
            this.stringBytes.set(i, CLOSE_BRACKET);
        }

    }

    private void replaceOpenBraceWithOpenBracket(int i) {
        if (this.stringBytes.get(i) == OPEN_BRACE) {
            this.stringBytes.set(i, OPEN_BRACKET);
        }

    }

    private void replaceCloseSquareWithGreaterThan(int i) {
        if (this.stringBytes.get(i) == CLOSE_SQUARE) {
            this.stringBytes.set(i, GREATER_THEN);
        }

    }

    private void replaceOpenSquareWithLessThan(int i) {
        if (this.stringBytes.get(i) == OPEN_SQUARE) {
            this.stringBytes.set(i, LESS_THEN);
        }

    }

    private void replaceApostropheWithGraveAccent(int i) {
        if (this.stringBytes.get(i) == APOSTROPHE) {
            this.stringBytes.set(i, GRAVE_ACCENT);
        }

    }

    private void replaceQuotationMarkWithTilde(int i) {
        if (this.stringBytes.get(i) == QUOTATION_MARK) {
            this.stringBytes.set(i, TILDE);
        }

    }

    private void replaceControlBytesWithSpace(int i) {
        if (0 <= (Byte)this.stringBytes.get(i) && (Byte)this.stringBytes.get(i) < MAX_CONTROL_BYTES_VALUE || -4 < (Byte)this.stringBytes.get(i) && (Byte)this.stringBytes.get(i) < -1) {
            this.stringBytes.set(i, SPACE);
        }

    }

    private static byte[] toValidEncodedByteArray(String s) {
        byte[] bytes = s.getBytes(CHARSET);
        byte questionMark = 63;

        for(int i = 0; i < bytes.length; ++i) {
            if (s.substring(i, i + 1).equals("і") && bytes[i] == questionMark) {
                bytes[i] = 105;
            }

            if (s.substring(i, i + 1).equals("І") && bytes[i] == questionMark) {
                bytes[i] = 73;
            }
        }

        return bytes;
    }

    public String toString() {
        byte[] primitiveBytes = new byte[this.stringBytes.size()];

        for(int i = 0; i < this.stringBytes.size(); ++i) {
            primitiveBytes[i] = (Byte)this.stringBytes.get(i);
        }

        return "PrinterString [" + new String(primitiveBytes, CHARSET) + "]";
    }
}