package com.njj.controller;

import com.alibaba.fastjson.JSONObject;
import com.njj.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "DelAllCustomerServlet",urlPatterns = "/DelAllCustomerServlet")
public class DelAllCustomerServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 修正 编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");

        //重点servlet收取数组
        String[] parameterValues = req.getParameterValues("ids[]");
        System.out.println("parameterValues = " + parameterValues);
        CustomerService service = new CustomerService();
        for (String idStr : parameterValues) {
           int i= service.deletecustomer(Integer.parseInt(idStr));
            System.out.println("i = " + i);
        }
        Map codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("msg","ok");

        String s = JSONObject.toJSONString(codeMap);
        PrintWriter writer = resp.getWriter();
        writer.println(s);//
        writer.close();
    }
}
