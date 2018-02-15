package main.java;

public enum DealType{
    BUY(0), RENT(1);

    private final int value;

    private DealType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static DealType parseString(String string){
        if (string.equals("0"))
            return BUY;
        else
            return RENT;
    }
}