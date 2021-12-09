package model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class MemberDAO {

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

    //한 회원에 대한 정보 저장
    public void insertMember(MemberBean bean) {

        getCon();

        try {
            String sql = "insert into member values(?,?,?,?,?,?,?,?)";

            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,bean.getId());
            pstmt.setString(2,bean.getPass1());
            pstmt.setString(3,bean.getEmail());
            pstmt.setString(4,bean.getTel());
            pstmt.setString(5,bean.getHobby());
            pstmt.setString(6,bean.getJob());
            pstmt.setString(7,bean.getAge());
            pstmt.setString(8,bean.getInfo());

            pstmt.executeUpdate();
            con.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //모든 회원 정보 가져오기
    public Vector<MemberBean> getAllMember() {

        Vector<MemberBean> v = new Vector<>();

        getCon();
        try{
            String sql = "select * from member";
            pstmt = con.prepareStatement(sql);
            rs= pstmt.executeQuery();

            while(rs.next()){
                MemberBean bean = new MemberBean();
                bean.setId(rs.getString(1));
                bean.setPass1(rs.getString(2));
                bean.setEmail(rs.getString(3));
                bean.setTel(rs.getString(4));
                bean.setHobby(rs.getString(5));
                bean.setJob(rs.getString(6));
                bean.setAge(rs.getString(7));
                bean.setInfo(rs.getString(8));

                v.add(bean);
            }
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }
}
