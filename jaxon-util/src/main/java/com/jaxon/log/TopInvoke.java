package com.jaxon.log;

public class TopInvoke {
    public static void main(String[] args) {

        LogUtil.info("Top self log info");
        LogInvoke.invokeLog("Top invoke");
    }
}
