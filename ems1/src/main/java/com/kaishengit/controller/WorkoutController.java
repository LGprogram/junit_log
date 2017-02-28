package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.dto.WorkerOutDto;
import com.kaishengit.exception.NotFoundException;
import com.kaishengit.pojo.DeviceRent;
import com.kaishengit.pojo.DeviceRentDoc;
import com.kaishengit.pojo.WorkOutDetail;
import com.kaishengit.pojo.Worker;
import com.kaishengit.service.DeviceService;
import com.kaishengit.service.WorkerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/2/18.
 */
@Controller
@RequestMapping("/page/workout")
public class WorkoutController {
    @Autowired
    private WorkerService workerService;
    @Autowired
    private DeviceService deviceService;

    @GetMapping("/add")
    public String workoutAdd(Model model){
        List<Worker> workerList = workerService.findAll();
        model.addAttribute("workerList",workerList);
        return "/business/work_out/add";
    }
    @PostMapping("/add/price")
    @ResponseBody
    public AjaxResult workerDetail(Integer id){
        Worker worker = workerService.findById(id);
        if(worker!=null){
            return new AjaxResult(worker);
        }else{
            return new AjaxResult(AjaxResult.ERROR,"选择的工种不存在");
        }
    }
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult workoutAdd(@RequestBody WorkerOutDto workerOutDto){
        String serialNum = workerService.saveWorkerOutDto(workerOutDto);
        return new AjaxResult(serialNum);
    }
    @GetMapping("/show/{serialNum:\\d+}")
    public String show(@PathVariable String serialNum,Model model){
        //1.查询合同对象
        DeviceRent rent = deviceService.findBySerialNum(serialNum);
        if(rent==null){
            throw new NotFoundException();
        }else{
            //2.查询外包详情
            List<WorkOutDetail> workOutDetails = workerService.findListByRentId(rent.getId());
            //3.查询合同文件详情
            List<DeviceRentDoc> docList = deviceService.findDeviceRentDocListByRentId(rent.getId());
            model.addAttribute("deviceRent",rent);
            model.addAttribute("workOutDetails",workOutDetails);
            model.addAttribute("docList",docList);
        }
        return "/business/work_out/show";
    }
    @GetMapping("/out")
    public String out(){
        return "/business/work_out/list";
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
        Integer type = DeviceRent.WORK;
        searchParam.put("type",type);
        List<DeviceRent> rentList = deviceService.findAllDeviceRentBySearchParam(searchParam);
        Long count = deviceService.countDeviceRentByType(type);



        return new DataTablesResult(draw,count,count,rentList);
    }
    @PostMapping("/in")
    @ResponseBody
    public AjaxResult in(Integer id){
        workerService.changeRentState(id);
        return new AjaxResult(AjaxResult.SUCCESS);

    }
}
