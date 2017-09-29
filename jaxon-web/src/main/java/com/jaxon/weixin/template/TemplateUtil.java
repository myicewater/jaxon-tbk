package com.jaxon.weixin.template;


import com.alibaba.fastjson.JSONObject;
import com.jaxon.weixin.thread.TokenThread;
import com.jaxon.weixin.util.WeixinUtil;

public class TemplateUtil {
	

	/**
	 * 
	 * @param toUser
	 * @param
	 */
	public static JSONObject sendTemplateMsg(String firstValue, String borrower, String bookTitle, String author, String time, String remarkValue, String templateId, String toUser, String url){
		
		Template t =new Template();
		TemplateData td = new TemplateData();
		TemplateItem first = new TemplateItem();
		
		TemplateItem keyword1 = new TemplateItem();
		TemplateItem keyword2 = new TemplateItem();
		TemplateItem keyword3 = new TemplateItem();
		TemplateItem keyword4 = new TemplateItem();
		
		TemplateItem remark = new TemplateItem();
		
		
		keyword1.setColor("#173177");
		keyword1.setValue(borrower);
		
		keyword2.setColor("#173177");
		keyword2.setValue(bookTitle);
		
		keyword3.setColor("#173177");
		keyword3.setValue(author);
		
		keyword4.setColor("#173177");
		keyword4.setValue(time);
		
		first.setColor("#173177");
		first.setValue(firstValue);
		remark.setColor("#173177");
		remark.setValue(remarkValue);
		
		td.setFirst(first);
		td.setKeyword1(keyword1);
		td.setKeyword2(keyword2);
		td.setKeyword3(keyword3);
		td.setKeyword4(keyword4);
		td.setRemark(remark);
		
		
		t.setData(td);
		t.setTemplate_id(templateId);
		t.setTouser(toUser);
		t.setUrl(url);
		
		
		String msg = JSONObject.toJSONString(t);
		String accessToken = TokenThread.accessToken.getToken();
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken;

		JSONObject jsonobj = WeixinUtil.httpRequest(requestUrl, "POST", msg);
		
		
		return jsonobj;
		
	}
	
	
	public static void main(String[] args) {
		Template t =new Template();
		TemplateData td = new TemplateData();
		TemplateItem content = new TemplateItem();
		TemplateItem title = new TemplateItem();
		
		title.setColor("#173177");
		title.setValue("锄禾日当午");
		content.setColor("#173177");
		content.setValue("汗滴禾下土");
		

		
		t.setData(td);
		t.setTemplate_id("UpdD76XBWd4XRFZK824fcU0gmbe5lEBUES1RFs2kmsM");
		t.setTouser("o36rev-R6h79UWwdc03benr70smI");
		t.setUrl("http://www.yizhifan.com");
		
		//sendTemplateMsg(t);
		
	}
}
