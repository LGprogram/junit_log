package com.kaishengit.mapper;

import com.kaishengit.pojo.Disk;

import java.util.List;

/**
 * Created by liu on 2017/2/21.
 */
public interface DiskMapper {
    List<Disk> findByFid(Integer path);

    void save(Disk disk);

    Disk findById(Integer id);

    void deleteById(Integer id);

    List<Disk> findAll();

    void bathDelete(List<Integer> delIdList);
}
