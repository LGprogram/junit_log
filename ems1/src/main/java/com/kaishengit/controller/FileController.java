package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.exception.NotFoundException;
import com.kaishengit.pojo.DeviceRent;
import com.kaishengit.pojo.DeviceRentDoc;
import com.kaishengit.service.DeviceService;
import com.kaishengit.service.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * Created by liu on 2017/2/17.
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private DeviceService deviceService;
    @Value("${upload.path}")
    private String pathName;

    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult upload(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        Map<String,Object> map = Maps.newHashMap();
        map.put("originalFilename",originalFilename);
        try {
            String newFileName = fileService.uploadFile(originalFilename,file.getContentType(),file.getInputStream());
            map.put("newFileName",newFileName);
            return new AjaxResult(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(AjaxResult.ERROR,"上传失败，请稍候");
        }
    }
    /**
     * 下载合同文件
     * @param id 合同文件Id
     * @param response
     * @throws IOException
     */
    @GetMapping("/out/download")
    public void download(Integer id ,HttpServletResponse response) throws IOException {
        DeviceRentDoc doc = deviceService.findDocById(id);
        if(doc==null){
            throw new NotFoundException();
        }else{
            //获取输入流

            String newName = doc.getNewName();
            File file = new File(new File(pathName),newName);
            InputStream inputStream = new FileInputStream(file);
            //设置响应流MIME格式为二进制文件
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
            String sourceName = doc.getSourceName();

            //中文转码
            sourceName=new String(sourceName.getBytes("UTF-8"),"ISO8859-1");
            //设置下载文件的名字
            response.setHeader("Content-disposition","attachment;filename=\""+sourceName+"\"");
            OutputStream outputStream = response.getOutputStream();

            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        }
    }
    /*//使用spring中的类进行文件下载
    @GetMapping("/out/download")
    @ResponseBody
    public ResponseEntity<InputStreamResource> download(Integer id ) throws IOException {
        DeviceRentDoc doc = deviceService.findDocById(id);
        if (doc == null) {
            throw new NotFoundException();
        } else {
            //获取输入流
            String newName = doc.getNewName();
            File file = new File(new File(pathName),newName);
            InputStream inputStream = new FileInputStream(file);

            String fileName = doc.getSourceName();
            //使用spring 完成文件下载
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment",fileName, Charset.forName("UTF-8"));
            return new ResponseEntity<>(new InputStreamResource(inputStream),headers, HttpStatus.OK);
        }
    }*/

    /**
     * 打包下载文件
     * @param id 合同id
     * @param response
     * @throws IOException
     */
    @GetMapping("/out/docs")
    public void downloadZipFile(Integer id,HttpServletResponse response) throws IOException {
        DeviceRent rent = deviceService.findDeviceRentById(id);
        if(rent==null){
            throw new NotFoundException();
        }else{
            //将文件下载标记为二进制
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
            //更改文件下载的名称
            String fileName = rent.getCompanyName()+".zip";
            fileName = new String(fileName.getBytes("UTF-8"),"ISO8859-1");
            response.setHeader("Content-Disposition","attachment;filename=\""+fileName+"\"");

            OutputStream outputStream = response.getOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
            deviceService.downloadZipFile(rent,zipOutputStream);
        }
    }

}

