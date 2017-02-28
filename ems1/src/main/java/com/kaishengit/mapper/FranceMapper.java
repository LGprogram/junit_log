package com.kaishengit.mapper;

import com.kaishengit.dto.EchartsResult;
import com.kaishengit.dto.FranceDto;
import com.kaishengit.pojo.France;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/2/23.
 */
public interface FranceMapper {
    void save(France france);

    List<France> findAllByContain(String date);

    List<France> findByQueryParam(Map<String, Object> queryParam);

    Long count();

    Long filterCount(Map<String, Object> queryParam);

    France findById(Integer id);

    void update(France france);

    List<France> findAllByCreateDate(String day);

    List<EchartsResult> findByDate(@Param("date")String date, @Param("type")String type);

    List<FranceDto> findByQuery(Map<String, Object> queryParam);

    List<FranceDto> findByMonth(Map<String,Object> queryParam);

    List<FranceDto> findYearReportByQuery(Map<String, Object> queryParam);

    List<FranceDto> findByYear(Map<String, Object> queryParam);
}
