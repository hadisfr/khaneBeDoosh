package main.java;

public class StringStringPair {
    private String first;
    private String second;

    public StringStringPair(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return this.first;
    }

    public void setFisrt(String str) {
        this.first = str;
    }

    public String getSecond() {
        return this.second;
    }

    public void setSecond(String str) {
        this.second = str;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StringStringPair) {
            StringStringPair casted = (StringStringPair) obj;
            if (casted.getFirst().equals(this.first) && casted.getSecond().equals(this.second))
                return true;
        }
        return false;
    }
}
