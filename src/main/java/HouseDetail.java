package main.java;

public class HouseDetail {
    private String address;
    private String phone;
    private String description;
    private String expireTime;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public HouseDetail(String address, String phone, String description, String expireTime) {

        this.address = address;
        this.phone = phone;
        this.description = description;
        this.expireTime = expireTime;
    }
}
