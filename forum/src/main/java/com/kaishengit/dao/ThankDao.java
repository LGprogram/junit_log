package com.kaishengit.dao;

import com.kaishengit.entity.Thank;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by liu on 2016/12/23.
 */
public class ThankDao {
    public void save(Integer topicId, Integer userid) {
        String sql ="insert into t_thankyou(topicid,userid) values(?,?)";
        DbHelp.update(sql,topicId,userid);
    }

    public void cancel(Integer topicId, Integer userid) {
        String sql = "delete from t_thankyou where topicid=? and userid=?";
        DbHelp.update(sql,topicId,userid);
    }

    public Thank findThankByTopicIdAndUserId(Integer topicid, Integer userid) {
        String sql = "select * from t_thankyou where topicid = ? and userid = ?";
        return DbHelp.query(sql,new BeanHandler<Thank>(Thank.class),topicid,userid);

    }
}
