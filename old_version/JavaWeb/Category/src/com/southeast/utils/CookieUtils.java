package com.southeast.utils;

import javax.servlet.http.Cookie;

/**
 * 根据 cookie名称获取Cookie 的工具类
 */
public class CookieUtils {
    public static Cookie getCookieByName(String name,Cookie[] cookies){
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(name.equals(cookie.getName())){
                    return cookie;
                }
            }
        }
        return null;
    }
}
