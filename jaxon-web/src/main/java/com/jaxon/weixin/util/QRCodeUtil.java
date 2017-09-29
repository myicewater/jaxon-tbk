package com.jaxon.weixin.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


import com.alibaba.fastjson.JSONObject;
import com.jaxon.log.LogUtil;
import com.jaxon.util.property.PropertyUtil;
import org.apache.log4j.Logger;




public class QRCodeUtil {
	private static final Logger logger = Logger.getLogger(QRCodeUtil.class);
	/**
	 * 根据ticket 换取二维码
	 * @param ticket
	 * @param 
	 * @return
	 */
	public static void getQRCode(String ticket,String targetDir){
		String requestUrl="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
		requestUrl = requestUrl.replace("TICKET", CommonUtil.urlEncodeUTF8(ticket));
		try{
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			
		
			//将微信服务器返回的输入流写入文件
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(targetDir));
			byte[] buf = new byte[1024];
			int size =0;
			while((size = bis.read(buf)) != -1){
				fos.write(buf, 0, size);
				
			}
			fos.close();
			bis.close();
			conn.disconnect();
			LogUtil.info(" 根据ticket 换取二维码成功, targetDir=" + targetDir);
		}catch(Exception e){
			LogUtil.error(" 根据ticket 换取二维码失败{}",e);
		}
	}
	/**
	 * 创建永久带参二维码
	 * @param accessToken
	 * @param senceId
	 * @return
	 */
	public static String createQRCode(String accessToken,int senceId){
		
		String ticket = null;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
		
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		//需要提交的json数据
		String jsonMsg = "{\"action_name\": \"QR_LIMIT_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":%d}}}";
		//创建永久带参数二维码
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl,"POST",String.format(jsonMsg, senceId));
		if(null!=jsonObject){
			try{
				ticket = jsonObject.getString("ticket");
				logger.info(" 创建永久带参二维码成功 ticket:{}"+ ticket);
				
			}catch(Exception e){
				int errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				logger.error(" 创建永久带参二维码失败 errcode:{"+errorCode+"} errmsg:{"+errorMsg+"}");
				
			}
		}
		return ticket;
	}
	
	
	/**
	 * 创建临时二维码
	 * @param accessToken
	 * @param senceId
	 * @return
	 */
	public static String createQRCodeTemporary(String accessToken,int senceId,int expireSeconds){
		
		String ticket = null;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
		
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		//需要提交的json数据
		String jsonMsg = "{\"expire_seconds\":\""+expireSeconds+"\",\"action_name\": \"QR_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":%d}}}";
		//创建永久带参数二维码
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl,"POST",String.format(jsonMsg, senceId));
		if(null!=jsonObject){
			try{
				ticket = jsonObject.getString("ticket");
				logger.info(" 创建临时带参二维码成功 ticket:{}"+ ticket+" 超时时间："+expireSeconds);
				
			}catch(Exception e){
				int errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				logger.error(" 创建临时带参二维码失败 errcode:{"+errorCode+"} errmsg:{"+errorMsg+"}");
				
			}
		}
		return ticket;
	}
	
	
	private static String APPID = PropertyUtil.getProperty("weixinAppid");
	private static String SECRET = PropertyUtil.getProperty("weixinSecret");
	
	public static void main(String args[]){
		//运行生成二维码
		//
//		AccessToken accessToken =WeixinUtil.getAccessToken(APPID, SECRET);
//		
//		String accessTokenStr = accessToken.getToken();//CommonUtil.getToken("APPID","APPSECRET").getAccessToken();
//		//
//		String ticket = createQRCode(accessTokenStr, 617);
//		
//		logger.info("ticket:"+ticket);
		//gQHm8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2hFalk2a1RreTFHeTA1NVNQV1EtAAIE43mrVAMEAAAAAA==
		String ticket ="gQEw8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL09raFp3REhsc0dESlVzYlRPbVRvAAIEBgzHVAMEAAAAAA==";
		String savePath = "E:/tmp";
		//
		getQRCode(ticket, savePath);
		
	}

}
