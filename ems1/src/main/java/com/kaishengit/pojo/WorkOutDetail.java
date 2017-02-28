package com.kaishengit.pojo;

import lombok.Data;

/**
 * Created by liu on 2017/2/19.
 */
@Data
public class WorkOutDetail {
    private Integer id;
    private String workType;
    private float reward;
    private Integer rentNum;
    private float total;
    private Integer rentId;

}
