package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liu on 2017/1/19.
 */
@Data
public class Device implements Serializable {
    private Integer id;
    private String name;
    private String unit;
    private Integer currentNum;
    private Integer totalNum;
    private Float price;
}
