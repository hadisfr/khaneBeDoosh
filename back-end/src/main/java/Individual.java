package main.java;

import java.util.ArrayList;

public class Individual extends User {

    private int balance;
    private String displayName;
    private boolean isAdmin;

    private ArrayList<StringStringPair> paidHouses = new ArrayList<StringStringPair>();

    public Individual(String username, int balance, String displayName, boolean isAdmin) {
        super(username);
        if (balance < 0)
            balance = 0;
        this.balance = balance;
        this.displayName = displayName;
        this.isAdmin = isAdmin;
    }

    public Individual(String username, int balance, String displayName, boolean isAdmin,
                      ArrayList<StringStringPair> paidHouses) {
        this(username, balance, displayName, isAdmin);
        this.paidHouses = new ArrayList<StringStringPair>(paidHouses);
    }

    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    public boolean hasPaidforHouse(String houseId, String ownerId) {
        for (StringStringPair member : paidHouses) {
            if (member.equals(new StringStringPair(ownerId, houseId))) {
                return true;
            }
        }
        return false;
    }

    private static int phonePrice = 1000;

    public boolean payForHouse(String houseId, String ownerId) {
        if (balance >= phonePrice) {
            paidHouses.add(new StringStringPair(ownerId, houseId));
            balance -= phonePrice;
            return true;
        }
        return false;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        if (balance < 0)
            balance = 0;
        this.balance = balance;
    }

    public String getDisplayName() {
        return displayName;
    }

    ArrayList<StringStringPair> getPaidHouses() {
        return paidHouses;
    }
}