package com.kaishengit.mapper;

import com.kaishengit.pojo.Device;

import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/2/7.
 */
public interface DeviceMapper {
    void save(Device device);

    List<Device> findBySearchParam(Map<String, Object> searchParam);

    Long count();

    Long filterCount(Map<String, Object> searchParam);

    void del(Integer id);

    Device findById(Integer id);

    List<Device> findAll();

    void update(Device device);
}
