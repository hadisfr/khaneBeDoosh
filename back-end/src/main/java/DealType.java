package main.java;

public enum DealType {
    SELL(0), RENT(1);

    private final int value;

    DealType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static DealType parseInt(int i) {
        switch (i) {
            case 0:
                return SELL;
            case 1:
                return RENT;
            default:
                return null;
        }
    }

    public static DealType parseString(String string) {
        if (string.equals("0") || string.equals("SELL"))
            return SELL;
        else if (string.equals("1") || string.equals("RENT"))
            return RENT;
        else
            return null;
    }

    @Override
    public String toString() {
        if(value == 0)
            return "SELL";
        else
            return "RENT";
    }
}