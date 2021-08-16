package com.njj.controller;

import com.alibaba.fastjson.JSONObject;
import com.njj.bean.User;
import com.njj.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "UserUpdateServlet",urlPatterns = "/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {
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

        String is_del=req.getParameter("is_del");
        System.out.println("is_del = " + is_del);

        String modify_time=req.getParameter("modify_time");
        System.out.println("modify_time = " + modify_time);

        //调用service层
        UserService userService=new UserService();
        Map map = userService.selectUserById(Integer.parseInt(id));
        User data=(User)map.get("data");
        //把参数赋值成对象
        User user = new User();
        user.setId(Integer.parseInt(id));
        user.setUsername(username);
        user.setReal_name(real_name);
        user.setPassword(password);
        //String typess=(type.equals("管理员")?"1":"2");
        user.setType(Integer.parseInt(type));
        user.setIs_del(Integer.parseInt(is_del));
        user.setImg(data.getImg());
        user.setCreate_time(data.getCreate_time());
        user.setModify_time(modify_time);

        Map map1=userService.updateUser(user);
        String s= JSONObject.toJSONString(map1);
        System.out.println("s = " + s);

        PrintWriter printWriter=resp.getWriter();
        printWriter.println(s);
        printWriter.close();
    }
}
