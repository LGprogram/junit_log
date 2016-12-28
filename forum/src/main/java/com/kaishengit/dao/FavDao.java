package com.kaishengit.dao;

import com.kaishengit.entity.Fav;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by liu on 2016/12/23.
 */
public class FavDao {
    public void save(Integer topicid, Integer userid) {
        String sql ="insert into t_fav(topicid,userid) values(?,?)";
        DbHelp.update(sql,topicid,userid);
    }

    public void cancel(Integer topicid, Integer userid) {
        String sql="delete from t_fav where topicid = ? and userid = ?";
        DbHelp.update(sql,topicid,userid);
    }

    public Fav findFavByTopicIdAndUserId(Integer topicid, Integer userid) {
        String sql ="select * from t_fav where topicid = ? and userid = ?";
        return DbHelp.query(sql,new BeanHandler<Fav>(Fav.class),topicid,userid);
    }
}
