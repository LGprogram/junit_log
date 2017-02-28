package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by liu on 2017/2/18.
 */
@Data
public class DeviceRent implements Serializable {
    /**
     * 1表示设备租赁合同
     * 2表示外包务工合同
     */
    public static final Integer RENT = 1;
    public static final Integer WORK = 2;
    private Integer id;
    private String companyName;
    private String linkMan;
    private String cardNum;
    private String tel;
    private String address;
    private String fax;
    private String rentDate;
    private String backDate;
    private Integer totalDays;
    private float totalPrice;
    private float preCost;
    private float lastCost;
    private Timestamp createTime;
    private String createUser;
    private String serialNum;
    private String state;
    private Integer type;

}

