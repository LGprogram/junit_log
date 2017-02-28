package com.kaishengit.mapper;

import com.kaishengit.pojo.DeviceRentDoc;

import java.util.List;

/**
 * Created by liu on 2017/2/18.
 */
public interface DeviceRentDocMapper {
    void batchSave(List<DeviceRentDoc> docList);

    List<DeviceRentDoc> findDeviceRentDocListByRentId(Integer rentId);

    DeviceRentDoc findDocById(Integer id);
}
