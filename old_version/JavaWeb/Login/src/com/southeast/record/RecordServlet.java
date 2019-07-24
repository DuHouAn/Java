package com.southeast.record;

import com.southeast.utils.CookieUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class RecordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        response.setContentType("text/html;charset=utf-8");
        PrintWriter w = response.getWriter();

        //2.获取指定名称的Cookie
        Cookie cookie = CookieUtils.getCookieByName("record",request.getCookies());

        //3.判断cookie是否为空；
        // 若为null,则说明是第一次访问；
        // 若不为 null,则根据cookie显示上一次的访问时间
        if(cookie == null){
            w.write("这是您第一次访问");
        }else{
            long lastTime= Long.parseLong(cookie.getValue());
            w.write("您上次访问的时间："+ new Date(lastTime).toLocaleString());
        }

        //4.记录当前访问时间，并且该信息存入cookie中
        Cookie c = new Cookie("record",System.currentTimeMillis()+"");
        response.addCookie(c);
    }

    //获取指定名称的Cookie
    private Cookie getCookieByName(String name,Cookie[] cookies){
        if( cookies !=null){
            for(Cookie cookie : cookies){
                if(name.equals(cookie.getName())){
                    return cookie;
                }
            }
        }
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
