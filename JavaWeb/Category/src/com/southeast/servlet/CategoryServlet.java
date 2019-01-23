package com.southeast.servlet;

import com.southeast.utils.CookieUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

/**
 * 记录商品浏览记录，只展示3个商品
 */
public class CategoryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取当前访问商品的id
        String id = request.getParameter("id");

        Cookie c = CookieUtils.getCookieByName("ids",request.getCookies());

        //判断该 cookie 是否为空
        String ids="";
        if(c == null){
            //若为空,说明之前没有访问记录
            //将当前商品的id放入ids中
            ids = id;
        }else{
            //若不为空,获取值。也就是之前浏览的商品编号，使用 "-"进行连接
            ids = c.getValue();

            //将 ids 通过"-"进行分割，然后存入list中，方便后续的操作
            String[] categoryIds = ids.split("-");
            LinkedList<String> categories = new LinkedList<>();
            if(categories != null){
                for(String categoryId : categoryIds){
                    categories.add(categoryId);
                }
            }
            //判断之前记录中有无该商品
            if(categories.contains(id)){
                //若有，删除原来的id,将当前的id放入前面
                categories.remove(id);
            }else{
                // 若没有
                // 继续判断长度是否>=3
                // 若>=3,移除最后一个,将当前的id放入最前面
                // 若<3,直接将当前的id放入最前面.
                if(categories.size() >= 3){
                    categories.removeLast();
                }
            }
            //不管如何，id都是最新浏览的，直接加入到前面
            categories.addFirst(id);

            ids="";
            for(String categoryId : categories){
                ids += (categoryId + "-");
            }
            ids = ids.substring(0,ids.length()-1);
        }

        //创建cookie
        c=new  Cookie("ids",ids);
        //设置访问路径
        c.setPath(request.getContextPath()+"/");
        //设置存活时间
        c.setMaxAge(60);

        //写回浏览器
        response.addCookie(c);

        //跳转到指定的商品页面上
        response.sendRedirect(request.getContextPath()+"/category_info"+id+".htm");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
