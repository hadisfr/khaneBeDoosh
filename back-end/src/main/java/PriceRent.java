package main.java;

public class PriceRent extends Price {
    private int basePrice;
    private int rentPrice;

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public int getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(int rentPrice) {
        this.rentPrice = rentPrice;
    }

    public PriceRent(int basePrice, int rentPrice) {

        this.basePrice = basePrice;
        this.rentPrice = rentPrice;
    }
}
