package com.kaishengit.service;

import com.kaishengit.pojo.DeviceOut;

import java.util.List;

/**
 * Created by liu on 2017/2/10.
 */
public interface DeviceOutService {
    void save(DeviceOut deviceOut);

    List<DeviceOut> findAllContain(String date);

    List<DeviceOut> findAll();

    DeviceOut findById(Integer id);

    void update(DeviceOut deviceOut);
}
