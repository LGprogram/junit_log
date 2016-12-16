package com.kaishengit.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by liu on 2016/12/15.
 */
public class Config {
    private static Properties properties = new Properties();
    public static String get(String key){
        try {
            properties.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("读取+\"config.properties\"+错误",e);
        }
        return properties.getProperty(key);
    }
}
