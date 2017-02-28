package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.dto.EchartsResult;
import com.kaishengit.dto.FranceDto;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.pojo.France;
import com.kaishengit.service.FranceService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/2/23.
 */
@Controller
@RequestMapping("/page/france")
public class FranceController {
    @Autowired
    private FranceService franceService;

    @GetMapping("/day")
    public String readDayReport(){
        return "france/dayReport";
    }

    @PostMapping("/day/load")
    @ResponseBody
    public DataTablesResult ajaxLoad(HttpServletRequest request){
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String day = request.getParameter("day");

        Map<String,Object> queryParam = Maps.newHashMap();
        queryParam.put("start",start);
        queryParam.put("length",length);
        queryParam.put("day",day);
        List<France> franceList = franceService.findByQueryParam(queryParam);
        Long count = franceService.count();
        Long filterCount = franceService.filterCount(queryParam);
        return new DataTablesResult(draw,count,filterCount,franceList);
    }
    @PostMapping("/income")
    @ResponseBody
    public AjaxResult income(Integer id){
        try {
            franceService.updateState(id);
            return new AjaxResult(AjaxResult.SUCCESS);
        }catch(ServiceException e){
            return new AjaxResult(AjaxResult.ERROR,e.getMessage());
        }
    }

    @GetMapping("/day/{day}/data.xls")
    public void exportExcel(@PathVariable String day, HttpServletResponse response) throws IOException {
        List<France> franceList = franceService.findAllByCreateDate(day);

        response.setContentType("application/vnd.ms-excel");

        response.setHeader("Content-Disposition","attachment;filename=\""+day+".xls\"");
        //建Excel表格
        Workbook workbook = new HSSFWorkbook();
        //在表中建分页
        Sheet sheet = workbook.createSheet(day+"财务报表");
        //在分页中建行
        Row row = sheet.createRow(0);
        //在行中建一个个的单元格作为表头
        row.createCell(0).setCellValue("流水号");
        row.createCell(1).setCellValue("创建日期");
        row.createCell(2).setCellValue("类型");
        row.createCell(3).setCellValue("金额");
        row.createCell(4).setCellValue("业务模块");
        row.createCell(5).setCellValue("业务流水");
        row.createCell(6).setCellValue("状态");
        row.createCell(7).setCellValue("备注");
        row.createCell(8).setCellValue("创建人");
        row.createCell(9).setCellValue("确认人");
        row.createCell(10).setCellValue("确认时间");
        //建数据行
        for(int i= 0; i<franceList.size();i++){
            France france = franceList.get(i);
            Row datarow = sheet.createRow(i+1);
            datarow.createCell(0).setCellValue(france.getSerialNum());
            datarow.createCell(1).setCellValue(france.getCreateDate());
            datarow.createCell(2).setCellValue(france.getType());
            datarow.createCell(3).setCellValue(france.getMoney());
            datarow.createCell(4).setCellValue(france.getModule());
            datarow.createCell(5).setCellValue(france.getRentSerialNum());
            datarow.createCell(6).setCellValue(france.getState());
            datarow.createCell(7).setCellValue(france.getRemark());
            datarow.createCell(8).setCellValue(france.getCreateUser());
            datarow.createCell(9).setCellValue(france.getConfimUser());
            datarow.createCell(10).setCellValue(france.getConfimDate());
        }
        //设置列宽自适应
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(10);
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();


    }

    @PostMapping("/echarts/in/load")
    @ResponseBody
    public AjaxResult load(String date){
        try{
            String type = France.TYPE_INCOME;
            List<EchartsResult> list = franceService.findByDate(date,type);
            return new AjaxResult(list);
        }catch (ServiceException e){
            return new AjaxResult(AjaxResult.ERROR,e.getMessage());
        }


    }

    @GetMapping("/month")
    public String readMonthReport(){
        return "france/monthReport";
    }

    @PostMapping("/month/load")
    @ResponseBody
    public DataTablesResult readMonthReport(HttpServletRequest request){
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String month = request.getParameter("month");

        Map<String,Object> queryParam = Maps.newHashMap();
        queryParam.put("start",start);
        queryParam.put("length",length);
        queryParam.put("month",month);

        List<FranceDto> franceDtoList = franceService.findByQuery(queryParam);
        Long count = Long.valueOf(franceDtoList.size());
        return new DataTablesResult(draw,count,count, franceDtoList);

    }

    @GetMapping("/day/{date}/detail")
    public String getDayDetail(@PathVariable String date , RedirectAttributes redirectAttributes){
        System.out.println(date);
        redirectAttributes.addAttribute("date",date);
        return "redirect:/page/france/day";
    }

    @PostMapping("/echarts/month/load")
    @ResponseBody
    public AjaxResult monthLoad(String month){
        Map<String,Object> queryParam = Maps.newHashMap();
        queryParam.put("month",month);
        try {
            List<FranceDto> franceDtoList = franceService.findByMonth(queryParam);
            return new AjaxResult(franceDtoList);
        }catch (ServiceException e){
            return new AjaxResult(AjaxResult.ERROR,e.getMessage());
        }

    }

    @GetMapping("/year")
    public String readYearReport(){
        return "france/yearReport";
    }
    @PostMapping("/year/load")
    @ResponseBody
    public DataTablesResult readYearReport(HttpServletRequest request){
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String year = request.getParameter("year");

        Map<String,Object> queryParam = Maps.newHashMap();
        queryParam.put("start",start);
        queryParam.put("length",length);
        queryParam.put("year",year);

        List<FranceDto> franceDtoList = franceService.findYearReportByQuery(queryParam);
        Long count = Long.valueOf(franceDtoList.size());
        return new DataTablesResult(draw,count,count, franceDtoList);

    }

    @PostMapping("/echarts/year/load")
    @ResponseBody
    public AjaxResult yearLoad(String year){
        Map<String,Object> queryParam = Maps.newHashMap();
        queryParam.put("year",year);
        try {
            List<FranceDto> franceDtoList = franceService.findByYear(queryParam);
            return new AjaxResult(franceDtoList);
        }catch (ServiceException e){
            return new AjaxResult(AjaxResult.ERROR,e.getMessage());
        }

    }

    @GetMapping("/month/{month}/detail")
    public String getMonthDetail(@PathVariable String month , RedirectAttributes redirectAttributes){
        System.out.println(month);
        redirectAttributes.addAttribute("month",month);
        return "redirect:/page/france/month";
    }
}
