package com.njj.dao;

import com.njj.bean.User;
import com.njj.util.DBHelper;
import com.njj.util.PageBeanUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDao {
    //增删改查
    //登录select * from t_user where username=? and password=?
    public  User login(String username,String password){
        User user=null;
        //1创建连接
        Connection connection = DBHelper.getConnection();
        //2、创建sql
        String sql= "select * from t_user where username=? and password=? ";

        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            //3、使用链接对象获取预编译对象
            ps= connection.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            //4、执行预编译第项
            rs= ps.executeQuery();
             if(rs.next()){
                 user = new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));

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
        return user;
    }


    //全查询 查询所有业务员
    public List<User> selectAllByService(){
        //dao层如何和数据库做对接 ，我们用的知识点叫做jdbc
        //要链接数据库就需要到dbhelper.getconnection()
        //1、步骤1、创建链接对象
        List<User> users=new ArrayList<>();
        Connection connection = DBHelper.getConnection();
        //2、创建sql语句
        String sql="select * from t_user where type=2";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            //3、使用链接对象获取预编译对象
             ps= connection.prepareStatement(sql);
            System.out.println("ps = " + ps);
            //4、执行预编译对象，得出结果集
             rs= ps.executeQuery();
             //5、遍历结果集,一一的取对象
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
                users.add(user);
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
       return  users;
    }

    //动态的带参数的分页查询 m是页数 page  n是limit
    public List<User> selectAllByParam(Map map){
        System.out.println("map dao= " + map);
        for (Object o : map.keySet()) {
            System.out.println("o = " + o);
        }
        String page =(String) map.get("page");//接收前端参数放入到map中
        String limit =(String) map.get("limit");

        String real_name =(String) map.get("real_name");
        String type =(String) map.get("type");
        String username =(String) map.get("username");
        //如果real_name不为空   （动态sql 1个sql 实现多个查询）
        //sql=select * from t_user where real_name like %张% limit ?,?

        //如果real_name不为空  type不为空
        //sql=select * from t_user where real_name like %张% and type=1  limit ?,?

        //如果real_name不为空  type不为空  username不为空
        //sql=select * from t_user where real_name like %张% and type=1  and username like %李%  limit ?,?


        List<User> lists = new ArrayList<>();
        //1、步骤1、创建链接对象
        Connection connection = DBHelper.getConnection();
        //2、创建sql语句
        /*String sql="select * from t_user where +" +
                "             " +
                "  " +
                " +  limit ?,? ";*/
        String sql="select * from t_user where 1=1";//因为是多余的
        if(null!=real_name &&real_name.length()>0){
            sql=sql +" and real_name like  '%"+real_name+"%'  ";
        }

        if(null!=type &&type.length()>0){
            sql=sql +" and type  ="+type+" ";
        }

        if(null!=username &&username.length()>0){
            sql=sql +" and username like  '%"+username+"%'  ";
        }
        sql = sql + " limit  ? , ? ";
        System.out.println("dao de sql = " + sql);
        PreparedStatement ps=null;
        ResultSet rs=null;
        PageBeanUtil pageBeanUtil = new PageBeanUtil(Integer.parseInt(page),Integer.parseInt(limit));
        try {
            //3、使用链接对象获取预编译对象
            ps= connection.prepareStatement(sql);
            ps.setInt(1,pageBeanUtil.getStart());
            ps.setInt(2,Integer.parseInt(limit));
            //4、执行预编译对象，得出结果集
            rs= ps.executeQuery();
            //5、遍历结果集,一一的取对象
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
                lists.add(user);
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
        return lists;
    }

    //查询总条数
    public  int selectCount(Map map1){
        String real_name =(String) map1.get("real_name");
        String type =(String) map1.get("type");
        String username =(String) map1.get("username");
        //1、步骤1、创建链接对象
        Connection connection = DBHelper.getConnection();
        //2、创建sql语句
        String sql="select count(*) total from t_user where 1=1 ";

        if(null!=real_name &&real_name.length()>0){
            sql=sql +" and real_name like  '%"+real_name+"%'  ";
        }

        if(null!=type &&type.length()>0){
            sql=sql +" and type  ="+type+" ";
        }

        if(null!=username &&username.length()>0){
            sql=sql +" and username like  '%"+username+"%'  ";
        }
        System.out.println("sql count 的 = " + sql);
        PreparedStatement ps=null;
        ResultSet rs=null;
        int total=0;
        try {
            //3、使用链接对象获取预编译对象
            ps= connection.prepareStatement(sql);
            //4、执行预编译对象，得出结果集
            rs= ps.executeQuery();
            //5、遍历结果集,一一的取对象
            if (rs.next()){
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
    //新增
    public int addUser(User user){
        //1、步骤1、创建链接对象
        Connection connection = DBHelper.getConnection();
        //2、sql语句因为添加的数据是变量 ，所以要用?代替
        String sql="insert into t_user values (null,?,?,?,?,?,?,?,?)";
        PreparedStatement ps=null;
        int i=0;
        try {
            //3、预编译
           ps = connection.prepareStatement(sql);
           ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getReal_name());
            ps.setString(4,user.getImg());
            ps.setInt(5,user.getType());
            ps.setInt(6,user.getIs_del());
            ps.setString(7,user.getCreate_time());
            ps.setString(8,user.getModify_time());
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
        return  i;
    }
    //修改全部
    public int updateUser(User user){
        /*//1、步骤1、创建链接对象
        Connection connection = DBHelper.getConnection();
        //2、sql语句
        String sql="update t_user set username=?,password=?,real_name=?,img=?,type=?,is_del=?,create_time=?,modify_time=? where id=?";
        PreparedStatement ps=null;
        int i=0;
        try {
            //3、预编译
            ps = connection.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getReal_name());
            ps.setString(4,user.getImg());
            ps.setInt(5,user.getType());
            ps.setInt(6,user.getIs_del());
            ps.setString(7,user.getCreate_time());
            ps.setString(8,user.getModify_time());
            ps.setInt(9,user.getId());
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
        return  i;*/


        //1、步骤1、创建链接对象
        Connection connection = DBHelper.getConnection();
        //2、sql语句
      //String sql="update t_user set id=?,  username=?,password=?,real_name=?,img=?,type=?,is_del=?,create_time=?,modify_time=? where id=?";
        String sql="update t_user set id=?,  username=?,password=?,real_name=?,type=?,is_del=?,modify_time=? where id=?";
        PreparedStatement ps=null;
        int i=0;
        try {
            //3、预编译
            ps = connection.prepareStatement(sql);
            ps.setInt(1,user.getId());
            ps.setString(2,user.getUsername());
            ps.setString(3,user.getPassword());
            ps.setString(4,user.getReal_name());

            ps.setInt(5,user.getType());
            ps.setInt(6,user.getIs_del());

            ps.setString(7,user.getModify_time());
            ps.setInt(8,user.getId());
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
        return  i;


    }

    //修改id
    public int updateUserById(Integer sfDel,Integer userId){
        //1、步骤1、创建链接对象
        Connection connection = DBHelper.getConnection();
        //2、sql语句因为添加的数据是变量 ，所以要用?代替
        String sql="update t_user set is_del=? where id=?";
        PreparedStatement ps=null;
        int i=0;
        try {
            //3、预编译
            ps = connection.prepareStatement(sql);
            ps.setInt(1,sfDel);
            ps.setInt(2,userId);
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
        return  i;

    }
    //根据id查询!!!!!!
    public User selectUserById(Integer id) {
        User user= new User();
        //1、步骤1、创建链接对象
        Connection connection = DBHelper.getConnection();
        //2、sql语句因为添加的数据是变量 ，所以要用?代替
        String sql="select * from   t_user where id=?";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            //3、预编译
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){

                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
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
        return  user;
    }
    //删除
    public int deleteUser(int id){
        //1、步骤1、创建链接对象
        Connection connection = DBHelper.getConnection();
        //2、sql语句因为添加的数据是变量 ，所以要用?代替
        String sql="delete from  t_user where id=?";

        PreparedStatement ps=null;
        int i=0;
        try {
            //3、预编译
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
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
        return  i;
    }

    public static void main(String[] args) {
        //全查
       UserDao dao=new UserDao();
/*        List<User> users=dao.selectAll();
        for (User user : users) {
            System.out.println("user = " + user);
        }*/

       /* //新增
        User user = new User();
        user.setUsername("小宁");
        user.setType(1);
        user.setReal_name("宁静静");
        user.setPassword("123");
        user.setModify_time("2021-09-07");
        user.setIs_del(1);
        user.setImg("xxxx");
        user.setCreate_time("2021-08-09");
        int i=dao.addUser(user);
        System.out.println("i = " + i);
*/

       /* //删除
        int i=dao.deleteUser(7);
        System.out.println("i = " + i);*/

       /*//修改
        User user = new User();
        user.setId(12);
        user.setUsername("小小");
        user.setType(1);
        user.setReal_name("程小小");
        user.setPassword("1234567");
        user.setModify_time("2029-12-07 11:33:54");
        user.setIs_del(1);
        user.setImg("xxxx");
        user.setCreate_time("2020-07-19");
        int i=dao.updateUser(user);
        System.out.println("i = " + i);*/


//       //登录
//        User abc = dao.login("test", "123456");
//        System.out.println("abc = " + abc);


        /*//分页的测试
        List<User> users = dao.selectAllByParam(2, 5);
        System.out.println("users = " + users);
        System.out.println("users = " + users.size());*/

      /*  //测试总条数
        int i = dao.selectCount();
        System.out.println("i = " + i);*/


       /* //修改id
        int up2 = dao.updateUserById(2, 39);
        System.out.println("up2 = " + up2);*/
    }
}
