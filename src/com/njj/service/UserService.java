package com.njj.service;

import com.njj.bean.User;
import com.njj.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    //登录
public Map  login(String username, String password, HttpServletRequest request){
    Map map=new HashMap();
    //service 层要调用dao层
    UserDao dao = new UserDao();
    User userFromDB = dao.login(username, password);
    if(null==userFromDB){
        map.put("code",4001);
        map.put("msg","账户名或者密码不正确");
        return map;
    }else {
        HttpSession session=request.getSession();
        session.setAttribute("user",userFromDB);
        map.put("code",0);
        map.put("msg","登录成功");
        //给一个账户  给前端渲染
        map.put("username",username);
        return map;
    }
  }

  //带参数的分页查询
    public  Map selectAllByParam(Map map1){
        UserDao userDao = new UserDao();
        List<User> users = userDao.selectAllByParam(map1);
        int i = userDao.selectCount(map1);
        Map map = new HashMap();
        map.put("code",0);//必须和layui的JSON返回的格式一样
        map.put("msg","查询成功");
        map.put("count",i);//把数据写活
        map.put("data",users);

        //根据layui的返回的JSON 格式去封装给你的数据 如果不一样需要layui解析
        /*{
            code:0,
            msg:"",
            count:1000,
            data:[每一条数据]

        }*/

        Map map2 = new HashMap();
        map2.put("number",2001);//必须和layui的JSON返回的格式一样
        map2.put("message","数据查询成功");
        map2.put("object",map);//把数据写活


        return map2;
    }

    //修改id 是否可用
    public Map updateUserById(Integer sfDel,Integer userId){
        UserDao dao = new UserDao();
        int i=dao.updateUserById(sfDel,userId);
        Map map = new HashMap();
        if(i==1){
            map.put("code",0);//必须和layui的JSON返回的格式一样
            map.put("msg","修改成功");
        }else{
            map.put("code",400);//必须和layui的JSON返回的格式一样
            map.put("msg","修改不成功");
        }
        return map;
    }

    //修改全部的user
    public Map updateUser(User user){
        Map codeMap = new HashMap();
        // service 层要调用dao层
        UserDao dao = new UserDao();
        //System.out.println("dao = " + dao);

        int i= dao.updateUser(user);
        //System.out.println("i = " + i);
        if(i==1){
            //System.out.println(" 提交成功" );
            codeMap.put("code", 0);
            codeMap.put("msg", "修改成功");
        }else{
            //System.out.println(" 提交失败" );
            codeMap.put("code",400);
            codeMap.put("msg","修改失败");
        }
        return  codeMap;

    }

    //按照id查询1个user
    public Map selectUserById(Integer id){
        UserDao userDao = new UserDao();
        User user = userDao.selectUserById(id);
        Map codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("msg","ok");
        codeMap.put("data",user);
        return codeMap;
    }
   /* //新增
    public Map insertByUser(User user){
        Map map = new HashMap();
        // service 层要调用dao层
        UserDao dao = new UserDao();
        System.out.println("dao = " + dao);
        System.out.println("user = " + user);

        int a= dao.addUser(user);
        System.out.println("a = " + a);
        if(a>0){
            System.out.println(" 提交成功" );
            map.put("code", 0);
            map.put("msg", "添加成功");
        }else{
            System.out.println(" 提交失败" );
            map.put("code",4002);
            map.put("msg","添加失败");
        }
        return  map;
    }*/

//全查业务员
    public  Map selectAllByService(){
        UserDao dao = new UserDao();
        List<User> users = dao.selectAllByService();
        Map codemap = new HashMap();
        codemap.put("code",0);//必须和layui的JSON返回的格式一样
        codemap.put("msg","ok");
        codemap.put("data",users);
        return codemap;
    }
}

