package com.kaishengit.pojo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by liu on 2017/2/23.
 */
@Data
public class France {
    public static final String TYPE_INCOME = "收入";
    public static final String TYPE_EXPEND ="支出";
    public static final String STATE_DONE = "确认";
    public static final String STATE_UNDONE= "未确认";
    public static final String REMARK_PRE ="预付款";
    public static final String REMARK_LAST ="尾款";
    public static final String MODULE_RENT = "租赁";
    public static final String MODULE_OUT ="外包";
    private Integer id;
    private String serialNum;
    private String type;
    private String state;
    private String createUser;
    private String createDate;
    private String confimUser;
    private String confimDate;
    private String remark;
    private Float money;
    private String module;
    private String rentSerialNum;


}
