package com.njj.controller;

import com.alibaba.fastjson.JSONObject;
import com.njj.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "SelectAllByServiceServlet",urlPatterns = "/SelectAllByServiceServlet")
public class SelectAllByServiceServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接受登陆传过来的3个参数
        //1、修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");

        //调用service层
        UserService userService=new UserService();
        Map map = userService.selectAllByService();

        PrintWriter printWriter=resp.getWriter();
        String s= JSONObject.toJSONString(map);
        printWriter.println(s);
        printWriter.close();

    }
}
