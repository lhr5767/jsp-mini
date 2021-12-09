package model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LikeyDAO {

    Connection con;
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

    public int like(String userID, String evaluationID, String userIP) {
        int count = 0;
        getCon();

        try{
            String sql = "insert into likey values(?,?,?)";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,userID);
            pstmt.setInt(2,Integer.parseInt(evaluationID));
            pstmt.setString(3,userIP);

            count = pstmt.executeUpdate();

            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
