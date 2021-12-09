package model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LikeyDao {

    Connection con ;
    PreparedStatement pstmt;
    ResultSet rs;


    public void getCon() {
        try{
            Context ini = new InitialContext();
            Context emvctx = (Context) ini.lookup("java:comp/env");
            DataSource ds = (DataSource) emvctx.lookup("jdbc/mysql");

            con = ds.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int like(String userid, String evaluationid, String userip) {
        getCon();
        int count = 0;
        try{
            String sql = "insert into likey values(?,?,?)";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,userid);
            pstmt.setInt(2,Integer.parseInt(evaluationid));
            pstmt.setString(3,userip);

            count=pstmt.executeUpdate();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }
}
