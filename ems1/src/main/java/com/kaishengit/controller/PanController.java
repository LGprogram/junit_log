package com.kaishengit.controller;

import com.kaishengit.dto.AjaxResult;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.pojo.Disk;
import com.kaishengit.service.DiskService;
import com.kaishengit.shiro.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by liu on 2017/2/21.
 */
@Controller
@RequestMapping("/pan")
public class PanController {
    @Autowired
    private DiskService diskService;
    @GetMapping
    public String list(
            @RequestParam(required = false,defaultValue = "0") Integer path,
            Model model){
        List<Disk> diskList = diskService.findByFid(path);
        model.addAttribute("diskList",diskList);
        model.addAttribute("fid",path);
        return "pan/list";
    }

    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult upload(Integer fid, MultipartFile file){
       try {
           diskService.uploadFile(fid, file);
           return new AjaxResult(AjaxResult.SUCCESS);
       }catch(ServiceException e){
           e.printStackTrace();
           return new AjaxResult(AjaxResult.ERROR,e.getMessage());
       }

    }

    @PostMapping("/new")
    @ResponseBody
    public AjaxResult newFloder(Disk disk){
        disk.setCreateUser(ShiroUtil.getUserName());
        disk.setType(Disk.DIRECTORY_TYPE);
        diskService.save(disk);
        return new AjaxResult(AjaxResult.SUCCESS);
    }

    @GetMapping("/download/{id:\\d+}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> download(@PathVariable Integer id) throws FileNotFoundException {
        InputStream inputStream = diskService.findById(id);
        Disk disk = diskService.findDiskById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment",disk.getSourceName(), Charset.forName("UTF-8"));
        return new ResponseEntity<>(new InputStreamResource(inputStream),headers, HttpStatus.OK);
    }

    @GetMapping("/del")
    @ResponseBody
    public AjaxResult del(Integer id){
        diskService.deleteById(id);
        return new AjaxResult(AjaxResult.SUCCESS);
    }
}
