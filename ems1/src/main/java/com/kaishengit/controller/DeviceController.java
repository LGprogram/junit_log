package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.dto.DeviceRentDto;
import com.kaishengit.exception.NotFoundException;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.pojo.*;
import com.kaishengit.service.DeviceOutService;
import com.kaishengit.service.DeviceService;
import com.kaishengit.shiro.ShiroUtil;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

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
    @Value("${upload.path}")
    private String pathName;
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
    public String out(){
        return "/business/device_out/list";
    }
    @PostMapping("/out/load")
    @ResponseBody
    public DataTablesResult outLoad(HttpServletRequest request){
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");

        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("start",start);
        searchParam.put("length",length);
        Integer type =DeviceRent.RENT;
        searchParam.put("type",type);
        List<DeviceRent> rentList = deviceService.findAllDeviceRentBySearchParam(searchParam);
        Long count = deviceService.countDeviceRentByType(type);


        return new DataTablesResult(draw,count,count,rentList);
    }

    /**
     * 新增流水
     * @param model
     * @return
     */
    @GetMapping("/out/add")
    public String outAdd(Model model){
        List<Device> deviceList = deviceService.findAll();
        model.addAttribute("deviceList",deviceList);
        return "/business/device_out/add";
    }

    /**
     * 根据设备Id查询设备详情
     * @param id
     * @return
     */
    @PostMapping("/out/add/price")
    @ResponseBody
    public AjaxResult getPrice(Integer id){
        Device device= deviceService.findById(id);
        if(device!=null){
            return new AjaxResult(device);
        }else{
            return new AjaxResult(AjaxResult.ERROR,"所选的设备不存在");
        }

    }

    @PostMapping("/out/add")
    @ResponseBody
    public AjaxResult outAdd(@RequestBody DeviceRentDto deviceRentDto){
        try{
        String serialNum = deviceService.saveDeviceRentDto(deviceRentDto);
        return new AjaxResult(serialNum);
        }catch(ServiceException e){
            return new AjaxResult(AjaxResult.ERROR,e.getMessage());
        }
    }

    /**
     * 根据流水号查询合同详情
     * @param serialNum
     * @param model
     * @return
     */
    @GetMapping("/out/{serialNum:\\d+}")
    public String print(@PathVariable String serialNum,Model model){
        //1.查询合同对象
        DeviceRent rent = deviceService.findBySerialNum(serialNum);
        if(rent==null){
            throw new NotFoundException();
        }else{
            //2.查询设备列表
            List<DeviceRentDetail> deviceRentDetailList = deviceService.findRentDetailByRentId(rent.getId());
            System.out.println(deviceRentDetailList.size());
            //3.查询合同文件列表
            List<DeviceRentDoc> docList = deviceService.findDeviceRentDocListByRentId(rent.getId());
            model.addAttribute("deviceRent",rent);
            model.addAttribute("deviceRentDetailList",deviceRentDetailList);
            model.addAttribute("docList",docList);
            return "/business/device_out/show";
        }



    }



    @PostMapping("/in")
    @ResponseBody
    public AjaxResult in(Integer id){
        deviceService.changeRentState(id);
        return new AjaxResult(AjaxResult.SUCCESS);

    }

}
