package com.kaishengit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.kaishengit.dto.wx.User;
import com.kaishengit.dto.wx.WeiXinMessage;
import com.kaishengit.exception.ServiceException;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by liu on 2017/2/24.
 */
@Service
public class WeiXinService {

    private Logger logger = LoggerFactory.getLogger(WeiXinService.class);

    private static final String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={0}&corpsecret={1}";
    private static final String CREATE_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token={0}";
    private static final String SEND_MESSAGE_URL ="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token={0}";
    private static final String EDIT_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token={0}";
    private static final String CODE_TO_USERID_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token={0}&code={1}";
    @Value("${weixin.token}")
    private String token;
    @Value("${weixin.aeskey}")
    private String aeskey;
    @Value("${weixin.CorpID}")
    private String corpID;
    @Value("${weixin.Secret}")
    private String secret;

    private LoadingCache<String,String> cache = CacheBuilder.newBuilder()
            .maximumSize(10).expireAfterWrite(7200, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    String url = MessageFormat.format(ACCESS_TOKEN_URL,corpID,secret);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    String result = response.body().string();
                    response.close();
                    Map<String,Object> map = new Gson().fromJson(result, HashMap.class);
                    if(map.containsKey("errcode")){
                        logger.error("获取微信AccessToken异常:{}",map.get("errcode"));
                        throw new ServiceException("获取微信AccessToken异常:"+map.get("errcode"));
                    }else{
                        return map.get("access_token").toString() ;
                    }

                }
            });

    /**
     * 微信企业号初始化方法
     * @return
     */
    public String init(String msg_signature,String timestamp,String nonce,String echostr){
        try {
            WXBizMsgCrypt crypt = new WXBizMsgCrypt(token,aeskey,corpID);
            return crypt.VerifyURL(msg_signature,timestamp,nonce,echostr);
        } catch (AesException e) {
            throw new ServiceException("微信初始化异常",e);
        }
    }

    public String getAccessToken(){
        try {
            return cache.get("");
        } catch (ExecutionException e) {
            throw new ServiceException("获取AccessToken异常",e);
        }
    }

    /**
     * 在微信企业号上添加用户
     * @param user
     */
    public void saveUser(User user){
        String url = MessageFormat.format(CREATE_USER_URL,getAccessToken());
        String json = new Gson().toJson(user);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),json);
        Request request = new Request.Builder().post(body).url(url).build();

        try {
            Response response = new OkHttpClient().newCall(request).execute();
            String resultJson = response.body().string();
            response.close();
            ObjectMapper mapper = new ObjectMapper();
            Map<String,Object> map = mapper.readValue(resultJson, HashMap.class);

           /* Map<String,Object> map = new Gson().fromJson(resultJson, HashMap.class);*/


            String errcode =  map.get("errcode").toString();

            if(!"0".equals(errcode)){
                logger.error("微信创建用户异常:{}",resultJson);
                throw new ServiceException("微信创建用户异常:"+resultJson);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 微信企业号发送消息
     * @param weiXinMessage
     */
    public void sendMessage(WeiXinMessage weiXinMessage){
        String url = MessageFormat.format(SEND_MESSAGE_URL,getAccessToken());
        String json = new Gson().toJson(weiXinMessage);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),json);
        Request request = new Request.Builder().post(requestBody).url(url).build();
        try {
            Response response = new OkHttpClient().newCall(request).execute();
            String resultJson = response.body().string();
            Map<String,Object> result = new ObjectMapper().readValue(resultJson,HashMap.class);
            if(!"0".equals(result.get("errcode").toString())){
                logger.error("微信企业号发送消息异常:{}",resultJson);
                throw new ServiceException("微信企业号发送消息异常:"+resultJson);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改微信公众好用户信息
     * @param user
     */
    public void updateUser(User user){
        String json = new Gson().toJson(user);
        String url = MessageFormat.format(EDIT_USER_URL,getAccessToken());
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8 "),json);
        Request request = new Request.Builder().post(body).url(url).build();
        try {
            Response response = new OkHttpClient().newCall(request).execute();
            String resultJson = response.body().string();
            Map<String,Object> result = new ObjectMapper().readValue(resultJson,HashMap.class);
            if(!"0".equals(result.get("errcode").toString())){
                logger.error("更新微信用户异常:{}",resultJson);
                throw new ServiceException("更新微信用户异常"+resultJson);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getUserId(String code){
        String url = MessageFormat.format(CODE_TO_USERID_URL,getAccessToken(),code);
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = new OkHttpClient().newCall(request).execute();
            String resultJson = response.body().string();
            Map<String,String> result = new ObjectMapper().readValue(resultJson,HashMap.class);
            if(result.containsKey("UserId")){
                return  result.get("UserId");
            }else{
                return null;
            }
        } catch (IOException e) {
            throw new ServiceException("通过Code获取UserID异常",e);
        }
    }
}
