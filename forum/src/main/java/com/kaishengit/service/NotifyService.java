package com.kaishengit.service;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.kaishengit.dao.NotifyDao;
import com.kaishengit.entity.Notify;
import com.kaishengit.entity.User;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by liu on 2016/12/26.
 */
public class NotifyService {
    private NotifyDao notifyDao = new NotifyDao();
    public List<Notify> findAll(User user) {
        return notifyDao.findAll(user.getId());
    }

    public List<Notify> findUnReadNotify(User user) {
        List<Notify> topicList = findAll(user);
        //根据guava 的Collections2.filter 过滤未读消息数据
        List<Notify> unReadNotifyList = Lists.newArrayList(Collections2.filter(topicList, new Predicate<Notify>() {
            @Override
            public boolean apply(Notify notify) {
                return notify.getState()==Notify.UNREAD_STATE;
            }
        }));
        return unReadNotifyList;
    }

    public void updateByNotifyId(Integer notifyId) {
        Notify notify = notifyDao.findById(notifyId);
        notify.setState(Notify.READED_STATE);
        notify.setReadtime(new Timestamp(new Date().getTime()));
        notifyDao.update(notify);
    }
}
