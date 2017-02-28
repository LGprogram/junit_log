package com.kaishengit.service.impl;

import com.google.common.collect.Lists;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.DiskMapper;
import com.kaishengit.pojo.Disk;
import com.kaishengit.service.DiskService;
import com.kaishengit.shiro.ShiroUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * Created by liu on 2017/2/21.
 */
@Service
public class DiskServiceImpl implements DiskService {
    @Autowired
    private DiskMapper diskMapper;
    @Value("${upload.path}")
    private String filePath;
    @Override
    public List<Disk> findByFid(Integer path) {
        return diskMapper.findByFid(path);
    }

    @Override
    public void uploadFile(Integer fid, MultipartFile file) {

        String sourceName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString();
        if(sourceName.lastIndexOf(".")!=-1){
            newName += sourceName.substring(sourceName.lastIndexOf("."));
        }
        Long size = file.getSize();
        //1.保存文件到磁盘
        try{
            File saveFile = new File(new File(filePath),newName);
            InputStream inputStream = file.getInputStream();
            OutputStream outputStream = new FileOutputStream(saveFile);
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }catch (IOException e){
            throw new ServiceException("文件保存到磁盘异常",e);
        }
        //2.写入数据库
        Disk disk = new Disk();
        disk.setFid(fid);
        disk.setNewName(newName);
        disk.setSourceName(sourceName);
        disk.setSize(FileUtils.byteCountToDisplaySize(size));
        disk.setCreateUser(ShiroUtil.getUserName());
        disk.setType(Disk.FILE_TYPE);
        diskMapper.save(disk);
    }

    @Override
    public void save(Disk disk) {
        diskMapper.save(disk);
    }

    @Override
    public InputStream findById(Integer id) throws FileNotFoundException {
        Disk disk = diskMapper.findById(id);
        String newName = disk.getNewName();
        File file = new File(new File(filePath),newName);
        InputStream inputStream = new FileInputStream(file);
        return inputStream ;
    }

    @Override
    public Disk findDiskById(Integer id) {
        return diskMapper.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        Disk disk = diskMapper.findById(id);
        if(disk.getType().equals(Disk.FILE_TYPE)){
            //1.删除文件
            File file = new File(new File(filePath),disk.getNewName());
            file.delete();
            //2.删除数据库中记录
            diskMapper.deleteById(disk.getId());
        }else{
            List<Disk> diskList = diskMapper.findAll();
            List<Integer> delIdList = Lists.newArrayList();
            folderDelete(diskList,delIdList,id);
            delIdList.add(id);
            diskMapper.bathDelete(delIdList);
        }
    }

    private void folderDelete(List<Disk> diskList, List<Integer> delIdList, Integer id) {
        for(Disk disk : diskList){
            if(disk.getFid().equals(id)){
                delIdList.add(disk.getId());
                if(disk.getType().equals(Disk.DIRECTORY_TYPE)){
                    folderDelete(diskList,delIdList,disk.getId());
                }else{
                    File file = new File(new File(filePath),disk.getNewName());
                    file.delete();
                }
            }
        }
    }
}
