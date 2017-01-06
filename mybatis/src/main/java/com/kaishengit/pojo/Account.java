package com.kaishengit.pojo;

/**
 * Created by liu on 2017/1/6.
 */
public class Account {
    private Integer id;
    private String accname;
    private String address;

    public Account() {}
    public Account(String accname, String address) {
        this.accname = accname;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccname() {
        return accname;
    }

    public void setAccname(String accname) {
        this.accname = accname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
