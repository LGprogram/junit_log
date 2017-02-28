package com.kaishengit.dto;

import lombok.Data;

/**
 * Created by liu on 2017/2/23.
 */

/**
 * 用于统计日收入/支出，月收入/支出，年收入/支出
 *
 */
@Data
public class EchartsResult {

    public static final String DEVICE_RENT = "设备租赁";
    public static final String WORK_OUT = "劳务外包";
    private String date;
    private String name;
    private Float value;
}
