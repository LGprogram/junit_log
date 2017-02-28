package com.kaishengit.service;

import com.kaishengit.pojo.Disk;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by liu on 2017/2/21.
 */
public interface DiskService {
    List<Disk> findByFid(Integer path);

    void uploadFile(Integer fid, MultipartFile file);

    void save(Disk disk);

    InputStream findById(Integer id) throws FileNotFoundException;

    Disk findDiskById(Integer id);

    void deleteById(Integer id);
}
