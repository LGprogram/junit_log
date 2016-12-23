package com.kaishengit.dao;

import com.kaishengit.entity.Reply;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.util.Config;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by liu on 2016/12/20.
 */
public class TopicDao {
    public Topic findTopicById(Integer id) {
        String sql ="select * from t_topic where id=?";
        return DbHelp.query(sql,new BeanHandler<>(Topic.class),id);
    }

    public Long save(Topic topic) {
        String sql = "insert into t_topic(title,content,nodeid,userid) values(?,?,?,?) ";
        return DbHelp.insert(sql,new ScalarHandler<Long>(),topic.getTitle(),topic.getContent(),topic.getNodeid(),topic.getUserid());
    }

    public void update(Topic topic) {
        String sql = "update t_topic set title=?,content=?,createtime=?,clicknum=?,favnum=?,thankyounum=?,replynum=?,lastreplytime=?,nodeid=?,userid=? where id=?";
        DbHelp.update(sql,topic.getTitle(),topic.getContent(),topic.getCreatetime(),topic.getClicknum(),topic.getFavnum(),topic.getThankyounum(),topic.getReplynum(),topic.getLastreplytime(),topic.getNodeid(),topic.getUserid(),topic.getId());
    }

    public List<Reply> findReplyListByTopicId(String topicid) {
        String sql = "select tu.id,tu.username,tu.avatar,tr.* from t_user as tu,t_reply as tr where tr.userid = tu.id and tr.topicid=?";
        return DbHelp.query(sql, new AbstractListHandler<Reply>() {
            @Override
            protected Reply handleRow(ResultSet rs) throws SQLException {
                Reply reply = new BasicRowProcessor().toBean(rs,Reply.class);
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setAvatar(Config.get("qiniu.domain")+rs.getString("avatar"));
                reply.setUser(user);
                return reply;
            }
        },Integer.valueOf(topicid));
    }
}
