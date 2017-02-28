package com.kaishengit.dto;

import lombok.Data;

/**
 * Created by liu on 2017/2/16.
 */
@Data
public class AjaxResult {
    public static String SUCCESS="success";
    public static String ERROR="error";
    private String state;
    private String message;
    private Object data;
    public AjaxResult(String state,String message){
        this.state=state;
        this.message=message;
    }
    public AjaxResult(Object data){
        this.state=SUCCESS;
        this.data= data;
    }

}
