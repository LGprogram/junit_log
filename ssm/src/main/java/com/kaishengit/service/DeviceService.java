package com.kaishengit.service;

import com.kaishengit.pojo.Device;

import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/1/19.
 */
public interface DeviceService {

    List<Device> findAll();

    List<Device> findAllByParams(String start, String length);

    Long count();

    void save(Device device);

    List<Device> findAllBySearchParam(Map<String, Object> searchParam);

    void del(Integer id);

    Long filterCount(Map<String, Object> searchParam);
}
