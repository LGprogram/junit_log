package com.kaishengit.service;

import com.kaishengit.dao.NotifyDao;
import com.kaishengit.dao.ReplyDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.Notify;
import com.kaishengit.entity.Reply;
import com.kaishengit.entity.Topic;
import com.kaishengit.exception.ServiceException;
import java.sql.Timestamp;
import java.util.Date;


/**
 * Created by liu on 2016/12/21.
 */
public class ReplyService {
   private ReplyDao replyDao = new ReplyDao();
   private UserDao  userDao = new UserDao();
   private TopicDao topicDao =new TopicDao();
   private NotifyDao notifyDao = new NotifyDao();
    public void addNewReply(String content, String topicid, Integer userid) {
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setUserid(userid);
        reply.setTopicid(Integer.valueOf(topicid));
        reply.setReplytime(new Timestamp(new Date().getTime()));

        replyDao.save(reply);

        Topic topic = topicDao.findTopicById(Integer.valueOf(topicid));
        if(topic!=null){
            topic.setLastreplytime(reply.getReplytime());
            topic.setReplynum(topic.getReplynum()+1);
            topicDao.update(topic);
            //给Topic的楼主发消息（notify）
            if(!userid.equals(topic.getUserid())) {
                Notify notify  = new Notify();
                notify.setUserid(topic.getUserid());
                notify.setContent("您发布的主题<a href='/topicDetail?topicid="+topicid+"'>"+topic.getTitle()+"</a>有了新的回复");
                notify.setCreatetime(new Timestamp(new Date().getTime()));
                notifyDao.save(notify);

            }

        }else{
            throw new ServiceException("帖子找不到或已被删除");
        }

    }


}
