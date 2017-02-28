package com.kaishengit.mapper;





import com.kaishengit.pojo.Worker;

import java.util.List;

/**
 * Created by liu on 2017/2/18.
 */
public interface WorkerMapper {
    List<Worker> findAll();

    Worker findById(Integer id);

    Worker findByWorkType(String workType);

    void update(Worker worker);
}
