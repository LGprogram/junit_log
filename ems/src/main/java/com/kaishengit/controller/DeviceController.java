package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.pojo.Device;
import com.kaishengit.pojo.DeviceOut;
import com.kaishengit.service.DeviceOutService;
import com.kaishengit.service.DeviceService;
import com.kaishengit.shiro.ShiroUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/2/9.
 */
@Controller
@RequestMapping("/page/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceOutService deviceOutService;
    @GetMapping
    public String list(){
        return "/business/device_manager/current";
    }
    @GetMapping("/new")
    public String save(){
        return "business/device_manager/new";
    }

    @PostMapping("/new")
    public String save(Device device, RedirectAttributes redirectAttributes){
        Integer createUserId = ShiroUtil.getUserId();
        device.setCreateUserId(createUserId);
        deviceService.save(device);

        redirectAttributes.addFlashAttribute("message","操作成功");
        return "redirect:/page/device";
    }
    @PostMapping("/load")
    @ResponseBody
    public Map<String,Object> load(HttpServletRequest request){
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String orderIndex = request.getParameter("order[0][column]");
        String orderType = request.getParameter("order[0][dir]");
        String orderColumn = request.getParameter("columns["+orderIndex+"][name]");
        String deviceName = request.getParameter("deviceName");


        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("start",start);
        searchParam.put("length",length);
        searchParam.put("orderColumn",orderColumn);
        searchParam.put("orderType",orderType);
        searchParam.put("deviceName",deviceName);
        List<Device> deviceList = deviceService.findAllBySearchParam(searchParam);
        Long count = deviceService.count();
        Long filterCount = deviceService.filterCount(searchParam);

        Map<String,Object> resultMap = Maps.newHashMap();
        resultMap.put("draw",draw);
        resultMap.put("data",deviceList);
        resultMap.put("recordsTotal",count);//总记录数
        resultMap.put("recordsFiltered",filterCount) ;//过滤后总记录数

        return resultMap;
    }
    @GetMapping("/{id:\\d+}/del")
    @ResponseBody
    public String del(@PathVariable Integer id){
        deviceService.del(id);
        return "success";
    }
    @GetMapping("/out")
    public String out(Model model){
        List<DeviceOut> deviceOuts = deviceOutService.findAll();
        model.addAttribute("deviceOuts",deviceOuts);
        return "/business/device_out/out";
    }
    @GetMapping("/out/add")
    public String outAdd(Model model){
        List<Device> deviceList = deviceService.findAll();
        model.addAttribute("deviceList",deviceList);
        return "/business/device_out/add";
    }

    @PostMapping("/out/add/price")
    @ResponseBody
    public Device getPrice(Integer id){
        return deviceService.findById(id);
    }
    @PostMapping("/out/add/number")
    @ResponseBody
    public Integer getPrice(String number,Integer id){
        Device device = deviceService.findById(id);
        Integer currentNum = device.getCurrentNum();

        return (currentNum-Integer.valueOf(number));
    }
    @PostMapping("/out/add")
    @Transactional
    public String outAdd(Model model, DeviceOut deviceOut){
        /*生成业务流水号，当并发添加时注意加锁*/
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String date= df.format(new Date());
        date = date.substring(2,date.length());
        System.out.println(date);
        List<DeviceOut> deviceOutList =  deviceOutService.findAllContain(date);
        if(deviceOutList.isEmpty()){
            String serialNum = date+"0001";
            deviceOut.setSerialNum(serialNum);
        }else{
            Integer old = Integer.valueOf(deviceOutList.get(deviceOutList.size()-1).getSerialNum());
            String serialNum = Integer.toString(old+1);
            deviceOut.setSerialNum(serialNum);
        }
        Integer userId = ShiroUtil.getUserId();
        Integer id = deviceOut.getDeviceId();
        Device device = deviceService.findById(id);
        String deviceName = device.getName();
        deviceOut.setDeviceName(deviceName);
        deviceOut.setUserId(userId);
        deviceOut.setDamagePrice(device.getDamagePrice());
        deviceOut.setMissPrice(device.getMissPrice());
        deviceOutService.save(deviceOut);

        Integer number = deviceOut.getNumber();
        device.setCurrentNum(device.getCurrentNum()-number);
        device.setEditUserId(ShiroUtil.getUserId());
        device.setLasteditTime(new Timestamp(DateTime.now().getMillis()));
        deviceService.update(device);
        List<DeviceOut> deviceOuts = deviceOutService.findAll();
        model.addAttribute("deviceOuts",deviceOuts);
        return "/business/device_out/out";
    }
    @GetMapping("/out/{id:\\d+}/in")
    public String outIn(@PathVariable Integer id, Model model){
        DeviceOut deviceOut = deviceOutService.findById(id);
        model.addAttribute("deviceOut",deviceOut);
        return "/business/device_out/in";
    }
    @PostMapping("/out/{id:\\d+}/in")
    @Transactional
    public String outIn(@PathVariable Integer id,HttpServletRequest request){
        DeviceOut deviceOut = deviceOutService.findById(id);
        String repayNum =  request.getParameter("repayNum");
        String deviceId = request.getParameter("deviceId");
        String damageMoney =  request.getParameter("damageMoney");
        String totalMoney = request.getParameter("totalMoney");
        deviceOut.setState(DeviceOut.DONE);
        deviceOut.setDeditMoney(Float.valueOf(damageMoney));
        deviceOut.setRepaytimeActual(new Timestamp(DateTime.now().getMillis()));
        deviceOutService.update(deviceOut);
        Device device = deviceService.findById(Integer.valueOf(deviceId));
        device.setCurrentNum(device.getCurrentNum()+Integer.valueOf(repayNum));
        device.setLasteditTime(new Timestamp(DateTime.now().getMillis()));
        device.setEditUserId(ShiroUtil.getUserId());
        deviceService.update(device);
        return "redirect:/page/device/out";
    }
}
