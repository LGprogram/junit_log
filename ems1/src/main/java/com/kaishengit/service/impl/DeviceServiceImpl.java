package com.kaishengit.service.impl;

import com.google.common.collect.Lists;
import com.kaishengit.dto.DeviceRentDto;
import com.kaishengit.dto.wx.WeiXinMessage;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.*;
import com.kaishengit.pojo.*;
import com.kaishengit.service.DeviceService;
import com.kaishengit.service.WeiXinService;
import com.kaishengit.shiro.ShiroUtil;
import com.kaishengit.util.SerialNumberUtil;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by liu on 2017/2/7.
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    private Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private DeviceRentMapper rentMapper;
    @Autowired
    private DeviceRentDetailMapper rentDetailMapper;
    @Autowired
    private DeviceRentDocMapper deviceRentDocMapper;
    @Value("${upload.path}")
    private String filePath;
    @Autowired
    private FranceMapper franceMapper;
    @Autowired
    private WeiXinService weiXinService;
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

    @Override
    @Transactional
    public String saveDeviceRentDto(DeviceRentDto deviceRentDto) {
        //1.保存合同
        DeviceRent rent = new DeviceRent();
        rent.setAddress(deviceRentDto.getAddress());
        rent.setBackDate(deviceRentDto.getBackDate());
        rent.setCardNum(deviceRentDto.getCardNum());
        rent.setCompanyName(deviceRentDto.getCompanyName());
        rent.setCreateUser(ShiroUtil.getUserName());
        rent.setFax(deviceRentDto.getFax());
        rent.setLastCost(0F);
        rent.setTel(deviceRentDto.getTel());
        rent.setLinkMan(deviceRentDto.getLinkMan());
        rent.setPreCost(0F);
        rent.setTotalPrice(0F);
        rent.setRentDate(deviceRentDto.getRentDate());
        rent.setTotalDays(deviceRentDto.getTotalDays());
        rent.setSerialNum(SerialNumberUtil.getSerialNumber(rentMapper));
        //确定合同类型为设备租赁
        rent.setType(DeviceRent.RENT);
        rentMapper.save(rent);
        //2.保存合同详情
        List<DeviceRentDto.DeviceArrayBean> deviceArray  = deviceRentDto.getDeviceArray();
        List<DeviceRentDetail> detailList = Lists.newArrayList();
        float total = 0F;
        for(DeviceRentDto.DeviceArrayBean bean : deviceArray) {
            //验证设备库存是否足够
            Device device = deviceMapper.findById(bean.getId());
            float currentNum =  device.getCurrentNum();
            if(bean.getNum()>currentNum){
                throw new ServiceException("设备"+bean.getName()+"库存不足");
            }else{
                device.setCurrentNum(currentNum-bean.getNum());
                deviceMapper.update(device);
            }
            DeviceRentDetail rentDetail = new DeviceRentDetail();
            rentDetail.setDeviceName(bean.getName());
            rentDetail.setTotalPrice(bean.getTotal());
            rentDetail.setDevicePrice(bean.getPrice());
            rentDetail.setDeviceUnit(bean.getUnit());
            rentDetail.setNum(bean.getNum());
            rentDetail.setRentId(rent.getId());
            detailList.add(rentDetail);
            total += bean.getTotal();

        }
        if(!detailList.isEmpty()) {
            rentDetailMapper.batchSave(detailList);
        }
        //计算合同总价及预付款、尾款
        float totalPrice = total * rent.getTotalDays();
        float preCost = totalPrice  * 0.3F;
        float lastCost = totalPrice - preCost;
        rentMapper.updateCost(totalPrice,preCost,lastCost,rent.getId());
        //3.保存合同文件
        List<DeviceRentDto.DocBean> docBeanList = deviceRentDto.getFileArray();
        List<DeviceRentDoc> docList = Lists.newArrayList();
        for(DeviceRentDto.DocBean docBean:docBeanList){
            DeviceRentDoc deviceRentDoc = new DeviceRentDoc();
            deviceRentDoc.setSourceName(docBean.getSourceName());
            deviceRentDoc.setNewName(docBean.getNewName());
            deviceRentDoc.setRentId(rent.getId());
            docList.add(deviceRentDoc);
        }
        if(!docList.isEmpty()){
            deviceRentDocMapper.batchSave(docList);
        }
        //4.写入财务流水（收入预付款）
        France france = new France();
        france.setType(France.TYPE_INCOME);
        france.setState(France.STATE_UNDONE);
        france.setCreateUser(ShiroUtil.getUserName());
        france.setRemark(France.REMARK_PRE);
        france.setMoney(preCost);
        france.setModule(France.MODULE_RENT);
        france.setRentSerialNum(rent.getSerialNum());
        france.setSerialNum(SerialNumberUtil.getFranceSerialNum(franceMapper));
        france.setCreateDate(DateTime.now().toString("yyyy-MM-dd"));
        franceMapper.save(france);

        //5.给财务人员发送消息

        WeiXinMessage weiXinMessage = new WeiXinMessage();
        weiXinMessage.setToparty("2");
        WeiXinMessage.TextBean textBean = new WeiXinMessage.TextBean();
        textBean.setContent("有一笔新的设备租赁模块的财务流水[预付款]，请确认");
        weiXinMessage.setText(textBean);

        weiXinService.sendMessage(weiXinMessage);
        return rent.getSerialNum();
    }

    @Override
    public DeviceRent findBySerialNum(String serialNum) {
        return rentMapper.findBySerialNum(serialNum);
    }

    @Override
    public List<DeviceRentDetail> findRentDetailByRentId(Integer rentId) {
        return rentDetailMapper.findRentDetailByRentId(rentId);
    }

    @Override
    public List<DeviceRentDoc> findDeviceRentDocListByRentId(Integer id) {
        return deviceRentDocMapper.findDeviceRentDocListByRentId(id);
    }

    @Override
    public DeviceRentDoc findDocById(Integer id) {
        return deviceRentDocMapper.findDocById(id);
    }

    @Override
    public DeviceRent findDeviceRentById(Integer id) {
        return rentMapper.findDeviceRentById(id);
    }

    @Override
    public void downloadZipFile(DeviceRent rent, ZipOutputStream zipOutputStream) throws IOException {
        List<DeviceRentDoc> docList = deviceRentDocMapper.findDeviceRentDocListByRentId(rent.getId());
        if(docList.isEmpty()){
            throw new ServiceException();
        }else{
            for(DeviceRentDoc doc : docList){
                ZipEntry zipEntry = new ZipEntry(doc.getSourceName());
                zipOutputStream.putNextEntry(zipEntry);
                File file = new File(new File(filePath),doc.getNewName());
                InputStream inputStream = new FileInputStream(file);
                IOUtils.copy(inputStream,zipOutputStream);
                inputStream.close();
            }
            zipOutputStream.closeEntry();
            zipOutputStream.flush();
            zipOutputStream.close();
        }
    }

    @Override
    public List<DeviceRent> findAllDeviceRent() {
        return rentMapper.findAllDeviceRent();
    }

    @Override
    public List<DeviceRent> findAllDeviceRentBySearchParam(Map<String,Object> searchParam) {
        return rentMapper.findAllDeviceRentBySearchParam( searchParam);
    }



    @Override
    @Transactional
    public void changeRentState(Integer id) {
        //1改变合同的状态
        DeviceRent rent = rentMapper.findDeviceRentById(id);
        rent.setState("已完成");
        rentMapper.updateRent(rent);
        //2.修改device表的数量
        List<DeviceRentDetail> deviceRentDetailList = rentDetailMapper.findRentDetailByRentId(rent.getId());
        for(DeviceRentDetail rentDetail : deviceRentDetailList){
            Device device = deviceMapper.findByDeviceName(rentDetail.getDeviceName());
            device.setCurrentNum(device.getCurrentNum()+rentDetail.getNum());
            deviceMapper.update(device);

        }
        //3将尾款加进财务报表
        France france = new France();
        france.setType(France.TYPE_INCOME);
        france.setState(France.STATE_UNDONE);
        france.setCreateUser(ShiroUtil.getUserName());
        france.setRemark(France.REMARK_LAST);
        france.setMoney(rent.getLastCost());
        france.setModule(France.MODULE_RENT);
        france.setRentSerialNum(rent.getSerialNum());
        france.setSerialNum(SerialNumberUtil.getFranceSerialNum(franceMapper));
        france.setCreateDate(DateTime.now().toString("yyyy-MM-dd"));
        franceMapper.save(france);

        //4.给财务人员发送消息
        WeiXinMessage weiXinMessage = new WeiXinMessage();
        weiXinMessage.setToparty("2");
        WeiXinMessage.TextBean textBean = new WeiXinMessage.TextBean();
        textBean.setContent("有一笔新的设备租赁模块的财务流水[尾款]，请确认");
        weiXinMessage.setText(textBean);

        weiXinService.sendMessage(weiXinMessage);

    }

    @Override
    public Long countDeviceRentByType(Integer type) {
        return rentMapper.countDeviceRentByType(type);
    }


}
