package com.kaishengit.dao;

import com.kaishengit.entity.Notify;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by liu on 2016/12/26.
 */
public class NotifyDao {

    public List<Notify> findAll(Integer userid) {
        String sql = "select * from t_notify where userid = ? order by state,createtime desc";
        return DbHelp.query(sql,new BeanListHandler<Notify>(Notify.class),userid);

    }

    public void save(Notify notify) {
        String sql = "insert into t_notify(userid,content,createtime) values(?,?,?)";
        DbHelp.update(sql,notify.getUserid(),notify.getContent(),notify.getCreatetime());

    }

    public Notify findById(Integer notifyId) {
        String sql = "select * from t_notify where id = ?";
        return DbHelp.query(sql,new BeanHandler<Notify>(Notify.class),notifyId);
    }

    public void update(Notify notify) {
        String sql="update t_notify set state = ?,readtime = ? where id = ?";
        DbHelp.update(sql,notify.getState(),notify.getReadtime(),notify.getId());
    }
}
