package com.njj.dao;

import com.njj.bean.Visit;
import com.njj.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VisitDao {
    //新增
    public int insertVisit(Visit visit){

        //第1：创建链接对象
        Connection connection = DBHelper.getConnection();
        //因为添加的是变量用》？代替
        String sql="insert into t_visit values (null,?,?,?,?,?)";
        PreparedStatement ps=null;
        int i=0;
        try {
            ps=connection.prepareStatement(sql);
            ps.setInt(1,visit.getUser_id());
            ps.setInt(2,visit.getCust_id());
            ps.setString(3,visit.getVisit_desc());
            ps.setString(4,visit.getVisit_time());
            ps.setString(5,visit.getCreate_time());

            i=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return i;
    }
    public static void main(String[] args) {
       /* VisitDao visitDao = new VisitDao();
        Visit visit = new Visit();
        visit.setUser_id(33);
        visit.setCust_id(77);
        visit.setVisit_desc("测试拜访222");
        visit.setVisit_time("2021-08-05");
        visit.setCreate_time("2020-09-12");

      int i=  visitDao.insertVisit(visit);
        System.out.println("i = " + i);*/
    }
}
