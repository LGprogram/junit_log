package com.kaishengit.service.impl;


import com.google.common.collect.Lists;
import com.kaishengit.dto.DeviceRentDto;
import com.kaishengit.dto.WorkerOutDto;
import com.kaishengit.dto.wx.WeiXinMessage;
import com.kaishengit.mapper.*;
import com.kaishengit.pojo.*;
import com.kaishengit.service.WeiXinService;
import com.kaishengit.service.WorkerService;

import com.kaishengit.shiro.ShiroUtil;
import com.kaishengit.util.SerialNumberUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liu on 2017/2/18.
 */
@Service
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    private WorkerMapper workerMapper;
    @Autowired
    private DeviceRentMapper rentMapper;
    @Autowired
    private WorkOutDetailMapper workOutDetailMapper;
    @Autowired
    private DeviceRentDocMapper deviceRentDocMapper;
    @Autowired
    private FranceMapper franceMapper;
    @Autowired
    private WeiXinService weiXinService;


    @Override
    public List<Worker> findAll() {
        return workerMapper.findAll();
    }

    @Override
    public Worker findById(Integer id) {
        return workerMapper.findById(id);
    }

    @Override
    public String saveWorkerOutDto(WorkerOutDto workerOutDto) {

        //1.存储外包合同
        DeviceRent rent = new DeviceRent();
        rent.setAddress(workerOutDto.getAddress());
        rent.setBackDate(workerOutDto.getBackDate());
        rent.setCardNum(workerOutDto.getCardNum());
        rent.setCompanyName(workerOutDto.getCompanyName());
        rent.setCreateUser(ShiroUtil.getUserName());
        rent.setFax(workerOutDto.getFax());
        rent.setLastCost(0F);
        rent.setTel(workerOutDto.getTel());
        rent.setLinkMan(workerOutDto.getLinkMan());
        rent.setPreCost(0F);
        rent.setTotalPrice(0F);
        rent.setRentDate(workerOutDto.getRentDate());
        rent.setTotalDays(workerOutDto.getTotalDays());
        rent.setSerialNum(SerialNumberUtil.getSerialNumber(rentMapper));
        //确认合同类型为外包务工
        rent.setType(DeviceRent.WORK);
        rentMapper.save(rent);
        //2.存储外包详情
        List<WorkerOutDto.WorkerArrayBean> workerArray = workerOutDto.getWorkerArray();
        List<WorkOutDetail> detailList = Lists.newArrayList();
        float total = 0F;
        for(WorkerOutDto.WorkerArrayBean bean:workerArray){
            WorkOutDetail workOutDetail = new WorkOutDetail();
            workOutDetail.setRentId(rent.getId());
            workOutDetail.setRentNum(bean.getRentNum());
            workOutDetail.setReward(bean.getReward());
            workOutDetail.setTotal(bean.getTotal());
            workOutDetail.setWorkType(bean.getWorkType());
            detailList.add(workOutDetail);
            total+=workOutDetail.getTotal();
        }
        if(!detailList.isEmpty()){
            workOutDetailMapper.bathSave(detailList);
        }
        //计算合同总价及预付款、尾款
        float totalPrice = total * rent.getTotalDays();
        float preCost = totalPrice  * 0.3F;
        float lastCost = totalPrice - preCost;
        rentMapper.updateCost(totalPrice,preCost,lastCost,rent.getId());
        //3.存储合同文件
        List<WorkerOutDto.FileArrayBean> docBeanList = workerOutDto.getFileArray();
        List<DeviceRentDoc> docList = Lists.newArrayList();
        for(WorkerOutDto.FileArrayBean docBean:docBeanList){
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
        france.setModule(France.MODULE_OUT);
        france.setRentSerialNum(rent.getSerialNum());
        france.setSerialNum(SerialNumberUtil.getFranceSerialNum(franceMapper));
        france.setCreateDate(DateTime.now().toString("yyyy-MM-dd"));
        franceMapper.save(france);
        //5.调用微信企业号给财务人员发消息
        WeiXinMessage weiXinMessage = new WeiXinMessage();
        weiXinMessage.setToparty("2");
        WeiXinMessage.TextBean textBean = new WeiXinMessage.TextBean();
        textBean.setContent("有一笔新的业务外包模块的财务流水[预付款]，请确认");
        weiXinMessage.setText(textBean);

        weiXinService.sendMessage(weiXinMessage);
        return rent.getSerialNum();
    }

    @Override
    public List<WorkOutDetail> findListByRentId(Integer rentId) {
        return workOutDetailMapper.findListByRentId(rentId);
    }

    @Override
    @Transactional
    public void changeRentState(Integer id) {
        //1改变合同的状态
        DeviceRent rent = rentMapper.findDeviceRentById(id);
        rent.setState("已完成");
        rentMapper.updateRent(rent);
        //2.更新t_worker表数据
        List<WorkOutDetail> outDetails = workOutDetailMapper.findListByRentId(rent.getId());
        for(WorkOutDetail outDetail:outDetails){
            Worker worker = workerMapper.findByWorkType(outDetail.getWorkType());
            worker.setCurrentNum(worker.getCurrentNum()+outDetail.getRentNum());
            workerMapper.update(worker);
        }
        //3.写入财务流水（收回尾款）

        France france = new France();
        france.setType(France.TYPE_INCOME);
        france.setState(France.STATE_UNDONE);
        france.setCreateUser(ShiroUtil.getUserName());
        france.setRemark(France.REMARK_LAST);
        france.setMoney(rent.getLastCost());
        france.setModule(France.MODULE_OUT);
        france.setRentSerialNum(rent.getSerialNum());
        france.setSerialNum(SerialNumberUtil.getFranceSerialNum(franceMapper));
        france.setCreateDate(DateTime.now().toString("yyyy-MM-dd"));
        franceMapper.save(france);

        //调用微信企业号给财务人员发消息
        WeiXinMessage weiXinMessage = new WeiXinMessage();
        weiXinMessage.setToparty("2");
        WeiXinMessage.TextBean textBean = new WeiXinMessage.TextBean();
        textBean.setContent("有一笔新的业务外包模块的财务流水[尾款]，请确认");
        weiXinMessage.setText(textBean);

        weiXinService.sendMessage(weiXinMessage);

    }
}
