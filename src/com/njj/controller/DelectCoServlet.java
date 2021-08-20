package com.njj.controller;

import com.alibaba.fastjson.JSON;
import com.njj.bean.Customer;
import com.njj.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet(name = "DelectCoServlet",urlPatterns = "/DelectCoServlet")
public class DelectCoServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //2.接收前端传来的参数
        String id=req.getParameter("id");
        System.out.println("id = " + id);

        Customer customer = new Customer();
        customer.setId(Integer.parseInt(id));

        CustomerService customerService = new CustomerService();
        int map = customerService.deletecustomer(Integer.parseInt(id));
        System.out.println("map = " + map);

        String s= JSON.toJSONString(map);
        System.out.println("s = " + s);

        PrintWriter printWriter=resp.getWriter();
        printWriter.println(s);
        printWriter.close();
    }
}
