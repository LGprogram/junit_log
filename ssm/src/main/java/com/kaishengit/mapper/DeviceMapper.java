package com.kaishengit.mapper;

import com.kaishengit.pojo.Device;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/1/19.
 */
public interface DeviceMapper {
    List<Device> findAll();

    List<Device> findAllByParams(@Param("start") String start,@Param("length") String length);

    Long count();

    void save(Device device);

    List<Device> findBySearchParam(Map<String, Object> searchParam);

    void del(Integer id);

    Long filterCount(Map<String, Object> searchParam);
}
