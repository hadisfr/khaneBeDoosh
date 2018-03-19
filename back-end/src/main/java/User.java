package main.java;

public abstract class User {

    protected String name;
    private int id;
    protected static int next_index = 1000;

    public User(String name) {
        this.name = name;
        this.id = next_index++;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

}