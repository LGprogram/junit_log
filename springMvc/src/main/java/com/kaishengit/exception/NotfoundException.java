package com.kaishengit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by liu on 2017/1/11.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)//自定义异常及其类型
public class NotfoundException extends RuntimeException {
}
