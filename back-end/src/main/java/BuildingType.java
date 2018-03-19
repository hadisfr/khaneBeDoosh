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
            return "APARTMENT";
        else if (value == 1)
            return "VILLA";
        else
            return "";
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