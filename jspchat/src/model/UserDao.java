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

    //로그인 메서드
    public int login(String userID, String userPassword) {
        getCon();
        int count = 0;
        try{
            String sql = "select * from user where userID=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,userID);
            rs= pstmt.executeQuery();

            if(rs.next()){ //db에서 가져온 패스워드와 사용자가 입력한 패스워드가 같은경우
                if(rs.getString("userPassword").equals(userPassword)){
                    count = 1; //로그인 성공
                }
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    //아이디 중복 체크
   public int registerCheck(String userID) {
        getCon();
        int count = 0;
        try{
            String sql = "select * from user where userID=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,userID);
            rs= pstmt.executeQuery();

            if(rs.next() || userID.equals("")){
                count = 0; //이미 존재하는 회원
            } else {
                count = 1; //가입 가능한 아이디
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return count;
   }
    //회원가입
   public int register(UserBean bean) {
        getCon();
        int count = 0;

        try{
            String sql = "insert into user values(?,?,?,?,?,?,?)";
            pstmt=con.prepareStatement(sql);

            pstmt.setString(1, bean.getUserID());
            pstmt.setString(2,bean.getUserPassword());
            pstmt.setString(3,bean.getUserName());
            pstmt.setInt(4,bean.getUserAge());
            pstmt.setString(5,bean.getUserGender());
            pstmt.setString(6,bean.getUserEmail());
            pstmt.setString(7,bean.getUserProfile());
            count = pstmt.executeUpdate(); //성공시 count에 1 들어감

            con.close();
        }catch (Exception e){
            e.printStackTrace();

        }
        return count;
   }

    //사용자정보 가져오기
    public UserBean getUser(String userID) {
        getCon();
        UserBean bean = new UserBean();
        try{
            String sql = "select * from user where userID=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,userID);
            rs= pstmt.executeQuery();

            if(rs.next()) {
                bean.setUserID(rs.getString(1));
                bean.setUserPassword(rs.getString(2));
                bean.setUserName(rs.getString(3));
                bean.setUserAge(rs.getInt(4));
                bean.setUserGender(rs.getString(5));
                bean.setUserEmail(rs.getString(6));
                bean.setUserProfile(rs.getString(7));
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return bean;
    }
    //사용자 정보 수정시 사용할 메서드 (Profile은 제외)
    public int update(UserBean bean) {
        getCon();
        int count = 0;

        try{
            String sql = "update user set userPassword=?,userName=?,userAge=?,userGender=?,userEmail=? where userID=?";
            pstmt= con.prepareStatement(sql);



            pstmt.setString(1,bean.getUserPassword());
            pstmt.setString(2,bean.getUserName());
            pstmt.setInt(3,bean.getUserAge());
            pstmt.setString(4,bean.getUserGender());
            pstmt.setString(5,bean.getUserEmail());
            pstmt.setString(6,bean.getUserID());

             count = pstmt.executeUpdate();
             con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    //프로필 변경시에 사용할 메서드
    public int profile(String userID, String userProfile) {
        getCon();
        int count = 0;

        try{
            String sql = "update user set userProfile = ? where userID=?";
            pstmt= con.prepareStatement(sql);

            pstmt.setString(1,userProfile);
            pstmt.setString(2,userID);


            count = pstmt.executeUpdate();
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    //프로필 이미지 보여줄때 사용할 메서드
    public String getProfile(String userID) {
        getCon();
        String profile ="";
        try{
            String sql = "select userProfile from user where userID=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,userID);
            rs= pstmt.executeQuery();

            //profile이 공백일때(처음 회원가입시 공백이 들어감)
            if(rs.next()) {
                if (rs.getString(1).equals("")) {
                    profile = "http://localhost:8080/img1/icon.jpg";
                } else {
                    profile = "http://localhost:8080/upload/" + rs.getString(1);
                }
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return profile;
    }
}
