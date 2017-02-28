package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by liu on 2017/2/10.
 */
@Data
public class DeviceOut implements Serializable {
    public static final String DONE = "完成";
    public static final String UNDONE="未完成";
    private Integer id;
    private String serialNum;
    private Integer userId;
    private Integer deviceId;
    private Timestamp createtime;
    private Timestamp repaytimePlan;
    private Timestamp repaytimeActual;
    private String rentCompany;
    private String companyAddress;
    private String companyTel;
    private String companyLegal;
    private  String legalPhone;
    private String legalCard;
    private float rentMoney;
    private float advanceMoney;
    private float lastMoney;
    private String state=UNDONE;
    private Integer number;
    private Float deditMoney;
    private String deviceName;
    private float damagePrice;
    private float missPrice;

}
