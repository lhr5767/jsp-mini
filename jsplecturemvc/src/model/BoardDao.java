package model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BoardDao {

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

    //전체 게시글 가져오기
    public ArrayList<BoardBean> getAllBoard(int start, int end) {
        ArrayList<BoardBean> list = new ArrayList<>();

        getCon();

        try{
            //delcheck = 0 은 삭제된 게시물이므로 가져오지 않음
            String sql ="select * from freeboard where delcheck=1 order by bnum desc limit ?,?";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,start-1);
            pstmt.setInt(2,end);
            rs= pstmt.executeQuery();

            while(rs.next()) {
                BoardBean bean = new BoardBean();
                bean.setBnum(rs.getInt(1));
                bean.setTitle(rs.getString(2));
                bean.setUserid(rs.getString(3));
                bean.setDate(rs.getDate(4).toString());
                bean.setContent(rs.getString(5));
                bean.setDelcheck(rs.getInt(6));

                list.add(bean);
            }

            con.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //전체 게시글의 개수 가져오기
    public int getAllCount() {
        getCon();
        int count = 0;

        try{
            String sql = "select count(*) from freeboard";
            pstmt=con.prepareStatement(sql);
            rs= pstmt.executeQuery();

            if(rs.next()) {
                count=rs.getInt(1);
            }
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    //게시글 작성 메서드
    public void write(String title, String userid, String content) {
        getCon();
        try{
            String sql = "insert into freeboard values( null,?,?,now(),?,1)";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,title);
            pstmt.setString(2,userid);
            pstmt.setString(3,content);

            pstmt.executeUpdate();

            con.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //하나의 게시글 정보 가져오기
    public BoardBean getOneBoard(int bnum) {
        getCon();
        BoardBean bean = new BoardBean();
        try{
            String sql = "select * from freeboard where bnum =?";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,bnum);
            rs= pstmt.executeQuery();

            if(rs.next()) {
                bean.setBnum(rs.getInt(1));
                bean.setTitle(rs.getString(2));
                bean.setUserid(rs.getString(3));
                bean.setDate(rs.getString(4));
                bean.setContent(rs.getString(5));
                bean.setDelcheck(rs.getInt(6));
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }

    //게시글 수정시 필요한 메서드
    public void update(int bnum, String title, String content) {
        getCon();
        try{
            String sql ="update freeboard set title =? , content=? where bnum=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,title);
            pstmt.setString(2,content);
            pstmt.setInt(3,bnum);

            pstmt.executeUpdate();
            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //userid 가져오기
    public String getUserId(int bnum) {
        String userid ="";
        getCon();

        try{

            String sql = "select userid from freeboard where bnum=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,bnum);
            rs=pstmt.executeQuery();

            if(rs.next()) {
                userid = rs.getString(1);
            }
            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return userid;
    }

    //게시글 삭제 메서드 (db에서 데이터 날리지 않고 delcheck 을 0으로 변경)
    public void delete(int bnum) {
        getCon();


        try{

            String sql = "update freeboard set delcheck = 0 where bnum =?";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,bnum);

            pstmt.executeUpdate();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
