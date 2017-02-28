package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liu on 2017/2/18.
 */
@Data
public class DeviceRentDetail implements Serializable {
    private Integer id;
    private String deviceName;
    private String deviceUnit;
    private float devicePrice;
    private float num;
    private float totalPrice;
    private Integer rentId;
}
