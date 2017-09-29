package com.jaxon.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jaxon.exception.BusinessException;
import com.jaxon.util.TFUtil;
import org.apache.log4j.Logger;

/**
 * 日志工具类
 */
public class LogUtil {

    private static Logger log = Logger.getRootLogger();

    public static String getLogMsg(String msg){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        //log.info(JSONObject.toJSONString(stackTrace));
        if(stackTrace != null && stackTrace.length > 3){

            StackTraceElement st = stackTrace[3];//由于本身多了一层调用封装
            return "["+st.getClassName()+"-"+st.getMethodName()+"("+st.getLineNumber()+")"+"]:"+msg;
        }else{
            try {
                throw new BusinessException("获取LogUtil 调用者异常！");
            } catch (BusinessException e) {
                log.error("获取LogUtil 调用者异常！",e);
                return "";
            }
        }

    }

    public static void warn(String msg){
        log.warn(getLogMsg(msg));
    }

    public static void info(String msg){

        log.info(getLogMsg(msg));
    }

    public static void error(String msg,Throwable t){
        log.error(msg,t);
    }

    public static void error(String msg){
        log.error(msg);
    }

    public static void main(String[] args) {
        info("info msg");
    }
}
