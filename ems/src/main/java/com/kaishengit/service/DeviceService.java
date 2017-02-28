package com.kaishengit.service;

import com.kaishengit.pojo.Device;

import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/2/7.
 */
public interface DeviceService {
    void save(Device device);

    List<Device> findAllBySearchParam(Map<String, Object> searchParam);

    Long count();

    Long filterCount(Map<String, Object> searchParam);

    void del(Integer id);

    List<Device> findAll();

    Device findById(Integer id);

    void update(Device device);
}
