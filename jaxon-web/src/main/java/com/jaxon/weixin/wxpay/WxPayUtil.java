package com.jaxon.weixin.wxpay;

import com.jaxon.util.property.PropertyUtil;
import org.apache.log4j.Logger;

import com.jaxon.weixin.util.HttpRequestUtil;
import com.jaxon.weixin.util.MD5Util;
import com.jaxon.weixin.util.MessageUtil;

/**
 * 微信支付工具类
 * @author 朱素海
 *
 */
public class WxPayUtil {

	private static final String unifiedOrderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	private static final Logger logger = Logger.getLogger(WxPayUtil.class);
	/**
	 * 下单
	 * @return
	 */
	public static String unifiedOrder(UnifiedPayOrder order){
		
		String orderInfo = MessageUtil.unifiedOrderToXml(order);
		logger.info("微信支付订单信息："+orderInfo);
		String result = HttpRequestUtil.sendPost(unifiedOrderUrl, orderInfo);
		logger.info("微信下单返回结果："+result);
		return result;
	}
	
	/**
	 * 获取下单签名
	 * @param order
	 * @return
	 */
	public static String getSign(UnifiedPayOrder order){
		
		String stringA="appid="+order.getAppid()
				+"&body="+order.getBody()
				+"&mch_id="+order.getMch_id()
				+"&nonce_str="+order.getNonce_str()
				+"&notify_url="+order.getNotify_url()
				+"&openid="+order.getOpenid()
				+"&out_trade_no="+order.getOut_trade_no()
				+"&spbill_create_ip="+order.getSpbill_create_ip()
				+"&total_fee="+order.getTotal_fee()
				+"&trade_type="+order.getTrade_type();
		logger.info("下单签名stringA:"+stringA);
		String api_key = PropertyUtil.getProperty("api_key");
		String stringSignTemp= stringA + "&key="+api_key;
		String sign  = MD5Util.MD5Encode(stringSignTemp).toUpperCase();
		return sign;
	}
	
	/**
	 * 支付签名
	 * @param payInfo
	 * @return
	 */
	public static String getPaySign(WxPayInfo payInfo){
		String stringA="appId="+payInfo.getAppId()
				+"&nonceStr="+payInfo.getNonceStr()
				+"&package="+payInfo.getPackage2()
				+"&signType="+payInfo.getSignType()
				+"&timeStamp="+payInfo.getTimeStamp();
		logger.info("支付签名stringA:"+stringA);
		String api_key = PropertyUtil.getProperty("api_key");
		String stringSignTemp= stringA + "&key="+api_key;
		logger.info("支付签名stringSignTemp:"+stringSignTemp);
		String sign  = MD5Util.MD5Encode(stringSignTemp).toUpperCase();
		return sign;
	}
	
	
	public static void main(String[] args) {
		
		String str[] = {"appid","mch_id","1000","10000100","ibuaiVcKdpRxkhJA"};
		
		String stringA="appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA";
		
		String stringSignTemp=stringA + "&key=192006250b4c09247ec02edce69f6a2d";
		String sign  = MD5Util.MD5Encode(stringSignTemp).toUpperCase();
		
		System.out.println(sign);
		
	}
	
}
