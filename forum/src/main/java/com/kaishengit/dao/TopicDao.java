package com.kaishengit.dao;

import com.kaishengit.entity.Reply;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.TopicReplyCount;
import com.kaishengit.entity.User;
import com.kaishengit.util.Config;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
        String sql = "select tu.id,tu.username,tu.avatar,tr.* from t_user as tu,t_reply as tr where tr.userid = tu.id and tr.topicid=? order by replytime";
        return DbHelp.query(sql, new AbstractListHandler<Reply>() {
            @Override
            protected Reply handleRow(ResultSet rs) throws SQLException {
                Reply reply = new BasicRowProcessor().toBean(rs,Reply.class);
                User user = new User();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString("username"));
                user.setAvatar(Config.get("qiniu.domain")+rs.getString("avatar"));
                reply.setUser(user);
                return reply;
            }
        },Integer.valueOf(topicid));
    }

    public Integer count() {
        String sql = "select count(*) from t_topic";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    public List<Topic> findTopicListByPage(String nodeid, int start, int pageSize) {
        String sql = "SELECT tu.username,tu.avatar,tt.* FROM t_user tu,t_topic tt WHERE tt.userid = tu.id ";
        List<Object> list = new ArrayList<>();
        String where = "";
        if(StringUtils.isNotEmpty(nodeid)){
            where += "AND nodeid = ? ";
            list.add(nodeid);
        }
        where +="ORDER BY tt.lastreplytime DESC LIMIT ?,?";
        list.add(start);
        list.add(pageSize);
        sql += where;
        return DbHelp.query(sql, new AbstractListHandler<Topic>() {
            @Override
            protected Topic handleRow(ResultSet rs) throws SQLException {
                Topic topic = new BasicRowProcessor().toBean(rs,Topic.class);
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setAvatar(Config.get("qiniu.domain")+rs.getString("avatar"));
                topic.setUser(user);
                return topic;
            }
        },list.toArray());
    }

    public void delTopicById(Integer id) {
        String sql = "delete from t_topic where id = ?";
        DbHelp.update(sql,id);
    }

    public Integer countTopicByDay() {
        String sql = "select count(*) from (select count(*) from t_topic group by DATE_FORMAT(createtime,'%y-%m-%d')) as topicCount";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    public List<TopicReplyCount> findTopicReplyCountList(Integer start, int pageSize) {
        String sql = "SELECT COUNT(*) topicnum,DATE_FORMAT(createtime,'%y-%m-%d') 'time',(SELECT COUNT(*) FROM t_reply\n" +
                " WHERE DATE_FORMAT(replytime,'%y-%m-%d') = DATE_FORMAT(t_topic.createtime,'%y-%m-%d')) 'replynum' FROM t_topic\n" +
                "  GROUP BY (DATE_FORMAT(createtime,'%y-%m-%d'))\n" +
                "  ORDER BY (DATE_FORMAT(createtime,'%y-%m-%d')) DESC LIMIT ?,?";
        return DbHelp.query(sql,new BeanListHandler<TopicReplyCount>(TopicReplyCount.class),start,pageSize);
    }
}
