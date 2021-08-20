package com.njj.service;

import com.njj.bean.Visit;
import com.njj.dao.VisitDao;

import java.util.HashMap;
import java.util.Map;

public class VisitService {
    //添加
    public Map insertVisit(Visit visit){
        VisitDao dao=new VisitDao();

        int i=dao.insertVisit(visit);

        Map codeMap=new HashMap();
        System.out.println("i = " + i);
        if (i==1){
            codeMap.put("code",0);
            codeMap.put("msg","ok");
            return codeMap;
        }else{
            codeMap.put("code",400);
            codeMap.put("msg","no");
            return codeMap;

           }
        }
    }
