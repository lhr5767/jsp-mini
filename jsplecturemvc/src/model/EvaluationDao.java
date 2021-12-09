package model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EvaluationDao {

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

    //강의평가글 작성
    public void write(EvaluationBean bean) {


        getCon();
        try {
            String sql = "insert into evaluation values (null,?,?,?,?,?,?,?,?,?,?,?,?,0)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,bean.getUserid().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\r\n","<br>"));
            pstmt.setString(2,bean.getLecturename().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\r\n","<br>"));
            pstmt.setString(3,bean.getProfessorname().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\r\n","<br>"));
            pstmt.setInt(4,bean.getLectureyear());
            pstmt.setString(5,bean.getSemesterdivide().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\r\n","<br>"));
            pstmt.setString(6,bean.getLecturedivide().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\r\n","<br>"));
            pstmt.setString(7,bean.getTitle().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\r\n","<br>"));
            pstmt.setString(8,bean.getContent().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\r\n","<br>"));
            pstmt.setString(9,bean.getTotalscore().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\r\n","<br>"));
            pstmt.setString(10,bean.getCreditscore().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\r\n","<br>"));
            pstmt.setString(11,bean.getFeelscore().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\r\n","<br>"));
            pstmt.setString(12,bean.getLecturescore().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\r\n","<br>"));

            pstmt.executeUpdate();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //검색에 사용될 메서드
    public ArrayList<EvaluationBean> getList(String lecturedivide, String searchType, String search, int pageNumber){
        if(lecturedivide.equals("전체")) {
            lecturedivide = "";
        }
        ArrayList<EvaluationBean> list = new ArrayList<>();

        getCon();
        String sql = "";
        try{

            if(searchType.equals("최신순")){
                sql = "select * from evaluation where lecturedivide like ? and concat(lecturename,professorname,title,content) like ?"+
                        "order by evaluationID desc limit "+pageNumber*5+", "+pageNumber*5+6;
            }else if(searchType.equals("추천순")){
                sql = "select * from evaluation where lecturedivide like ? and concat(lecturename,professorname,title,content) like ?"+
                        "order by likecount desc limit "+pageNumber*5+", "+pageNumber*5+6;
            }
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,"%"+lecturedivide+"%");
            pstmt.setString(2,"%"+search+"%");

            rs= pstmt.executeQuery();
            while(rs.next()) {
                EvaluationBean bean = new EvaluationBean();
                bean.setEvaluationID(rs.getInt(1));
                bean.setUserid(rs.getString(2));
                bean.setLecturename(rs.getString(3));
                bean.setProfessorname(rs.getString(4));
                bean.setLectureyear(rs.getInt(5));
                bean.setSemesterdivide(rs.getString(6));
                bean.setLecturedivide(rs.getString(7));
                bean.setTitle(rs.getString(8));
                bean.setContent(rs.getString(9));
                bean.setTotalscore(rs.getString(10));
                bean.setCreditscore(rs.getString(11));
                bean.setFeelscore(rs.getString(12));
                bean.setLecturescore(rs.getString(13));
                bean.setLikecount(rs.getInt(14));

                list.add(bean);
            }
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    //추천수 증가
    public int like(String evaluationID) {
        getCon();

        int count = 0;

        try{
            String sql = "update evaluation set likecount = likecount +1 where evaluationID=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,Integer.parseInt(evaluationID));
            count = pstmt.executeUpdate();

            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return count;
    }

    //강의평가 삭제하기
    public int delete(String evaluationID) {
        getCon();
        int count = 0;

        try{
            String sql = "delete from evaluation where evaluationID =?";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,Integer.parseInt(evaluationID));
            count = pstmt.executeUpdate();

            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return count;
    }

    //특정 사용자의 userid 가져오기(강의평가 삭제시 userid 비교하기 위해 사용할것)
    public String getUserId(String evaluationID) {
        getCon();
        String userid="";

        try {
            String sql = "select userid from evaluation where evaluationID=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,Integer.parseInt(evaluationID));
            rs= pstmt.executeQuery();

            if(rs.next()) {
                userid=rs.getString(1);
            }
            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return userid;

    }

}
