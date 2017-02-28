package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liu on 2017/2/18.
 */
@Data
public class Worker implements Serializable {
    private Integer id;
    private String workType;
    private float reward;
    private Integer totalNum;
    private Integer currentNum;
}
