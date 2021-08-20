package com.njj.service;

import com.njj.bean.Customer;
import com.njj.dao.CustomerDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerService {
    //全查
    public  Map selectAllByParam(Map map){
        CustomerDao dao = new CustomerDao();
        List<Map> maps = dao.selectAllByParam(map);
        Map codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("data",maps);
        codeMap.put("msg","ok");
        Map countMap = selectAllByParamCount(map);
        int count = (int) countMap.get("data");
        codeMap.put("count",count);
        return  codeMap;
    }

    //全查总条数  多表的
    public  Map selectAllByParamCount(Map map){
        Map codeMap=new HashMap();
        CustomerDao dao = new CustomerDao();
        int i = dao.selectAllParamCount(map);
        codeMap.put("code",0);
        codeMap.put("data",i);
        codeMap.put("msg","ok");
        return codeMap;
    }
    /*//修改
    public Map delCustomer(Customer customer){
        System.out.println("进入 删除的 service");
        Map map1 = new HashMap();
        // service 层要调用dao层
        CustomerDao customerDao = new CustomerDao();
        int i= customerDao.deletecustomer(3);
        if(i>0){
            //System.out.println(" 提交成功" );
            map1.put("code", 0);
            map1.put("msg", "删除成功");
        }else{
            //System.out.println(" 提交失败" );
            map1.put("code",4004);
            map1.put("msg","删除失败");
        }
        return  map1;

    }*/

    //添加
    public Map insertCustomer(Customer customer){
        CustomerDao dao =new CustomerDao();
        int i = dao.insertCustomer(customer);
        Map codeMap=new HashMap();
        System.out.println("i = " + i);
        if (i==1){
            codeMap.put("code",0);
            codeMap.put("msg","添加成功");
        }else{
            codeMap.put("code",400);
            codeMap.put("msg","添加失败");
        }
        return codeMap;
    }

    //删除
    public int deletecustomer(Integer id){
        CustomerDao dao =new CustomerDao();
        int i = dao.deletecustomer(id);
        return i;
    }
}
