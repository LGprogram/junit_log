package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liu on 2017/2/18.
 */
@Data
public class DeviceRentDoc implements Serializable {
    private Integer id;
    private String sourceName;
    private String newName;
    private Integer rentId;

}
