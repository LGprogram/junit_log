package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by liu on 2017/2/7.
 */
@Data
public class Device implements Serializable {
    private Integer id;
    private String name;
    private String unit;
    private float currentNum;
    private float totalNum;
    private Float price;
    private Timestamp createTime;
    private Timestamp lasteditTime ;
    private Integer createUserId;
    private Integer editUserId;
    private Float damagePrice;
    private Float missPrice;
}
