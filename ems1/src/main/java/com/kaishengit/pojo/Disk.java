package com.kaishengit.pojo;

import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by liu on 2017/2/21.
 */
@Data
public class Disk {
    public static final String FILE_TYPE = "file";
    public static final String DIRECTORY_TYPE = "directory";

    private Integer id;
    private String sourceName;
    private String newName;
    private Integer fid;
    private String size;
    private String type;
    private Timestamp createTime;
    private String createUser;
}
