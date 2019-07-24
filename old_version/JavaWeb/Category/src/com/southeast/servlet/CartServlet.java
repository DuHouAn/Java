package com.southeast.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 18351 on 2019/1/23.
 */
public class CartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();

        //1.获取商品名称
        String name = request.getParameter("name");

        //2.获取购物车，实际上就是存入session的map
        HashMap<String,Integer> map = (HashMap<String, Integer>) request.getSession().getAttribute("cart");

        Integer num = null;

        //3.判断购物车是否为空
        if(map==null){
            //3.1 购物车为空，说明是第一次将商品放入购物车
            //先创建购物车,
            map = new HashMap<>();
            request.getSession().setAttribute("cart",map);
            num = 1;
        }else{
            //3.2 购物车不为空，判断该商品之前是否已经加入购物车
            num = map.get(name);
            if(num == null){
                //num==null,说明该商品之前未加入购物车
                num = 1;
            }else{
                num ++ ;
            }
        }
        map.put(name,num);

        //4.提示信息
        out.print("<center>已经将<b>"+name+"</b>添加到购物车中<hr></center>");
        out.print("<center><a href='"+request.getContextPath()+"/category_list.jsp'>继续购物</a></center><br/>");
        out.print("<center><a href='"+request.getContextPath()+"/cart.jsp'>查看购物车</a><center>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
