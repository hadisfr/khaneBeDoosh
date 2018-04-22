package main.java;

public class HouseDetail {
    private String phone;
    private String description;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HouseDetail(String phone, String description) {
        this.phone = phone;
        this.description = description;
    }
}
