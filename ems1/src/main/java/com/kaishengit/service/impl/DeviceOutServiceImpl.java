package com.kaishengit.service.impl;

import com.kaishengit.mapper.DeviceOutMapper;
import com.kaishengit.pojo.DeviceOut;
import com.kaishengit.service.DeviceOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liu on 2017/2/10.
 */
@Service
public class DeviceOutServiceImpl implements DeviceOutService {
    @Autowired
    private DeviceOutMapper deviceOutMapper;

    @Override
    public void save(DeviceOut deviceOut) {
        deviceOutMapper.save(deviceOut);
    }

    @Override
    public List<DeviceOut> findAllContain(String date) {
        return deviceOutMapper.findAllContain(date);
    }

    @Override
    public List<DeviceOut> findAll() {
        return deviceOutMapper.findAll();
    }

    @Override
    public DeviceOut findById(Integer id) {
        return deviceOutMapper.findById(id);
    }

    @Override
    public void update(DeviceOut deviceOut) {
        deviceOutMapper.update(deviceOut);
    }
}
