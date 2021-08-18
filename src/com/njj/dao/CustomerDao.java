package com.njj.dao;

import com.njj.util.DBHelper;
import com.njj.util.PageBeanUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDao {
    //1.带参数的全查(2个表的)
    //t_user和t_customer表
    //select * from t_customer c join t_user u on c.user_id=u.id where 后面要带参数

    //思考1：问题：多个表联查 ，返回值是什么？？？ Map
    //思考2：问题：多个表联查，后面要带的参数 肯定是 多个表中的属性都有，那么用什么传参？ Map
    public List<Map> selectAllByParam(Map map){
        System.out.println("dao de map = " + map);
        for (Object o : map.keySet()) {
            System.out.println("o = " + o);
        }
        List lists=new ArrayList();
        String page = (String) map.get("page");
        String limit = (String) map.get("limit");
        String cust_name = (String) map.get("cust_name");
        String cust_phone = (String) map.get("cust_phone");
        String cust_sex = (String) map.get("cust_sex");
        String username = (String) map.get("username");
        String modify_time = (String) map.get("modify_time");

        //1.开连接
        Connection connection = DBHelper.getConnection();
        //2.写sql
        String sql="select c.* ,t.username as username , t.password as password ,  t.real_name as real_name , t.type as type   from t_customer c  join t_user  t  on c.user_id  = t.id  where 1=1 ";
        if(null!=cust_name&&cust_name.length()>0){
            sql=sql+" and c.cust_name like '%"+cust_name+"%'    ";
        }
        if(null!=cust_phone&&cust_phone.length()>0){
            sql=sql+" and c.cust_phone = "+cust_phone+"   ";
        }
        if(null!=cust_sex&&cust_sex.length()>0){
            sql=sql+" and c.cust_sex = "+cust_sex+"   ";
        }
        if(null!=username&&username.length()>0){
            sql=sql+" and t.username like '%"+username+"%'  ";
        }
        if(null!=modify_time&&modify_time.length()>0){
            sql=sql+" and c.modify_time like '%"+modify_time+"%'  ";
        }
        sql = sql + " and t.is_del=1  ";
        sql = sql + " limit ? , ?";
        System.out.println(" dao de limit sql = " + sql);
        PreparedStatement ps = null;
        ResultSet rs = null;
        PageBeanUtil pageBeanUtil = new PageBeanUtil(Integer.parseInt(page), Integer.parseInt(limit));
        //3.编译sql
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,pageBeanUtil.getStart());//这是索引
            ps.setInt(2,Integer.parseInt(limit));
            //4.执行sql
            rs = ps.executeQuery();
            while (rs.next()){
                Map dataMap=new HashMap();
                dataMap.put("id",rs.getInt("id"));
                dataMap.put("cust_name",rs.getString("cust_name"));
                dataMap.put("cust_company",rs.getString("cust_company"));
                dataMap.put("cust_position",rs.getString("cust_position"));
                dataMap.put("cust_phone",rs.getString("cust_phone"));
                dataMap.put("cust_birth",rs.getString("cust_birth"));
                dataMap.put("cust_sex",rs.getInt("cust_sex"));
                dataMap.put("user_id",rs.getInt("user_id"));
                dataMap.put("create_time",rs.getString("create_time"));
                dataMap.put("modify_time",rs.getString("modify_time"));
                dataMap.put("username",rs.getString("username"));
                dataMap.put("password",rs.getString("password"));
                dataMap.put("real_name",rs.getString("real_name"));
                dataMap.put("type",rs.getInt("type"));
                lists.add(dataMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lists;
    }



    //2.带参数的查询总条数(两个表的)
    //select count(*) from t_customer c join t_user u on c.user_id=u.id where 后面要带参数
    public int selectAllParamCount(Map map){
        String cust_name = (String) map.get("cust_name");
        String cust_phone = (String) map.get("cust_phone");
        String cust_sex = (String) map.get("cust_sex");
        String username = (String) map.get("username");
        String modify_time = (String) map.get("modify_time");

        int total=0;
        //1.创建链接
        Connection connection = DBHelper.getConnection();
        //2.写sql语句
        String sql = "select count(*)  total from t_customer c join t_user t on c.user_id = t.id where 1=1 ";
        if(null!=cust_name&&cust_name.length()>0){
            sql=sql+" and c.cust_name like '%"+cust_name+"%'    ";
        }
        if(null!=cust_phone&&cust_phone.length()>0){
            sql=sql+" and c.cust_phone = "+cust_phone+"   ";
        }
        if(null!=cust_sex&&cust_sex.length()>0){
            sql=sql+" and c.cust_sex = "+cust_sex+"   ";
        }
        if(null!=username&&username.length()>0){
            sql=sql+" and t.username like '%"+username+"%'  ";
        }
        if(null!=modify_time&&modify_time.length()>0){
            sql=sql+" and c.modify_time like '%"+modify_time+"%'  ";
        }
        System.out.println("sql = " + sql);
        //3.编译
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps  = connection.prepareStatement(sql);
            //4.执行
            rs = ps.executeQuery();
            if(rs.next()){
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    public static void main(String[] args) {
        CustomerDao customerDao=new CustomerDao();
        Map paramMap=new HashMap();
        paramMap.put("page","1");
       paramMap.put("limit","5");
       // paramMap.put("cust_name","李小花");
        //CustomerDao customerDao=new CustomerDao();
        List<Map> maps=customerDao.selectAllByParam(paramMap);
        System.out.println("maps = " + maps);
        System.out.println("maps.size() = " + maps.size());

        //总数
        int i=customerDao.selectAllParamCount(paramMap);
        System.out.println("i = " + i);
    }
}