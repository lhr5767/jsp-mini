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
        try {
            Context ini = new InitialContext();
            Context emvctx = (Context) ini.lookup("java:comp/env");
            DataSource ds = (DataSource) emvctx.lookup("jdbc/mysql");

            con = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //게시글 작성 메서드
    public int write(BoardBean bean) {
        getCon();
        int count = 0;
        try{
            String sql = "insert into board values (?,null,?,?,now(),0,?,?,(select ifnull(max(a.boardGroup),0)+1 from board a),0,0,1)";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1, bean.getUserID());
            pstmt.setString(2, bean.getBoardTitle());
            pstmt.setString(3,bean.getBoardContent());
            pstmt.setString(4, bean.getBoardFile());
            pstmt.setString(5, bean.getBoardRealFile());

            count = pstmt.executeUpdate();

            con.close();

        }catch (Exception e) {
            e.printStackTrace();

        }
        return count;
    }

    //하나의 게시글 정보 가져오기
    public BoardBean getBoard(String boardID) {
        getCon();
        BoardBean bean = null;
        try{
            String sql = "select * from board where boardID = ?";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,boardID);
            rs=pstmt.executeQuery();

            if(rs.next()){
                bean = new BoardBean();
                bean.setUserID(rs.getString(1));
                bean.setBoardID(rs.getInt(2));
                bean.setBoardTitle(rs.getString(3).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));
                bean.setBoardContent(rs.getString(4).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));
                bean.setBoardDate(rs.getString(5).substring(0,11));
                bean.setBoardHit(rs.getInt(6));
                bean.setBoardFile(rs.getString(7));
                bean.setBoardRealFile(rs.getString(8));
                bean.setBoardGroup(rs.getInt(9));
                bean.setBoardSequence(rs.getInt(10));
                bean.setBoardLevel(rs.getInt(11));
                bean.setBoardAvailable(rs.getInt(12));
            }
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    //모든 게시글 가져오기
    public ArrayList<BoardBean> getList(String pageNumber) { //페이징 처리위해 pageNumber 파라미터로 넘겨줌(특정 페이지에 해당하는 데이터만 가져옴)
        ArrayList<BoardBean> list = new ArrayList<>();
        getCon();


        try{
            String sql = "select * from board where boardGroup > (select max(boardGroup) from board) - ? and boardGroup <=(select max(boardGroup) from board) - ? order by boardGroup desc, boardSequence asc";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,Integer.parseInt(pageNumber)*10);
            pstmt.setInt(2,(Integer.parseInt(pageNumber)-1)*10);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BoardBean bean = new BoardBean();

                bean.setUserID(rs.getString(1));
                bean.setBoardID(rs.getInt(2));
                bean.setBoardTitle(rs.getString(3).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));
                bean.setBoardContent(rs.getString(4).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));
                bean.setBoardDate(rs.getString(5).substring(0,11));
                bean.setBoardHit(rs.getInt(6));
                bean.setBoardFile(rs.getString(7));
                bean.setBoardRealFile(rs.getString(8));
                bean.setBoardGroup(rs.getInt(9));
                bean.setBoardSequence(rs.getInt(10));
                bean.setBoardLevel(rs.getInt(11));
                bean.setBoardAvailable(rs.getInt(12));

                list.add(bean);

            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    //다음 페이지가 존재하는지 확인하는 메서드 (페이징 처리시 사용)
    public boolean nextPage(String pageNumber) {
        getCon();
        boolean next = false;
        try{
            String sql = "select * from board where boardGroup >= ?";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,Integer.parseInt(pageNumber) * 10);
            rs= pstmt.executeQuery();

            if(rs.next()) {
                next = true;
            }
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return next;
    }

    public int targetPage(String pageNumber) {
        getCon();
        int count = 0;
        try{
            String sql = "select count(boardGroup) from board where boardGroup > ?";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,(Integer.parseInt(pageNumber) -1) * 10);
            rs= pstmt.executeQuery();

            if(rs.next()) {
                count = rs.getInt(1)/10;
            }
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    //게시글 조회수 증가
    public void hit(String boardID) {
        getCon();
        try {
            String sql = "update board set boardHit = boardHit +1 where boardID = ?";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,boardID);
            pstmt.executeUpdate();

            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //첨부파일 다운로드에 사용할 메서드
    public String getFile(String boardID) {
        getCon();
        String fileName = "";
        try {
            String sql = "select boardFile from board where boardID =?";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,boardID);

            rs= pstmt.executeQuery();
            if(rs.next()) {
                fileName = rs.getString(1);
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return fileName;
    }

    //실제 물리적인 경로
    public String getRealFile(String boardID) {
        getCon();
        String fileRealName = "";
        try {
            String sql = "select boardRealFile from board where boardID =?";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,boardID);

            rs= pstmt.executeQuery();
            if(rs.next()) {
                fileRealName = rs.getString(1);
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return fileRealName;
    }

    //게시글 수정 메서드
    public int update(BoardBean bean) {
        getCon();
        int count = 0;
        try{
            String sql = "update board set boardTitle = ?,boardContent = ?,boardFile = ?,boardRealFile = ? where boardID= ?";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1, bean.getBoardTitle());
            pstmt.setString(2, bean.getBoardContent());
            pstmt.setString(3,bean.getBoardFile());
            pstmt.setString(4, bean.getBoardRealFile());
            pstmt.setInt(5,bean.getBoardID());

            count = pstmt.executeUpdate();

            con.close();

        }catch (Exception e) {
            e.printStackTrace();

        }
        return count;
    }

    //게시글 삭제 메서드
    public void delete(String boardID) {
        getCon();
        try{
            String sql = "update board set boardAvailable = 0 where boardID =?"; //데이터 날리지 않고 boardAvailable=0 으로 변경
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,Integer.parseInt(boardID));

            pstmt.executeUpdate();

            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //답변 작성 메서드
   public int reply(BoardBean bean, BoardBean parent) { //parent는 부모글
        getCon();
        int count = 0;
        try{
            String sql = "insert into board values (?,null,?,?,now(),0,?,?,?,?,?,1)";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1, bean.getUserID());
            pstmt.setString(2, bean.getBoardTitle());
            pstmt.setString(3,bean.getBoardContent());
            pstmt.setString(4, bean.getBoardFile());
            pstmt.setString(5, bean.getBoardRealFile());
            pstmt.setInt(6,parent.getBoardGroup()); //부모글과 동일하게(Group)
            pstmt.setInt(7,parent.getBoardSequence()+1); //sequence는 +1 증가시켜서
            pstmt.setInt(8,parent.getBoardLevel()+1); //Level은 +1 (들여쓰기 하기 위해)

            count = pstmt.executeUpdate();

            con.close();

        }catch (Exception e) {
            e.printStackTrace();

        }
        return count;
    }


    //게시글 수정 메서드
    public int replyUpdate(BoardBean parent) {
        getCon();
        int count = 0;
        try{
            String sql = "update board set boardSequence = boardSequence + 1 where boardGroup = ? and boardSequence > ?";
            pstmt=con.prepareStatement(sql);

            pstmt.setInt(1,parent.getBoardGroup());
            pstmt.setInt(2,parent.getBoardSequence());
            count = pstmt.executeUpdate();

            con.close();

        }catch (Exception e) {
            e.printStackTrace();

        }
        return count;
    }
}
