package market.ics.fiscalconfiguration.Driver.DataType;

public final class ByteBits {

    public final Byte aByte;

    public ByteBits(Byte aByte) {
        this.aByte = aByte;
    }

    public boolean getFirst() {
        byte mask = 1;
        return this.apply(mask);
    }

    public boolean getSecond() {
        byte mask = 2;
        return this.apply(mask);
    }

    public boolean getThird() {
        byte mask = 4;
        return this.apply(mask);
    }

    public boolean getForth() {
        byte mask = 8;
        return this.apply(mask);
    }

    public boolean getFifth() {
        byte mask = 16;
        return this.apply(mask);
    }

    public boolean getSixth() {
        byte mask = 32;
        return this.apply(mask);
    }

    public boolean getSeventh() {
        byte mask = 64;
        return this.apply(mask);
    }

    public boolean getEighth() {
        byte mask = -128;
        return this.apply(mask);
    }

    public boolean apply(byte mask) {
        byte rez = (byte)(this.aByte & mask);
        return rez != 0;
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            ByteBits other = (ByteBits)obj;
            if (this.aByte == null) {
                if (other.aByte != null) {
                    return false;
                }
            } else if (!this.aByte.equals(other.aByte)) {
                return false;
            }

            return true;
        }
    }
}