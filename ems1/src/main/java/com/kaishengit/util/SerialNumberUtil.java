package com.kaishengit.util;

import com.kaishengit.mapper.DeviceRentMapper;
import com.kaishengit.mapper.FranceMapper;
import com.kaishengit.pojo.DeviceRent;
import com.kaishengit.pojo.France;
import com.kaishengit.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by liu on 2017/2/18.
 */

public class SerialNumberUtil {
    public static String getSerialNumber(DeviceRentMapper deviceRentMapper) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String date = df.format(new Date());
        date = date.substring(2, date.length());
        String serialNum = "";
        List<DeviceRent> list = deviceRentMapper.findAllByContain(date);
        if (list.isEmpty()) {
            serialNum = date + "0001";

        } else {
            Integer old = Integer.valueOf(list.get(list.size() - 1).getSerialNum());
            serialNum = Integer.toString(old + 1);

        }
        return serialNum;
    }

    public static String getFranceSerialNum(FranceMapper franceMapper) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String date = df.format(new Date());
        date = date.substring(2, date.length());
        String serialNum = "";
        List<France> list = franceMapper.findAllByContain(date);
        if (list.isEmpty()) {
            serialNum = date + "0001";

        } else {
            Integer old = Integer.valueOf(list.get(list.size() - 1).getSerialNum());
            serialNum = Integer.toString(old + 1);

        }
        return serialNum;
    }
}
