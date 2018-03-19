package main.java;

public class PriceSell extends Price {
    private int sellPrice;

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public PriceSell(int sellPrice) {

        this.sellPrice = sellPrice;
    }
}
