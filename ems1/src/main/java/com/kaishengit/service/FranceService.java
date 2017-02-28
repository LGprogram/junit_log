package com.kaishengit.service;

import com.kaishengit.dto.EchartsResult;
import com.kaishengit.dto.FranceDto;
import com.kaishengit.pojo.France;

import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/2/23.
 */
public interface FranceService {
    List<France> findByQueryParam(Map<String, Object> queryParam);

    Long count();

    Long filterCount(Map<String, Object> queryParam);

    void updateState(Integer id);

    List<France> findAllByCreateDate(String day);

    List<EchartsResult> findByDate(String date,String type);

    List<FranceDto> findByQuery(Map<String, Object> queryParam);

    List<FranceDto> findByMonth(Map<String,Object> queryParam);

    List<FranceDto> findYearReportByQuery(Map<String, Object> queryParam);

    List<FranceDto> findByYear(Map<String, Object> queryParam);
}
