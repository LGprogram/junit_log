package com.kaishengit.mapper;

import com.kaishengit.pojo.DeviceRent;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/2/18.
 */
public interface DeviceRentMapper {
    List<DeviceRent> findAllByContain(String date);

    void save(DeviceRent rent);

    void updateCost(@Param("totalPrice") float totalPrice,@Param("preCost") float preCost, @Param("lastCost")float lastCost, @Param("id")Integer id);

    DeviceRent findBySerialNum(String serialNum);

    DeviceRent findDeviceRentById(Integer id);

    List<DeviceRent> findAllDeviceRent();

    List<DeviceRent> findAllDeviceRentBySearchParam(Map<String, Object> searchParam);



    void updateRent(DeviceRent rent);

    Long countDeviceRentByType(Integer type);
}
