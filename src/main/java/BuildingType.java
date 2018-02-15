package main.java;

public enum BuildingType{
    APARTMENT(0),
    VILLA(1);

    private final int value;

    private BuildingType(int value){
        this.value = value;
    }

    @Override
    public String toString() {
        if(value == 0)
            return "آپارتمان";
        else
            return "ویلایی";
    }

    public static BuildingType parseString(String string){
        if(string.equals("آپارتمان"))
            return APARTMENT;
        else if(string.equals("ویلایی"))
            return VILLA;
        else
            return null;
    }
}