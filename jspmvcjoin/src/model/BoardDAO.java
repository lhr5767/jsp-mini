package model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

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

    public int getAllCount() {

        getCon();
        int count = 0;

        try {
            String sql = "select count(*) from board";
            pstmt = con.prepareStatement(sql);
            rs= pstmt.executeQuery();

            if(rs.next()) {
                count = rs.getInt(1);
            }
            con.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    //화면에 보여질 데이터를 10개씩 추출해 리턴
    public Vector<BoardBean> getAllBoard(int startRow, int endRow) {

        Vector<BoardBean> v = new Vector<>();

        getCon();

        try{
            String sql = "select * from board order by ref desc, re_step asc limit ?,?";
            pstmt= con.prepareStatement(sql);
            pstmt.setInt(1,startRow-1);
            pstmt.setInt(2,endRow);

            rs= pstmt.executeQuery();

            while (rs.next()){
                BoardBean bean = new BoardBean();
                bean.setNum(rs.getInt(1));
                bean.setWriter(rs.getString(2));
                bean.setEmail(rs.getString(3));
                bean.setSubject(rs.getString(4));
                bean.setPassword(rs.getString(5));
                bean.setReg_date(rs.getDate(6).toString());
                bean.setRef(rs.getInt(7));
                bean.setRe_step(rs.getInt(8));
                bean.setRe_level(rs.getInt(9));
                bean.setReadcount(rs.getInt(10));
                bean.setContent(rs.getString(11));

                v.add(bean);
            }
            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return v;
    }

    //하나의 게시글 저장
    public void insertBoard(BoardBean bean) {

        getCon();
        int ref = 0;
        int re_step = 1; //새글
        int re_level = 1; //새글
        try{
            String refsql = "select max(ref) from board";
            pstmt = con.prepareStatement(refsql);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                ref = rs.getInt(1)+1;
            }
            //데이터 넣는 쿼리
            String sql = "insert into board values(null,?,?,?,?,now(),?,?,?,0,?)";
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, bean.getWriter());
            pstmt.setString(2, bean.getEmail());
            pstmt.setString(3,bean.getSubject());
            pstmt.setString(4,bean.getPassword());
            pstmt.setInt(5,ref);
            pstmt.setInt(6,re_step);
            pstmt.setInt(7,re_level);
            pstmt.setString(8,bean.getContent());

            pstmt.executeUpdate();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //하나의 게시글 가져오기 (조회수도 올라가게끔 해주기)
    public BoardBean getOneBoard(int num) {
        getCon();
        BoardBean bean = null;

        try{
            //조회수 증가
            String countsql = "update board set readcount = readcount+1 where num=?";
            pstmt = con.prepareStatement(countsql);
            pstmt.setInt(1,num);

            pstmt.executeUpdate();
            //하나의 게시글 가져오기
            String sql = "select * from board where num=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,num);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                 bean = new BoardBean();
                bean.setNum(rs.getInt(1));
                bean.setWriter(rs.getString(2));
                bean.setEmail(rs.getString(3));
                bean.setSubject(rs.getString(4));
                bean.setPassword(rs.getString(5));
                bean.setReg_date(rs.getDate(6).toString());
                bean.setRef(rs.getInt(7));
                bean.setRe_step(rs.getInt(8));
                bean.setRe_level(rs.getInt(9));
                bean.setReadcount(rs.getInt(10));
                bean.setContent(rs.getString(11));
            }
            con.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    //답변글 저장
    public void reInsertBoard(BoardBean bean) {


        getCon();
        int ref = bean.getRef();
        int re_step = bean.getRe_step();
        int re_level = bean.getRe_level();

        try{
            String levelsql = "update board set re_level=re_level+1 where ref=? and re_level >?";
            pstmt = con.prepareStatement(levelsql);
            pstmt.setInt(1,ref);
            pstmt.setInt(2,re_level);
            pstmt.executeUpdate();

            //데이터 넣는 쿼리
            String sql = "insert into board values(null,?,?,?,?,now(),?,?,?,0,?)";
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, bean.getWriter());
            pstmt.setString(2, bean.getEmail());
            pstmt.setString(3,bean.getSubject());
            pstmt.setString(4,bean.getPassword());
            pstmt.setInt(5,ref);
            pstmt.setInt(6,re_step+1); //부모글 보다 1증가
            pstmt.setInt(7,re_level+1);
            pstmt.setString(8,bean.getContent());

            pstmt.executeUpdate();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //조회수 증가하지 않고 하나의 게시글 가져오기
    public BoardBean getOneUpdateBoard(int num) {
        getCon();
        BoardBean bean = null;

        try{

            //하나의 게시글 가져오기
            String sql = "select * from board where num=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,num);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                bean = new BoardBean();
                bean.setNum(rs.getInt(1));
                bean.setWriter(rs.getString(2));
                bean.setEmail(rs.getString(3));
                bean.setSubject(rs.getString(4));
                bean.setPassword(rs.getString(5));
                bean.setReg_date(rs.getDate(6).toString());
                bean.setRef(rs.getInt(7));
                bean.setRe_step(rs.getInt(8));
                bean.setRe_level(rs.getInt(9));
                bean.setReadcount(rs.getInt(10));
                bean.setContent(rs.getString(11));
            }
            con.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return bean;

    }

    //하나의 게시글 수정
    public void updateBoard(int num, String subject, String content) {

        getCon();
        try{
            String sql = "update board set subject=?, content=? where num=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,subject);
            pstmt.setString(2,content);
            pstmt.setInt(3,num);
            pstmt.executeUpdate();

            con.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //하나의 게시글 삭제하기
    public void deleteBoard(int num) {

        getCon();
        try {
            String sql = "delete from board where num=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,num);
            pstmt.executeUpdate();

            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
