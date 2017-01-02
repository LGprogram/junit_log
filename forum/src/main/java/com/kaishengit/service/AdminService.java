package com.kaishengit.service;

import com.kaishengit.dao.AdminDao;
import com.kaishengit.entity.Admin;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Config;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liu on 2016/12/28.
 */
public class AdminService {
    private AdminDao adminDao = new AdminDao();
    Logger logger = LoggerFactory.getLogger(AdminService.class);
    public Admin login(String adminname, String password, String ip) {
        Admin admin =adminDao.findByName(adminname);
        if(admin!=null&&admin.getPassword().equals(DigestUtils.md5Hex(Config.get("password.salt")+password))){
            logger.debug("管理员{}使用IP：{}登录",adminname,ip);
            return admin;
        }else{
            throw new ServiceException("账号或密码错误");
        }
    }
}
