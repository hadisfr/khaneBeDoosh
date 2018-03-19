package main.java;

public class PriceSell extends Price {
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public PriceSell(int price) {

        this.price = price;
    }
}
