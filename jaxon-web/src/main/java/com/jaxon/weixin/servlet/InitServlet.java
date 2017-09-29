package com.jaxon.weixin.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


import com.jaxon.log.LogUtil;
import com.jaxon.weixin.thread.TokenThread;
import com.jaxon.weixin.util.WeixinUtil;

/**
 * 初始化servlet
 * 
 * @author jaxon
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		LogUtil.info("定时获取token线程启动");
		new Thread(new TokenThread()).start();
		
	}
}
