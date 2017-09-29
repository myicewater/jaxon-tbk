package com.jaxon.weixin.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jaxon.log.LogUtil;
import com.jaxon.util.property.PropertyUtil;


import com.jaxon.weixin.pojo.Fans;
import com.jaxon.weixin.resp.TextMessage;
import com.jaxon.weixin.thread.TokenThread;
import com.jaxon.weixin.util.MessageUtil;
import com.jaxon.weixin.util.SpringBeanUtil;
import com.jaxon.weixin.util.WeixinUtil;

/**
 * @author 朱素海
 * <p>
 * 包括几乎所有的微信事件处理
 */
public class CoreService {
    private static String path = PropertyUtil.getProperty("weixinServerAddr");

    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
    public String processRequest(HttpServletRequest request) {
        try {
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型，event
            String msgType = requestMap.get("MsgType");


            if (MessageUtil.isEvent(msgType)) {//事件请求

                String event = requestMap.get("Event");

                if (event.equals(MessageUtil.EVENT_TYPE_SCAN)) {//已关注扫描事件,场景二维码扫描

                    String sceneId = requestMap.get("EventKey");


                    LogUtil.info("进入已关注扫描事件，openid为：" + fromUserName + "场景Id：" + sceneId);

                    if ("7758".equals(sceneId)) {
                        TextMessage tm = new TextMessage();
                        tm.setToUserName(fromUserName);
                        tm.setFromUserName(toUserName);
                        tm.setCreateTime(new Date().getTime());
                        tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

                        tm.setContent("<a href='" + path + "exampath" + fromUserName + "'>点击这里</a>");
                        return MessageUtil.textMessageToXml(tm);
                    }

                    return "";
                }


                /**
                 * 关注事件（包括扫描场景二维码的关注）
                 */
                if (MessageUtil.isSubscribe(event)) {

                    String eventKey = requestMap.get("EventKey");

                    if (eventKey != null && eventKey.startsWith("qrscene_")) {
                        LogUtil.info("进入扫描场景二维码关注事件");
                        String sceneId = requestMap.get("EventKey");
                        sceneId = sceneId.substring(8, sceneId.length());


                        //添加到数据库，如果存在不插入数据库
                        Fans fans = WeixinUtil.getFansInfo(TokenThread.accessToken.getToken(), fromUserName);

                        if ("7758".equals(sceneId)) {//场景扫描处理事件

                            WeixinUtil.sendText(TokenThread.accessToken.getToken(), fromUserName, "欢迎关注三里屯太古里官方微信，请点击底部“领取礼盒”按钮，开启音乐之旅！点击“打开礼盒”领取神秘大礼哦！");

                            TextMessage tm = new TextMessage();
                            tm.setToUserName(fromUserName);
                            tm.setFromUserName(toUserName);
                            tm.setCreateTime(new Date().getTime());
                            tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

                            tm.setContent("<a href='" + path + "examPath?openId=" + fromUserName + "'>点击这里</a>领取奖品/礼物");
                            return MessageUtil.textMessageToXml(tm);
                        }

                    } else {
                        LogUtil.info("单纯的关注事件");

                        //添加到数据库，如果存在不插入数据库
                        Fans fans = WeixinUtil.getFansInfo(TokenThread.accessToken.getToken(), fromUserName);


                    }


                    TextMessage textMessage = new TextMessage();
                    textMessage.setToUserName(fromUserName);
                    textMessage.setFromUserName(toUserName);
                    textMessage.setCreateTime(new Date().getTime());
                    textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                    String content = "";
                    content += "欢迎关注";

                    textMessage.setContent(content);

                    return MessageUtil.textMessageToXml(textMessage);
                }


                /**
                 * 取消关注
                 */
                if (MessageUtil.isUnsubscribe(event)) {
                    //保存关注着到数据库中
                    LogUtil.info("取消关注，openid为：");
                }


                /**
                 * 点击菜单事件
                 */
                if (MessageUtil.isClick(event)) {

                    String menueKey = requestMap.get("EventKey");
                    LogUtil.info("menueKey:" + menueKey);
                    if ("keyword1".equals(menueKey)) {
                        TextMessage tm = new TextMessage();
                        tm.setToUserName(fromUserName);
                        tm.setFromUserName(toUserName);
                        tm.setCreateTime(new Date().getTime());
                        tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

                        tm.setContent("<a href='" + path + "examPath?openId=" + fromUserName + "'>点击这里</a>");
                        return MessageUtil.textMessageToXml(tm);
                    }

                    if ("keyword2".equals(menueKey)) {
                        TextMessage tm = new TextMessage();
                        tm.setToUserName(fromUserName);
                        tm.setFromUserName(toUserName);
                        tm.setCreateTime(new Date().getTime());
                        tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

                        tm.setContent("<a href='" + path + "examPath?openId=" + fromUserName + "'>点击这里</a>");
                        return MessageUtil.textMessageToXml(tm);
                    }
                }


            }//事件请求结束


            /**
             * 关键字事件处理
             */
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                // 接收用户发送的文本消息内容
                String content = requestMap.get("Content");
                if ("关键字".equals(content)) {
                    TextMessage tm = new TextMessage();
                    tm.setToUserName(fromUserName);
                    tm.setFromUserName(toUserName);
                    tm.setCreateTime(new Date().getTime());
                    tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

                    tm.setContent("回复内容");
                    return MessageUtil.textMessageToXml(tm);
                }
            }


        } catch (Exception e) {
            LogUtil.error("核心service异常", e);
            e.printStackTrace();
        }
        return "";
    }

    /**
     * emoji表情转换(hex -> utf-16)
     *
     * @param hexEmoji
     * @return
     */
    public static String emoji(int hexEmoji) {
        return String.valueOf(Character.toChars(hexEmoji));
    }


}
