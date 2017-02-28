package com.kaishengit.pojo;

/**
 * Created by liu on 2017/1/11.
 */
public class Account {
    private String username;
    private String address;

    public Account() {}
    public Account(String username, String address) {
        this.username = username;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
