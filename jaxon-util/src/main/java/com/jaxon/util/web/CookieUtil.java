package com.jaxon.util.web;

import com.jaxon.util.TFUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieUtil
{



    /**
     * 获取cookie
     * @param req
     * @param key
     * @return
     */
    public static String getCookie(HttpServletRequest req, String key)
    {
        if(req == null || TFUtil.isBlank(key)){

            return null;
        }

        Cookie[] cookies = req.getCookies();
        if (cookies != null && cookies.length > 0)
        {
            for (Cookie cookie : cookies)
            {
                if (cookie.getName().equals(key))
                {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }




    /**
     * 增加登录Cookie
     * @param cookieMaxAge cookie有效时长 单位秒
     * @param resp
     */
    public static void addCooike( HttpServletResponse resp, String key, String value,String domain ,int cookieMaxAge, Boolean httpOnly)
    {

        if ( resp == null || TFUtil.isBlank(key) || TFUtil.isBlank(value))
        {
            return;
        }


        //设置登录Cookie
        Cookie cookie = new Cookie(key, value);
        cookie.setDomain("."+domain);
        cookie.setPath("/");
        cookie.setMaxAge(cookieMaxAge);
        cookie.setHttpOnly(httpOnly);//会话 cookie 中缺少 HttpOnly 属性
        resp.addCookie(cookie);
    }

    

    
    public static void main(String[] args)
    {

    }
}
