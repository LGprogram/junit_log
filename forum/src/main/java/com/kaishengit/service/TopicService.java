package com.kaishengit.service;

import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Reply;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Config;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by liu on 2016/12/20.
 */
public class TopicService {
    private TopicDao topicDao = new TopicDao();
    private UserDao userDao = new UserDao();
    private NodeDao nodeDao = new NodeDao();


    public Topic addNewTopic(String title, String content, Integer nodeid, Integer userid) {
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setNodeid(nodeid);
        topic.setUserid(userid);
        Node node = nodeDao.findById(nodeid);
        if(node!=null){
            node.setTopicnum(node.getTopicnum()+1);
            nodeDao.update(node);
            Integer id = topicDao.save(topic).intValue();
            topic.setId(id);
            return topic;
        }else{
            throw new ServiceException("发帖板块不存在或已被删除");
        }

    }

    public Topic findTopicById(String topicid) {
        if(StringUtils.isNumeric(topicid)){
            Topic topic = topicDao.findTopicById(Integer.valueOf(topicid));
            if(topic!=null){
                topic.setClicknum(topic.getClicknum()+1);
                topicDao.update(topic);
                User user = userDao.findById(topic.getUserid());
                Node node = nodeDao.findById(topic.getNodeid());
                user.setAvatar(Config.get("qiniu.domain")+user.getAvatar());
                topic.setUser(user);
                topic.setNode(node);

                return topic;
            }else{
                throw new ServiceException("该帖不存在或已被删除");
            }
        }else{
            throw new ServiceException("参数错误");
        }

    }

    public List<Reply> findReplyListByTopicId(String topicid) {
        return topicDao.findReplyListByTopicId(topicid);
    }
}
