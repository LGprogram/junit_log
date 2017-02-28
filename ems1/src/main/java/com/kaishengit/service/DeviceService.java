package com.kaishengit.service;

import com.kaishengit.dto.DeviceRentDto;
import com.kaishengit.pojo.Device;
import com.kaishengit.pojo.DeviceRent;
import com.kaishengit.pojo.DeviceRentDetail;
import com.kaishengit.pojo.DeviceRentDoc;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

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

    String saveDeviceRentDto(DeviceRentDto deviceRentDto);


    DeviceRent findBySerialNum(String serialNum);

    List<DeviceRentDetail> findRentDetailByRentId(Integer rentId);

    List<DeviceRentDoc> findDeviceRentDocListByRentId(Integer id);

    DeviceRentDoc findDocById(Integer id);

    DeviceRent findDeviceRentById(Integer id);

    void downloadZipFile(DeviceRent rent, ZipOutputStream zipOutputStream) throws IOException;

    List<DeviceRent> findAllDeviceRent();

    List<DeviceRent> findAllDeviceRentBySearchParam(Map<String,Object> searchParam);



    void changeRentState(Integer id);

    Long countDeviceRentByType(Integer type);
}
