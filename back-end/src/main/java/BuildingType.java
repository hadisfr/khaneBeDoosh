package main.java;

public enum BuildingType {
    APARTMENT(0),
    VILLA(1);

    private final int value;

    private BuildingType(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value == 0)
            return "آپارتمان";
        else
            return "ویلایی";
    }

    public static BuildingType parseString(String string) {
        if (string.equals("آپارتمان") || string.equals("APARTMENT"))
            return APARTMENT;
        else if (string.equals("ویلایی") || string.equals("VILLA"))
            return VILLA;
        else
            return null;
    }
}