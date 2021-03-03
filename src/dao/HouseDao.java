package dao;

import com.sun.xml.internal.bind.v2.model.core.ID;
import model.House;

import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;

public class HouseDao {
    public HouseDao() {
    }

    //查询
    public static ArrayList queryAllHouses(String admin,String[] inputIn) throws Exception{


        String name ="'%"+inputIn[0]+"%'";
        String site ="'%"+inputIn[1]+"%'";
        Object area = inputIn[2];//int
        String direct ="'%"+inputIn[3]+"%'";
        Object price = inputIn[4];//int
        Object rs1 = inputIn[5];//int
        Object state =inputIn[6];//int

        if (name.equals("''")){
             name ="name or 1=1";
        }
        if (site.equals("''")){
            site ="site or 1=1";
        }
        if (area.equals("")){
            area ="area or 1=1";
        }else {
             area = Integer.parseInt((String) area);
        }
        if (direct.equals("''")){
            direct ="direct or 1=1";
        }
        if (price.equals("")){
            price ="price or 1=1";
        }else {
             price =Integer.parseInt((String) price);
        }
        if (rs1.equals("")){
            rs1 ="price or 1=1";
        }else {
             rs1 = Integer.parseInt((String) rs1);
        }
        if (state.equals("")){
             state ="price or 1=1";
        }else {
             state = Integer.parseInt((String) state);
        }


        ArrayList houses = new ArrayList();
        Connection conn =  new ConnectDataDao().getConnection();
        Statement stat = conn.createStatement();
        String sql = "SELECT * FROM "+admin+" where (name like "+name+
                ")and (site like "+site+
                ")and (area = "+area+
                ")and (direct like "+direct+
                ")and (price = "+price+
                ")and (rs = "+rs1+
                ")and (state = "+state+")";

        ResultSet rs = stat.executeQuery(sql);
        while (rs.next()){
            House house = new House();
            house.setId(rs.getInt(1));
            house.setName(rs.getString(2));
            house.setPhone(rs.getString(3));
            house.setSite(rs.getString(4));
            house.setArea(rs.getInt(5));
            house.setDirect(rs.getString(6));
            house.setPrice(rs.getInt(7));
            house.setRs(rs.getInt(8));
            house.setState(rs.getInt(9));
            house.setTime(rs.getString(10));
            houses.add(house);
        }
        rs.close();
        conn.close();
        return houses;
    }

    //修改
    public static int Update(String admin,int id,String[] inputIn) throws Exception{
        String name =inputIn[0];
        String phone =inputIn[1];
        String site =inputIn[2];
        int area = Integer.parseInt(inputIn[3]);
        int price = Integer.parseInt(inputIn[4]);
        String direct =inputIn[5];
        int rs = Integer.parseInt(inputIn[6]);
        int state = Integer.parseInt(inputIn[7]);

        Connection conn =  new ConnectDataDao().getConnection();
        String sql = "update  "+admin+" set name =?,phone=?,site=?,area=?,price=?,direct=?," +
                "rs=?,state=? where id = "+id;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,name);
        ps.setString(2,phone);
        ps.setString(3,site);
        ps.setInt(4,area);
        ps.setInt(5,price);
        ps.setString(6,direct);
        ps.setInt(7,rs);
        ps.setInt(8,state);
        int i=ps.executeUpdate();
        ps.close();
        conn.close();
        return i;
    }

    //增加
    public static int Insert(String admin, String[] inputIn) throws Exception{

        for(int j= 0;j<inputIn.length;j++){
            System.out.println(inputIn[j]);
        }
        Connection conn =  new ConnectDataDao().getConnection();
        String sql = "insert into "+admin+"(name,phone,site,area,direct,price,rs,state,time)" +
                " values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,inputIn[0]);
        ps.setString(2,inputIn[1]);
        ps.setString(3,inputIn[2]);
        ps.setInt(4, Integer.parseInt(inputIn[3]));
        ps.setString(5,inputIn[5]);
        ps.setInt(6, Integer.parseInt(inputIn[4]));
        ps.setInt(7, Integer.parseInt(inputIn[6]));
        ps.setInt(8, Integer.parseInt(inputIn[7]));
        ps.setDate(9, Date.valueOf(inputIn[8]));
        int i = ps.executeUpdate();
        System.out.println(i);
        ps.close();
        conn.close();
        return i;
    }

    //删除
    public static int Delete(String admin,int id) throws Exception {
        System.out.println(admin);
        Connection conn =  new ConnectDataDao().getConnection();
        Statement stat = conn.createStatement();
        String sql = "delete from "+admin+" where ID ="+id;

        int i = stat.executeUpdate(sql);
        stat.close();
        conn.close();
        return i;
    }

    //月绩
    public static ArrayList queryMonth(String admin) throws Exception{
        ArrayList houses = new ArrayList();
        Connection conn =  new ConnectDataDao().getConnection();
        Statement stat = conn.createStatement();
        String sql = "SELECT * FROM "+admin+" " +
                "WHERE DATE_FORMAT(TIME,'%Y%m' ) = DATE_FORMAT(CURDATE(),'%Y%m') and state = 1";

        ResultSet rs = stat.executeQuery(sql);
        while (rs.next()){
            House house = new House();
            house.setId(rs.getInt(1));
            house.setName(rs.getString(2));
            house.setPhone(rs.getString(3));
            house.setSite(rs.getString(4));
            house.setArea(rs.getInt(5));
            house.setDirect(rs.getString(6));
            house.setPrice(rs.getInt(7));
            house.setRs(rs.getInt(8));
            house.setState(rs.getInt(9));
            house.setTime(rs.getString(10));
            houses.add(house);
        }
        rs.close();
        conn.close();
        return houses;
    }


    public static ArrayList queryYear(String admin) throws Exception {
        ArrayList houses = new ArrayList();
        Connection conn =  new ConnectDataDao().getConnection();
        Statement stat = conn.createStatement();
        String sql = "SELECT * FROM "+admin+" " +
                "WHERE YEAR(TIME )=YEAR(NOW()) and state = 1";

        ResultSet rs = stat.executeQuery(sql);
        while (rs.next()){
            House house = new House();
            house.setId(rs.getInt(1));
            house.setName(rs.getString(2));
            house.setPhone(rs.getString(3));
            house.setSite(rs.getString(4));
            house.setArea(rs.getInt(5));
            house.setDirect(rs.getString(6));
            house.setPrice(rs.getInt(7));
            house.setRs(rs.getInt(8));
            house.setState(rs.getInt(9));
            house.setTime(rs.getString(10));
            houses.add(house);
        }
        rs.close();
        conn.close();
        return houses;
    }
}

