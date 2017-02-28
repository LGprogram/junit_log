package com.kaishengit.mapper;

import com.kaishengit.pojo.WorkOutDetail;

import java.util.List;

/**
 * Created by liu on 2017/2/19.
 */
public interface WorkOutDetailMapper {
    void bathSave(List<WorkOutDetail> detailList);

    List<WorkOutDetail> findListByRentId(Integer rentId);
}
