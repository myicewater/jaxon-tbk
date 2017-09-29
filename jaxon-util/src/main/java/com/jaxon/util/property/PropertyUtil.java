package com.jaxon.util.property;


import com.jaxon.log.LogUtil;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    private static Properties properties = null;

    static{
        loadProperties();
    }

    synchronized private static void loadProperties(){
        LogUtil.info("加载配置文件...");
        properties = new Properties();
        InputStream in = null;
        try{
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("property.properties");
            properties.load(in);
        }catch (Exception e){
            LogUtil.error("加载配置文件异常！",e);
        }
    }

    public static String getProperty(String key){
        if(properties == null){
            loadProperties();
        }

        String property = properties.getProperty(key);
        LogUtil.info(key+" 对应的值是："+property);
        return property;

    }

    public static  String getProperty(String key,String defaultValue){
        if(properties == null){
            loadProperties();
        }
        return properties.getProperty(key,defaultValue);
    }

}
