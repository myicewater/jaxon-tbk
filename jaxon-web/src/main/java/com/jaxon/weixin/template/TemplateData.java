package com.jaxon.weixin.template;
/**
 * 图书借阅通知
 * @author 朱素海
 *
 */
public class TemplateData {

	private TemplateItem first;
	/**
	 * 借阅人
	 */
	private TemplateItem keyword1;
	/**
	 * 书名
	 */
	private TemplateItem keyword2;
	/**
	 * 作者
	 */
	private TemplateItem keyword3;
	/**
	 * 时间
	 */
	private TemplateItem keyword4;
	
	
	private TemplateItem remark;


	public TemplateItem getFirst() {
		return first;
	}


	public void setFirst(TemplateItem first) {
		this.first = first;
	}


	public TemplateItem getKeyword1() {
		return keyword1;
	}


	public void setKeyword1(TemplateItem keyword1) {
		this.keyword1 = keyword1;
	}


	public TemplateItem getKeyword2() {
		return keyword2;
	}


	public void setKeyword2(TemplateItem keyword2) {
		this.keyword2 = keyword2;
	}


	public TemplateItem getKeyword3() {
		return keyword3;
	}


	public void setKeyword3(TemplateItem keyword3) {
		this.keyword3 = keyword3;
	}


	public TemplateItem getKeyword4() {
		return keyword4;
	}


	public void setKeyword4(TemplateItem keyword4) {
		this.keyword4 = keyword4;
	}


	public TemplateItem getRemark() {
		return remark;
	}


	public void setRemark(TemplateItem remark) {
		this.remark = remark;
	}

	
	
	
	
	
	
}
