package com.kaishengit.mapper;

import com.kaishengit.pojo.DeviceRentDetail;

import java.util.List;

/**
 * Created by liu on 2017/2/18.
 */
public interface DeviceRentDetailMapper {
    void batchSave(List<DeviceRentDetail> detailList);

    List<DeviceRentDetail> findRentDetailByRentId(Integer rentId);
}
