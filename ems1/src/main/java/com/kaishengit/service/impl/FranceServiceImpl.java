package com.kaishengit.service.impl;

import com.kaishengit.dto.EchartsResult;
import com.kaishengit.dto.FranceDto;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.FranceMapper;
import com.kaishengit.pojo.France;
import com.kaishengit.service.FranceService;
import com.kaishengit.shiro.ShiroUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/2/23.
 */
@Service
public class FranceServiceImpl implements FranceService {
    @Autowired
    private FranceMapper franceMapper;
    @Override
    public List<France> findByQueryParam(Map<String, Object> queryParam) {
        return franceMapper.findByQueryParam(queryParam);
    }

    @Override
    public Long count() {
        return franceMapper.count();
    }

    @Override
    public Long filterCount(Map<String, Object> queryParam) {
        return franceMapper.filterCount(queryParam);
    }

    @Override
    public void updateState(Integer id) {
        France france = franceMapper.findById(id);
        if(france==null){
            throw new ServiceException("找不到此记录");
        }else{
            france.setState(France.STATE_DONE);
            france.setConfimUser(ShiroUtil.getUserName());
            france.setConfimDate(DateTime.now().toString("yyyy-MM-dd"));
            franceMapper.update(france);
        }
    }

    @Override
    public List<France> findAllByCreateDate(String day) {
        return franceMapper.findAllByCreateDate(day);
    }

    @Override
    public List<EchartsResult> findByDate(String date,String type) {
        List<EchartsResult> list = franceMapper.findByDate(date,type);
        if(list.isEmpty()){
            throw new ServiceException("此日没有财务流水");
        }else{
            return list;
        }

    }

    @Override
    public List<FranceDto> findByQuery(Map<String, Object> queryParam) {
        return franceMapper.findByQuery(queryParam);
    }

    @Override
    public List<FranceDto> findByMonth(Map<String,Object> queryParam) {
        List<FranceDto> list =franceMapper.findByMonth(queryParam);
        if(list.isEmpty()){
            throw new ServiceException("此月没有财务流水");
        }else{
            return list;
        }
    }

    @Override
    public List<FranceDto> findYearReportByQuery(Map<String, Object> queryParam) {
        return franceMapper.findYearReportByQuery(queryParam);
    }

    @Override
    public List<FranceDto> findByYear(Map<String, Object> queryParam) {
        return franceMapper.findByYear(queryParam);
    }
}
