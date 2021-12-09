package model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BoardDAO {


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

    //현재 시간 가져오기
    public String getDate() {
        getCon();
        String date ="";

        try {
            String sql = "select now()";
            pstmt=con.prepareStatement(sql);
            rs= pstmt.executeQuery();
            if(rs.next()) {
                date = rs.getString(1);
            }
            con.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    //게시글 쓰기
    public void write(String title, String userid, String content) {
        getCon();
        //int count = 0;
        try{
            String sql = "insert into freeboard values(null,?,?,now(),?,1)";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,title);
            pstmt.setString(2,userid);
            pstmt.setString(3,content);

            pstmt.executeUpdate();
            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //모든 게시글 가져오기 페이징 처리 까지 같이
    public ArrayList<BoardBean> getAllBoard(int start, int end) {
        ArrayList<BoardBean> list = new ArrayList<>();

        getCon();

        try{
            String sql = "select * from freeboard where delcheck=1 order by bnum desc limit ?,?";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,start-1);
            pstmt.setInt(2,end);
            rs= pstmt.executeQuery();

            while (rs.next()){
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

    //전체 게시글 갯수 가져오기
    public int getAllCount() {
        getCon();
        int count = 0;

        try {
            String sql="select count(*) from freeboard";
            pstmt=con.prepareStatement(sql);
            rs= pstmt.executeQuery();

            if(rs.next()) {
                count = rs.getInt(1);
            }

            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    //하나의 게시글 정보 가져오기
    public BoardBean getOneBoard(int bnum) {
        BoardBean bean = new BoardBean();
        getCon();
        try{
            String sql = "select * from freeboard where bnum=?";
            pstmt= con.prepareStatement(sql);
            pstmt.setInt(1,bnum);
            rs= pstmt.executeQuery();

            if(rs.next()) {

                bean.setBnum(rs.getInt(1));
                bean.setTitle(rs.getString(2));
                bean.setUserid(rs.getString(3));
                bean.setDate(rs.getDate(4).toString());
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

        try
        {
            String sql = "update freeboard set title =?, content=? where bnum=?";
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

    //게시글 삭제 메서드 (delcheck 을 0으로 바꾸기 실제 DB에서 데이터 날리는것이 아님)
    public void delete(int bnum) {
        getCon();

        try{
            String sql= "update freeboard set delcheck = 0 where bnum=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,bnum);

            pstmt.executeUpdate();

            con.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
