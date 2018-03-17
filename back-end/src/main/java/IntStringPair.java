package main.java;

public class IntStringPair {
    private int integer;
    private String string;

    public IntStringPair(int integer, String string) {
        this.integer = integer;
        this.string = string;
    }

    public int getInteger() {
        return integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IntStringPair) {
            IntStringPair casted = (IntStringPair) obj;
            if (casted.getInteger() == this.integer && casted.getString().equals(this.string))
                return true;
        }
        return false;
    }
}
