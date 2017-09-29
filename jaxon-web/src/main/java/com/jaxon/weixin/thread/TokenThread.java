package com.jaxon.weixin.thread;


import com.alibaba.fastjson.JSONObject;
import com.jaxon.log.LogUtil;
import com.jaxon.util.property.PropertyUtil;


import com.jaxon.weixin.pojo.AccessToken;
import com.jaxon.weixin.pojo.JsapiTicket;
import com.jaxon.weixin.util.WeixinUtil;

/**
 * 定时获取微信access_token的线程
 *
 * @author liuyq
 * @date 2013-05-02
 */
public class TokenThread implements Runnable {
    public static AccessToken accessToken = null;
    private final static String jsapi_ticket_url = " https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=TOKEN&type=jsapi";
    public static JsapiTicket jsapi_ticket = null;

    public void run() {
        while (true) {
            try {
                String appid = PropertyUtil.getProperty("weixinAppid");
                String secret = PropertyUtil.getProperty("weixinSecret");
                accessToken = WeixinUtil.getAccessToken(appid, secret);
                if (null != accessToken) {
                    LogUtil.info("获取access_token成功，有效时长{" + accessToken.getExpiresIn() + "}秒 token:{" + accessToken.getToken() + "}");


                    String url = jsapi_ticket_url.replace("TOKEN", accessToken.getToken());
                    JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);
                    if (jsonObject != null || "".equals(jsonObject)) {
                        jsapi_ticket = new JsapiTicket();
                        jsapi_ticket.setErrcode((Integer) jsonObject.get("errcode"));
                        jsapi_ticket.setErrmsg((String) jsonObject.get("errmsg"));
                        jsapi_ticket.setTicket((String) jsonObject.get("ticket"));
                        jsapi_ticket.setExpiresIn((Integer) jsonObject.get("expires_in"));

                    }
                    if (null != jsapi_ticket) {
                        LogUtil.info("获取jsapi_ticket成功，有效时长{" + jsapi_ticket.getExpiresIn() + "}秒 ticket:{" + jsapi_ticket.getTicket() + "}");
                        // 休眠7000秒
                        Thread.sleep(jsapi_ticket.getExpiresIn() * 1000);
                    } else {
                        // 如果access_token为null，60秒后再获取
                        Thread.sleep(60 * 1000);
                    }


                } else {
                    // 如果access_token为null，60秒后再获取
                    Thread.sleep(60 * 1000);
                }


            } catch (InterruptedException e) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e1) {
                    LogUtil.error("{}", e1);
                }
                LogUtil.error("{}", e);
            }
        }
    }
}
