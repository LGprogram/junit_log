package com.kaishengit.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kaishengit.dao.Login_LogDao;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.Login_Log;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Config;
import com.kaishengit.util.EmailUtil;
import com.kaishengit.util.Page;
import com.kaishengit.vo.UserVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by liu on 2016/12/15.
 */
public class UserService {
    private UserDao userDao = new UserDao();
    private Login_LogDao login_logDao = new Login_LogDao();

    private Logger logger = LoggerFactory.getLogger(UserService.class);
    //发送激活账号的TOKEN缓存：注册或者修改邮箱时诱发
    private static Cache<String, String> cache = CacheBuilder.newBuilder().expireAfterWrite(6, TimeUnit.HOURS).build();

    //找回密码提交频率缓存
    private static Cache<String, String> submitCache = CacheBuilder.newBuilder().expireAfterWrite(60, TimeUnit.SECONDS).build();
    //发送邮件找回密码的token缓存
    private static Cache<String, String> foundPwdCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build();

    public boolean validateUsername(String username) {
        String name = Config.get("no.signup.usernames");
        List<String> nameList = Arrays.asList(name.split(","));
        if (nameList.contains(username)) {
            return false;
        }
        return userDao.findByUsername(username) == null;
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public void save(String username, String password, String email, String phone) {
        User user = new User();
        user.setUsername(username);
        user.setAvatar(User.DEFAULT_AVATAR_NAME);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setState(User.USERSTATE_UNACTIVE);

        userDao.save(user);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String subject = "注册验证";
                String uuid = UUID.randomUUID().toString();
                String url = "http://localhost/user/active?_=" + uuid;
                String html = "<h3>Dear " + username + ":</h3>请点击<a href='" + url + "'>该链接</a>去激活你的账号. <br> 凯盛软件";
                //放入缓存等待6个小时
                cache.put(uuid, username);

                EmailUtil.sendHttpemail(html, subject, email);
            }
        });
        thread.start();

    }

    public void findUserByToken(String token) {
        String username = cache.getIfPresent(token);
        if (StringUtils.isNotEmpty(username)) {
            User user = userDao.findByUsername(username);
            if (user != null) {
                user.setState(user.USERSTATE_ACTIVE);
                userDao.update(user);
                logger.info("用户{}激活了账号", user.getUsername());
                cache.invalidate(token);
            } else {
                throw new ServiceException("无法找到对应的账号");
            }
        } else {
            throw new ServiceException("token超时或错误");
        }

    }

    public User login(String username, String password, String ip) {
        User user = userDao.findByUsername(username);
        /*Integer loginLimitTimes = 3;
        if(loginLimitTimes > 0){}else{}*/
        //TODO 密码输错3次弹出验证码

        if (user != null && user.getPassword().equals(DigestUtils.md5Hex(password + Config.get("password.salt")))) {
            if (user.getState().equals(user.USERSTATE_ACTIVE)) {
                login_logDao.save(ip, user.getId());
                logger.info("用户{}登录", user.getUsername());
                return user;
            } else if (user.getState().equals(user.USERSTATE_UNACTIVE)) {
                throw new ServiceException("账号未激活");
            } else {
                throw new ServiceException("账号已被禁用");
            }
        } else {
            throw new ServiceException("账号或密码错误");
        }
    }

    public void foundPassword(String type, String value, String sessionId) {

        if (type.equals("email")) {
            User user = userDao.findByEmail(value);
            if (user != null) {
                if (submitCache.getIfPresent(sessionId) == null) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String subject = "找回密码";
                            String uuid = UUID.randomUUID().toString();
                            String url = "http://localhost/user/foundPwd?token=" + uuid;
                            String html = "<h3>Dear " + user.getUsername() + ":</h3>请点击<a href='" + url + "'>该链接</a>重置密码。<br><p style='font-size:10px'>若非本人操作，请忽略</p><br> 凯盛软件";
                            //放入缓存等待6个小时
                            foundPwdCache.put(uuid, user.getUsername());


                            EmailUtil.sendHttpemail(html, subject, value);
                        }
                    });
                    thread.start();
                    //既能证明是同一个客户端的操作，又能用来防止重复提交过快
                    submitCache.put(sessionId, "xxx");
                } else {
                    throw new ServiceException("操作频率过快");
                }
            } else {
                throw new ServiceException("请输入正确的邮箱");
            }
        } else {
            //TODO 手机验证
        }
    }

    /**
     * token->username->user
     *
     * @param token
     * @return
     */
    public User validateToken(String token) {
        String username = foundPwdCache.getIfPresent(token);
        if (StringUtils.isNotEmpty(username)) {
            User user = userDao.findByUsername(username);
            return user;
        } else {
            throw new ServiceException("token超时");
        }


    }

    /**
     * 重置用户的密码
     *
     * @param id       用户ID
     * @param token    找回密码的TOken
     * @param password 新密码
     */
    public void resetPassword(String id, String token, String password) {
        if (foundPwdCache.getIfPresent(token) == null) {
            throw new ServiceException("token过期或错误");
        } else {
            User user = userDao.findById(Integer.valueOf(id));

            user.setPassword(DigestUtils.md5Hex(password + Config.get("password.salt")));
            userDao.update(user);
            logger.info("{} 重置了密码", user.getUsername());
        }
        //修改完密码后将强制缓存失效
        foundPwdCache.invalidate(token);

    }

    public void update(User user, String email) {
        user.setEmail(email);
        user.setState(User.USERSTATE_UNACTIVE);
        userDao.update(user);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String subject = "修改邮箱验证";
                String uuid = UUID.randomUUID().toString();
                String url = "http://localhost/user/active?_=" + uuid;
                String html = "<h3>Dear " + user.getUsername() + ":</h3>请点击<a href='" + url + "'>该链接</a>去激活你的账号. <br> 凯盛软件";
                //放入缓存等待6个小时
                cache.put(uuid, user.getUsername());

                EmailUtil.sendHttpemail(html, subject, email);
            }
        });
        thread.start();
    }

    public void updatePassword(User user, String oldpassword, String newpassword) {
        System.out.println(user.getPassword());
        System.out.println(DigestUtils.md5Hex(oldpassword + Config.get("password.salt")));

        if (user.getPassword().equals(DigestUtils.md5Hex(oldpassword + Config.get("password.salt")))) {
            user.setPassword(DigestUtils.md5Hex(newpassword + Config.get("password.salt")));
            String avatar = user.getAvatar();//带有七牛云的四级域名
            //更新数据库时需要将avatar字符串中的四级域名去掉
            avatar = avatar.replace(Config.get("qiniu.domain"), "");
            user.setAvatar(avatar);
            userDao.update(user);
        } else {
            throw new ServiceException("原始密码错误");
        }
    }

    public void updateUserAvatar(User user, String filekey) {
        user.setAvatar(filekey);
        userDao.update(user);
        //将session中保存的user的avatar属性改为http://oi0ntmwcf.bkt.clouddn.com/+filekey
        user.setAvatar(Config.get("qiniu.domain") + user.getAvatar());
    }

    public Page<UserVo> findByPageNo(String p) {
        Integer pageNo = StringUtils.isNumeric(p) ? Integer.valueOf(p) : 1;
        Integer totals = userDao.count();
        Page<UserVo> page = new Page<>(totals, pageNo);
        int start = (pageNo - 1) * page.getPageSize();
        List<User> userList = userDao.findAll(start, page.getPageSize());
        List<UserVo> items = new ArrayList<>();
        for (User user : userList) {
            UserVo userVo = new UserVo();
            userVo.setUserId(user.getId());
            userVo.setUsername(user.getUsername());
            userVo.setCreatetime(user.getCreatetime());
            userVo.setUserState(user.getState());
            Login_Log login_log = login_logDao.findByUserId(user.getId());
            //用户未登录过的话login_log==null
            if (login_log != null) {
                userVo.setLoginIP(login_log.getIp());
                userVo.setLastLoginTime(login_log.getLogintime());
            }
            items.add(userVo);
        }
        page.setItems(items);
        return page;
    }

    public void doActive(String action, String userId) {
        if (StringUtils.isNumeric(userId)) {
            User user = userDao.findById(Integer.valueOf(userId));
            if (user != null) {
                if ("unable".equals(action)) {
                    user.setState(User.USERSTATE_ACTIVE);
                    userDao.update(user);
                } else {
                    user.setState(User.USERSTATE_DISABLED);
                    userDao.update(user);
                }
            } else {
                throw new ServiceException("参数错误");
            }

        } else {
            throw new ServiceException("参数错误");
        }
    }
}
