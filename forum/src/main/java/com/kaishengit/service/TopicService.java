package com.kaishengit.service;

import com.kaishengit.dao.*;
import com.kaishengit.entity.*;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Config;
import com.kaishengit.util.Page;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by liu on 2016/12/20.
 */
public class TopicService {
    private TopicDao topicDao = new TopicDao();
    private UserDao userDao = new UserDao();
    private NodeDao nodeDao = new NodeDao();
    private FavDao favDao = new FavDao();
    private ThankDao thankDao = new ThankDao();


    public Topic addNewTopic(String title, String content, Integer nodeid, Integer userid) {
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setNodeid(nodeid);
        topic.setUserid(userid);
        Node node = nodeDao.findById(nodeid);
        if (node != null) {
            node.setTopicnum(node.getTopicnum() + 1);
            nodeDao.update(node);
            Integer id = topicDao.save(topic).intValue();
            topic.setId(id);
            return topic;
        } else {
            throw new ServiceException("发帖板块不存在或已被删除");
        }

    }

    public Topic findTopicById(String topicid) {
        if (StringUtils.isNumeric(topicid)) {
            Topic topic = topicDao.findTopicById(Integer.valueOf(topicid));
            if (topic != null) {
                //TODO 把点击量移走
                topic.setClicknum(topic.getClicknum() + 1);
                topicDao.update(topic);
                User user = userDao.findById(topic.getUserid());
                Node node = nodeDao.findById(topic.getNodeid());
                user.setAvatar(Config.get("qiniu.domain") + user.getAvatar());
                topic.setUser(user);
                topic.setNode(node);

                return topic;
            } else {
                throw new ServiceException("该帖不存在或已被删除");
            }
        } else {
            throw new ServiceException("参数错误");
        }

    }

    public List<Reply> findReplyListByTopicId(String topicid) {

        return topicDao.findReplyListByTopicId(topicid);
    }

    public Topic editTopic(String topicId) {
        if (StringUtils.isNumeric(topicId)) {
            Topic topic = topicDao.findTopicById(Integer.valueOf(topicId));
            if (topic != null) {
                Integer nodeid = topic.getNodeid();
                Node node = nodeDao.findById(nodeid);
                node.setTopicnum(node.getTopicnum() - 1);
                nodeDao.update(node);
                boolean booleanEdit = topic.isEdit();
                if (booleanEdit) {
                    return topic;
                } else {
                    throw new ServiceException("帖子发布时间过长或已经有人回复");
                }
            } else {
                throw new ServiceException("帖子不存在或已被删除");
            }
        } else {
            throw new ServiceException("参数错误");
        }

    }

    public Topic updateTopic(User user, String topicid, String title, String content, String nodeid) {
        if (StringUtils.isNumeric(topicid)) {
            Topic topic = topicDao.findTopicById(Integer.valueOf(topicid));
            if (topic.isEdit()) {
                topic.setTitle(title);
                topic.setContent(content);
                topic.setNodeid(Integer.valueOf(nodeid));
                topic.setCreatetime(new Timestamp(new Date().getTime()));
                topicDao.update(topic);
                Node node = nodeDao.findById(Integer.valueOf(nodeid));
                topic.setNode(node);
                topic.setUser(user);
                //修改node中的topicnum
                node.setTopicnum(node.getTopicnum() + 1);
                nodeDao.update(node);
                return topic;
            } else {
                throw new ServiceException("帖子发布时间过长或者已经有人回复");
            }
        } else {
            throw new ServiceException("参数错误");
        }

    }

    public void doFav(String action, String topicId, User user) {
        if (StringUtils.isNotEmpty(action) && StringUtils.isNumeric(topicId)) {
            if (action.equals("fav")) {
                addFav(topicId, user);
            } else {
                cancelFav(topicId, user);
            }
        } else {
            throw new ServiceException("参数错误");
        }

    }

    private void addFav(String topicId, User user) {
        favDao.save(Integer.valueOf(topicId), user.getId());

        Topic topic = topicDao.findTopicById(Integer.valueOf(topicId));
        topic.setFavnum(topic.getFavnum() + 1);
        topicDao.update(topic);
    }

    private void cancelFav(String topicId, User user) {
        favDao.cancel(Integer.valueOf(topicId), user.getId());

        Topic topic = topicDao.findTopicById(Integer.valueOf(topicId));
        topic.setFavnum(topic.getFavnum() - 1);
        topicDao.update(topic);
    }


    public Fav findFavByTopicIdAndUser(String topicid, User user) {

        return favDao.findFavByTopicIdAndUserId(Integer.valueOf(topicid), user.getId());


    }

    public void doThank(String action, String topicId, User user) {
        if (StringUtils.isNotEmpty(action) && StringUtils.isNumeric(topicId)) {
            if (action.equals("thank")) {
                addThank(topicId, user);
            } else {
                cancelThank(topicId, user);
            }
        } else {
            throw new ServiceException("参数错误");
        }
    }

    private void addThank(String topicId, User user) {
        thankDao.save(Integer.valueOf(topicId), user.getId());
        Topic topic = topicDao.findTopicById(Integer.valueOf(topicId));
        topic.setThankyounum(topic.getThankyounum() + 1);
        topicDao.update(topic);

    }

    private void cancelThank(String topicId, User user) {
        thankDao.cancel(Integer.valueOf(topicId), user.getId());
        Topic topic = topicDao.findTopicById(Integer.valueOf(topicId));
        topic.setThankyounum(topic.getThankyounum() - 1);
        topicDao.update(topic);
    }

    public Thank findThankByTopicIdAndUser(String topicid, User user) {
        return thankDao.findThankByTopicIdAndUserId(Integer.valueOf(topicid), user.getId());
    }

    public Page<Topic> findTopicListByNodeid(String nodeid, String p) {
        Integer pageNo = StringUtils.isNumeric(p) ? Integer.valueOf(p) : 1;
        if (!StringUtils.isEmpty(nodeid) && !StringUtils.isNumeric(nodeid)) {
//            throw new ServiceException("参数错误");
            nodeid = "";
        }
        Integer totals = 0;
        if (StringUtils.isEmpty(nodeid)) {
            totals = topicDao.count();
        } else {
            totals = nodeDao.findById(Integer.valueOf(nodeid)).getTopicnum();
        }
        Page<Topic> page = new Page<>(totals, pageNo);
        int start = (pageNo - 1) * page.getPageSize();

        List<Topic> items = topicDao.findTopicListByPage(nodeid, start, page.getPageSize());

        page.setItems(items);
        return page;


    }
}
