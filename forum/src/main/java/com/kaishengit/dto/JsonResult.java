package com.kaishengit.dto;

import com.sun.net.httpserver.Authenticator;

/**
 * Created by liu on 2016/12/19.
 */
public class JsonResult {
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    private String state;
    private String message;
    private Object data;

    public JsonResult() {
    }

    public JsonResult(String state, Object data) {
        this.state = SUCCESS;
        this.data = data;
    }

    public JsonResult(String state, String message) {
        this.state = ERROR;
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
