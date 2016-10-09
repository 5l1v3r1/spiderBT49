package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
public class Db_op{
    private final static String driver="com.mysql.jdbc.Driver";
    private final static String url="jdbc:mysql://localhost:3306/kuying_db?useUnicode=true&&characterEncoding=UTF-8";
    private final static String user="root";
    private final static String pass="19910201nmkybuaa";
     
    static{
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
     
    public static Connection getConn(){
        Connection conn=null;
        try {
             conn = DriverManager.getConnection(url,user,pass);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
         
    }
     
    public static ResultSet excuteQuery(Connection conn,String sql){
        ResultSet rs = null;
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rs;
    }
    public static boolean excute(Connection conn,String sql){
        boolean rs = false;
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rs;
    }
     
    public static void close(Connection conn,PreparedStatement ps,ResultSet rs){
        if(rs!=null){
        try {
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }}
        if(ps!=null){
        try {
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }}
        if(conn!=null){
        try {
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }}
    }
}