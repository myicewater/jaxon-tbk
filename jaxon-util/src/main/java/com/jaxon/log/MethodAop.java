package com.jaxon.log;

import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import com.jaxon.log.LogUtil;
import org.aspectj.lang.ProceedingJoinPoint;

import com.alibaba.fastjson.JSONObject;


/**
 * @author Jaxon
 */
public class MethodAop {

    //private static final LogUtilger LogUtil = LogUtilger.getLogUtilger(MethodAop.class);




    /**
     * 是否打印接口输入输出参数、时间耗时日志
     * 打印参数可以只用在开发和测试环境，生产环境可以配置为false，减少服务器压力，和日志打印
     * 配置文件注入，默认false
     */
    private Boolean showParam = false;

    /**
     * 项目名称
     * 配置文件注入，必填
     */
    private String projectName;

    /**
     * 方法类型：0-action调用，1-service调用，2-dao调用
     * <p>
     * 配置文件注入，必填
     */
    private String methodType;

    /**
     * 调用方式：0-调用自己系统方法  1-调用其他系统方法
     * <p>
     * 配置文件注入， 必填
     */
    private String invokeType;

    /**
     * 是否发送Mq，如果不发送则只进行日志拦截,为了兼容消费者不存在情况
     */
    private Boolean isSendMq;


    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        String method = pjp.getSignature().getName();
        Object[] params = pjp.getArgs();
        String className = pjp.getTarget().getClass().getSimpleName();

        long invokeTime = System.currentTimeMillis();

        //在执行前打印入参，防止异常不打印日志
        try {
            if (showParam) {//打印参数可以只用在开发和测试环境，生产环境可以配置为false，减少服务器压力，和日志打印
                if (methodType.equals("0")) {//controller调用
                    if (params != null) {
                        for (Object object : params) {
                            if (object instanceof HttpServletRequest) {
                                HttpServletRequest request = (HttpServletRequest) object;
                                if (request != null) {
                                    String parameters = getParameters(request);
                                    LogUtil.info("【" + invokeTime + " " + method + "  Controller入参】:" + parameters);
                                }
                                break;
                            }
                        }
                    } else {
                        LogUtil.info("【" + invokeTime + " " + method + "  Controller入参】:" + params);
                    }

                } else if (methodType.equals("1") || methodType.equals("2")) {//service 或者 dao
                    LogUtil.info("【" + invokeTime + " " + method + "  入参】:" + JSONObject.toJSONString(params));
                }
            }
        } catch (Exception e) {
            LogUtil.error("【AOP日志: " + method + "  入参打印异常！】", e);
        }

        long t = System.currentTimeMillis();
        Object result = pjp.proceed();
        long timeCounsume = System.currentTimeMillis() - t;
        String ip = null;//ip = NetworkUtil.getIpAddress(request);
        String agent = null;

        try {
            if (showParam) {//打印参数可以只用在开发和测试环境，生产环境可以配置为false，减少服务器压力，和日志打印
                if (methodType.equals("0")) {//controller调用

                    LogUtil.info("【" + invokeTime + " " + method + "  Controller结果】:" + result);
                    LogUtil.info("【" + invokeTime + " " + method + "  Controller耗时】:" + timeCounsume);

                } else if (methodType.equals("1") || methodType.equals("2")) {//service 或者 dao
                    LogUtil.info("【" + invokeTime + " " + method + "  结果】:" + JSONObject.toJSONString(result));
                    LogUtil.info("【" + invokeTime + " " + method + "  耗时】:" + timeCounsume);
                }
            }
        } catch (Exception e) {
            LogUtil.error("【AOP日志: " + method + "  接口异常！】", e);
        }
        if (isSendMq) {
            //可以对数据进行统计
        }
        return result;

    }


    private String getParameters(HttpServletRequest request) {
        try {
            Enumeration<String> parameterNames = request.getParameterNames();
            StringBuilder sb = new StringBuilder();
            if (parameterNames != null) {
                String key = null;
                while (parameterNames.hasMoreElements()) {
                    key = parameterNames.nextElement();
                    sb.append(key + ":" + request.getParameter(key) + " , ");
                }
            }
            return new String(sb);
        } catch (Exception e) {
            LogUtil.error("打印接口参数异常!", e);
        }
        return "";
    }



    public Boolean getShowParam() {
        return showParam;
    }

    public void setShowParam(Boolean showParam) {
        this.showParam = showParam;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getInvokeType() {
        return invokeType;
    }

    public void setInvokeType(String invokeType) {
        this.invokeType = invokeType;
    }


    public Boolean getIsSendMq() {
        return isSendMq;
    }

    public void setIsSendMq(Boolean isSendMq) {
        this.isSendMq = isSendMq;
    }

    public static void main(String[] args) {
        LogUtil.info("Test");


    }


}
