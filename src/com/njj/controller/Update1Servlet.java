package com.njj.controller;

import com.njj.bean.User;
import com.njj.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Update1Servlet",urlPatterns = "/Update1Servlet")
public class Update1Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接受登陆传过来的3个参数
        //1、修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");


        //2、接收前端传过来的参数
        String id=req.getParameter("id");
        System.out.println("id = " + id);

        String username =req.getParameter("username");
        System.out.println("username = " + username);

        String real_name=req.getParameter("real_name");
        System.out.println("real_name = " + real_name);

        String password =req.getParameter("password");
        System.out.println("password = " + password);

        String type =req.getParameter("type");
        System.out.println("type = " + type);

        String img=req.getParameter("img");
        System.out.println("img = " + img);

        String is_del=req.getParameter("is_del");
        System.out.println("is_del = " + is_del);

        String create_time=req.getParameter("create_time");
        System.out.println("create_time = " + create_time);

        String modify_time=req.getParameter("modify_time");
        System.out.println("modify_time = " + modify_time);

        int type1=(is_del=="管理员"?1:2);
        System.out.println("type1 = " + type1);

        int i1 = Integer.parseInt(id);
        System.out.println("i1 = " + i1);

        int i2 = Integer.parseInt(type);
        // System.out.println("i2 = " + i2);




        //3、调佣dao
        User user = new User();
        UserDao userDao = new UserDao();
        user.setId(i1);
        user.setUsername(username);
        user.setReal_name(real_name);
        user.setPassword(password);
        user.setType(i2);
        user.setImg(img);
        user.setIs_del(type1);
        user.setCreate_time(create_time);
        user.setModify_time(modify_time);
        System.out.println("user = " + user);
        int i = userDao.updateUser(user);
        System.out.println("修改成功 " + i);

    }
}
