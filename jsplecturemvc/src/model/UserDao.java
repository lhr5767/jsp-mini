package model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {

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

    //회원가입
    public int join(UserBean bean) {


        getCon();
        int count=0;

        try{
            String sql ="insert into user values(?,?,?,?,false)";
            pstmt=con.prepareStatement(sql);

            pstmt.setString(1,bean.getUserid());
            pstmt.setString(2, bean.getUserpassword());
            pstmt.setString(3,bean.getUseremail());
            pstmt.setString(4,bean.getUseremailhash());

           count = pstmt.executeUpdate(); //성공시 count에 1들어감

            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    //이메일 검증되었는지 확인
    public boolean getUserEmailChecked(String userid) {

        getCon();
        boolean check = false;

        try{
            String sql = "select useremailchecked from user where userid=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,userid);
            rs=pstmt.executeQuery();

            if(rs.next()) {
                check=rs.getBoolean(1);
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return check;
    }

    //이메일 검증하는 메서드 (인증시 true로 바꿔줌)
    public boolean setUserEmailChecked(String userid) {
        getCon();
        boolean check = false;
        int count = 0;

        try{
            String sql = "update user set useremailchecked = true where userid=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,userid);
            count = pstmt.executeUpdate();
            if(count == 1) {
                check = true;
            }else {
                check = false;
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return check;
    }

    //사용자의 이메일 가져오기
    public String getUserEmail(String userid) {
        getCon();
        String email="";

        try {
            String sql = "select useremail from user where userid=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,userid);
            rs=pstmt.executeQuery();

            if(rs.next()) {
                email = rs.getString(1);
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return email;
    }

    //로그인시 사용할 메서드
    public int login(String userid, String password) {
        getCon();
        int count = 0;
        try{
            String sql = "select userpassword from user where userid=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,userid);
            rs= pstmt.executeQuery();

            if(rs.next()) {
                if(rs.getString(1).equals(password)){
                    count = 1; //로그인 성공
                }else {
                    count = 0; //비밀번호 틀림
                }
            }else {
                count = -1; //아이디 없음
            }
            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }



}
