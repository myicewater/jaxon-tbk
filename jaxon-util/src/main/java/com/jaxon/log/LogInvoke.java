package com.jaxon.log;

public class LogInvoke {

    public static void invokeLog(String msg){
        LogUtil.info(msg);
    }

    public static void main(String[] args) {
        LogUtil.info("invoke log");
    }
}
