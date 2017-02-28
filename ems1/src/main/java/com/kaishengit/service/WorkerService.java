package com.kaishengit.service;



import com.kaishengit.dto.WorkerOutDto;
import com.kaishengit.pojo.WorkOutDetail;
import com.kaishengit.pojo.Worker;

import java.util.List;

/**
 * Created by liu on 2017/2/18.
 */
public interface WorkerService {
    List<Worker> findAll();

    Worker findById(Integer id);

    String saveWorkerOutDto(WorkerOutDto workerOutDto);

    List<WorkOutDetail> findListByRentId(Integer rentId);

    void changeRentState(Integer id);
}
