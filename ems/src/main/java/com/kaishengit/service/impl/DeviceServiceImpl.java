package com.kaishengit.service.impl;

import com.kaishengit.mapper.DeviceMapper;
import com.kaishengit.pojo.Device;
import com.kaishengit.service.DeviceService;
import com.kaishengit.shiro.ShiroUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/2/7.
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    private Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);
    @Autowired
    private DeviceMapper deviceMapper;
    @Override
    public void save(Device device) {
        deviceMapper.save(device);
        logger.info("{}添加了新设备{}", ShiroUtil.getUserName(),device.getName());
    }

    @Override
    public List<Device> findAllBySearchParam(Map<String, Object> searchParam) {
        return deviceMapper.findBySearchParam(searchParam);
    }

    @Override
    public Long count() {
        return deviceMapper.count();
    }

    @Override
    public Long filterCount(Map<String, Object> searchParam) {
        return deviceMapper.filterCount(searchParam);
    }

    @Override
    public void del(Integer id) {
        Device device = deviceMapper.findById(id);
        deviceMapper.del(id);
        logger.info("{}删除了设备{}", ShiroUtil.getUserName(),device.getName());
    }

    @Override
    public List<Device> findAll() {
        return deviceMapper.findAll();
    }

    @Override
    public Device findById(Integer id) {
        return deviceMapper.findById(id);
    }

    @Override
    public void update(Device device) {

        deviceMapper.update(device);
        logger.info("{}在时间：{}修改设备{}的数量为{}",ShiroUtil.getUserName(),device.getLasteditTime(),device.getName(),device.getCurrentNum() );
    }
}
