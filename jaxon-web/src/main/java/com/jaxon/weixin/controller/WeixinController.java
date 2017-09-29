package com.jaxon.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jaxon.entity.web.ApiResult;
import com.jaxon.exception.BusinessException;
import com.jaxon.util.TFUtil;
import com.jaxon.util.property.PropertyUtil;
import com.jaxon.util.web.HttpClientUtils;
import com.jaxon.weixin.service.CoreService;
import com.jaxon.weixin.util.SHA1;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WeixinController {

    private static String TOKEN = PropertyUtil.getProperty("weixinToken");
    private static String APPID = PropertyUtil.getProperty("weixinAppid");
    private static String SECRET = PropertyUtil.getProperty("weixinSecret");


    /**
     * 获取当前的accessToken
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("getAccessToken")
    @ResponseBody
    public String getAccessToken(HttpServletRequest request, HttpServletResponse response){
        ApiResult result = null;
        String json = null;
        Map<String,Object> data = new HashMap<String,Object>();
        try
        {

        }

        catch (Exception e)
        {
            result = new ApiResult("F", "服务器繁忙！", null);
        }finally{
            if(result == null){
                result = new ApiResult(data);
            }
            json = JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
        }
        return json;
    }


    /**
     * 根据auth 认证code获取openid
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("getOpenIdByCode")
    @ResponseBody
    public String getOpenIdByCode(HttpServletRequest request, HttpServletResponse response) {
        ApiResult result = null;
        String json = null;
        Map<String, Object> data = new HashMap<String, Object>();
        try {

            String code = request.getParameter("code");
            if (TFUtil.isEmpty(code)) {
                throw new BusinessException("获取openid传入code为空！");
            }
            String getOpenIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?&appid="
                    + APPID + "&secret=" + SECRET + "&code=" + code
                    + "&grant_type=authorization_code";
            String r = HttpClientUtils.httpGet(getOpenIdUrl);
            if (TFUtil.isNotEmpty(r)) {
                JSONObject jsonObject = JSONObject.parseObject(r);
                if (TFUtil.isNotEmpty(jsonObject.getString("errcode"))) {
                    throw new BusinessException("获取openid异常：" + jsonObject.getString("errmsg"));
                }
                jsonObject.getString("openid");
            } else {
                throw new BusinessException("根据code获取openid返回为空！");
            }

        } catch (Exception e) {
            result = new ApiResult("F", e.getMessage(), null);
        } finally {
            if (result == null) {
                result = new ApiResult(data);
            }
            json = JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
        }
        return json;
    }

    /**
     * 微信服务地址
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/weixin", method = RequestMethod.GET)
    public void validator(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");

        String[] str = {TOKEN, timestamp, nonce};
        Arrays.sort(str); // 字典序排序
        String bigStr = str[0] + str[1] + str[2];
        // SHA1加密
        String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();

        // 确认请求来至微信
        if (digest.equals(signature)) {
            response.getWriter().print(echostr);
        }

    }

    /**
     * 微信服务地址
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/weixin", method = RequestMethod.POST)
    public void validatorPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 调用核心业务类接收消息、处理消息
        String respMessage = new CoreService().processRequest(request);
        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();


    }


}
