package com.jaxon.weixin.pojo;

/**
 * token实体
 * @author jianyu
 *
 *access_token是公众号的全局唯一票据，公众号调用各接口时都需使用access_token。
 *开发者需要进行妥善保存。access_token的存储至少要保留512个字符空间。
 *access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
 */
public class AccessToken {
	// 获取到的凭证  
    private String token;  
    // 凭证有效时间，单位：秒  
    private int expiresIn;  
  
    public String getToken() {  
        return token;  
    }  
  
    public void setToken(String token) {  
        this.token = token;  
    }  
  
    public int getExpiresIn() {  
        return expiresIn;  
    }  
  
    public void setExpiresIn(int expiresIn) {  
        this.expiresIn = expiresIn;  
    }  
}
