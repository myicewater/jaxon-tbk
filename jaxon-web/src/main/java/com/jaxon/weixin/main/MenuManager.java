package com.jaxon.weixin.main;

import com.jaxon.log.LogUtil;
import com.jaxon.util.property.PropertyUtil;

import com.jaxon.weixin.pojo.AccessToken;
import com.jaxon.weixin.pojo.Button;
import com.jaxon.weixin.pojo.ClickButton;
import com.jaxon.weixin.pojo.ComplexButton;
import com.jaxon.weixin.pojo.Menu;
import com.jaxon.weixin.pojo.ViewButton;
import com.jaxon.weixin.util.WeixinUtil;

/**
 * 
 * @author jaxon
 *
 */
public class MenuManager {

	private static String APPID = PropertyUtil.getProperty("weixinAppid");
	private static String SECRET = PropertyUtil.getProperty("weixinSecret");
	
	public static void main(String[] args) {
		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(APPID, SECRET);
		if (at != null) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), at.getToken());
			System.out.println(result);
			// 判断菜单创建结果
			if (0 == result){
				LogUtil.info("菜单创建成功！");
			System.out.println("菜单创建成功！");
			}else{
				LogUtil.info("菜单创建失败，错误码：" + result);
			}
		}
	}
	
	


	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	public static Menu getMenu() {
		//获取项目路径
		String path = PropertyUtil.getProperty("weixinServerAddr");
		
		//链接类型的菜单
		ViewButton indexMenue = new ViewButton();
		indexMenue.setName("链接菜单");
		indexMenue.setType("view");
		indexMenue.setUrl("");
		//点击事件的按钮
		ClickButton  btnMenu = new ClickButton();
		btnMenu.setName("按钮菜单");
		btnMenu.setType("click");
		btnMenu.setKey("fuck");
		//二级菜单子菜单
		
		ClickButton  subMenu = new ClickButton();
		subMenu.setName("二级子菜单");
		subMenu.setType("click");
		subMenu.setKey("girl");
		
		ComplexButton complexButton = new ComplexButton();
		complexButton.setName("二级菜单");
		complexButton.setSub_button(new Button[] { subMenu});
		
		//总菜单
		Menu menu = new Menu();
		menu.setButton(new Button[] {indexMenue,btnMenu, complexButton });

		return menu;
	}
}
