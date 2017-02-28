package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.pojo.Device;
import com.kaishengit.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 系统设置中的设备管理器
 */
@Controller
@RequestMapping("/setting/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;
    @GetMapping
    public String list(){
        return "setting/device/list";
    }
    @GetMapping("/new")
    public String save(){
        return "setting/device/new";
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
    @PostMapping("/new")
    public String save(Device device, RedirectAttributes redirectAttributes){

        deviceService.save(device);
        redirectAttributes.addFlashAttribute("message","操作成功");
        return "redirect:/setting/device";
    }
    @GetMapping("/{id:\\d+}/del")
    @ResponseBody
    public String del(@PathVariable Integer id){
        deviceService.del(id);
        return "success";
    }

}
