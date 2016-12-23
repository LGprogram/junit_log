package com.kaishengit.dao;

import com.kaishengit.entity.Reply;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by liu on 2016/12/21.
 */
public class ReplyDao {

    public void save(Reply reply) {
        String sql = "insert into t_reply(content,topicid,userid) values(?,?,?)";
        DbHelp.update(sql,reply.getContent(),reply.getTopicid(),reply.getUserid());
    }


}
