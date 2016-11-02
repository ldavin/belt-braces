package io.ldavin.beltsbraces.fixture;

public class JPrimitiveMembers {

    private final byte theByte;
    private final short theShort;
    private final int theInt;
    private final long theLong;
    private final float theFloat;
    private final double theDouble;
    private final boolean theBoolean;
    private final char theChar;

    public JPrimitiveMembers(byte theByte, short theShort, int theInt, long theLong, float theFloat, double theDouble, boolean theBoolean, char theChar) {
        this.theByte = theByte;
        this.theShort = theShort;
        this.theInt = theInt;
        this.theLong = theLong;
        this.theFloat = theFloat;
        this.theDouble = theDouble;
        this.theBoolean = theBoolean;
        this.theChar = theChar;
    }

    public byte getTheByte() {
        return theByte;
    }

    public short getTheShort() {
        return theShort;
    }

    public int getTheInt() {
        return theInt;
    }

    public long getTheLong() {
        return theLong;
    }

    public float getTheFloat() {
        return theFloat;
    }

    public double getTheDouble() {
        return theDouble;
    }

    public boolean getTheBoolean() {
        return theBoolean;
    }

    public char getTheChar() {
        return theChar;
    }
}
