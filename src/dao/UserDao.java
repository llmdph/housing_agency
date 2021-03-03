package dao;

import model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDao {

    //修改
    public static int Update(String adminName, String[] inputIn) throws Exception{

        Connection conn =  new ConnectDataDao().getConnection();
        String sql = "update manager set name =?,password=? where name = '"+adminName+"'";
        String sql1 = "ALTER table "+adminName+" rename to "+inputIn[0];

        PreparedStatement ps = conn.prepareStatement(sql);
        Statement stat = conn.createStatement();
        stat.execute(sql1);

        ps.setString(1,inputIn[0]);
        ps.setString(2,inputIn[1]);
        int i = ps.executeUpdate();
        ps.close();
        conn.close();
        return i;
    }

    //删除
    public static int Delete(String name,int id) throws  Exception{
        Connection conn =  new ConnectDataDao().getConnection();
        Statement stat = conn.createStatement();
        String sql = "delete from manager where ID ="+id;
        String sql1 = "drop table "+name;
        int i =stat.executeUpdate(sql);
        stat.execute(sql1);
        stat.close();
        conn.close();
        return i;
    }

    //登录
    public Admin auth(String name,String pw)throws Exception{


        Connection conn =  new ConnectDataDao().getConnection();
        Statement stat = conn.createStatement();
        Admin admin = new Admin();
        String sql = "select * from manager where name = '"+name+"' and password = '"+pw+"'";
        ResultSet rs = stat.executeQuery(sql);
        if(rs.next()){
            admin.setId(rs.getInt(1));
            admin.setName(rs.getString(2));
            admin.setPassword(rs.getString(3));
        }
        rs.close();
        conn.close();
        return admin;
    }

    //注册
    public static int register(String name, String pwd) throws Exception{

        Connection conn =  new ConnectDataDao().getConnection();
        String sql = "insert into manager(name,password) values(?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        String sql1 = "CREATE TABLE "+name+" LIKE yan";
        Statement stat = conn.createStatement();
        stat.execute(sql1);

        ps.setString(1,name);
        ps.setString(2,pwd);
        int i = ps.executeUpdate();
        System.out.println(i);
        ps.close();
        conn.close();
        return i;
    }

    //超级管理员查询
    public static ArrayList  queryAllAdmin(String inputIn) throws Exception{

        String name ="'%"+inputIn+"%'";
        if (name.equals("''")){
            name ="name or 1=1";
        }

        ArrayList admins = new ArrayList();
        Connection conn =  new ConnectDataDao().getConnection();
        Statement stat = conn.createStatement();
        String sql = "select * from manager where name like "+name;
        ResultSet rs = stat.executeQuery(sql);
        while (rs.next()){
            Admin admin = new Admin();
            admin.setId(rs.getInt(1));
            admin.setName(rs.getString(2));
            admin.setPassword(rs.getString(3));
            admins.add(admin);
        }
        rs.close();
        conn.close();
        return admins;
    }


}
