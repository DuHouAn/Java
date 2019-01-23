package com.southeast.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 清空浏览记录
 */
public class ClearServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie c = new Cookie("ids","");

        //cookie的路径与 CategoryServlet中的cookie中的路径要相同
        c.setPath(request.getContextPath()+"/");
        //直接将cookie设置成无效
        c.setMaxAge(0);
        response.addCookie(c);

        //重定向
        response.sendRedirect(request.getContextPath()+"/category_list.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
