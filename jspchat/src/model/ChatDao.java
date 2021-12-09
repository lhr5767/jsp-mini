package model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChatDao {

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

    //서로 주고받은 모든 메시지 가져오기
    public ArrayList<ChatBean> getChatListById(String fromID, String toID, String chatID) {
        ArrayList<ChatBean> list = new ArrayList<>();
        getCon();

        try {
            String sql = "select * from chat where ((fromID=? and toID=?) or (fromID=? and toID=?)) and chatID > ? order by chatTime";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, fromID);
            pstmt.setString(2, toID);
            pstmt.setString(3, toID);
            pstmt.setString(4, fromID);
            pstmt.setInt(5, Integer.parseInt(chatID));

            rs=pstmt.executeQuery();
            while (rs.next()) {
                ChatBean bean = new ChatBean();
                bean.setChatID(rs.getInt(1));
                //xss 공격 방어위해 특수문자 치환해줌
                bean.setFromID(rs.getString(2).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));
                bean.setToID(rs.getString(3).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));
                bean.setChatContent(rs.getString(4).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));

                int chatTime = Integer.parseInt(rs.getString(5).substring(11,13));
                String timeType = "오전";
                if(chatTime >= 12) {
                    timeType = "오후";
                    chatTime -= 12;
                }
                bean.setChatTime(rs.getString(5).substring(0,11) + " " + timeType + " " + chatTime + ":" + rs.getString(5).substring(14,16)+"");
                list.add(bean);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //최근 메세지 몇개만 가져오기

    public ArrayList<ChatBean> getChatListByRecent(String fromID, String toID, int number) {
        ArrayList<ChatBean> list = new ArrayList<>();
        getCon();

        try {
            String sql = "select * from chat where ((fromID=? and toID=?) or (fromID=? and toID=?)) and chatID > (select max(chatID) -? from chat where(fromID=? and toID=?) or (fromID=? and toID=?)) order by chatTime";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, fromID);
            pstmt.setString(2, toID);
            pstmt.setString(3, toID);
            pstmt.setString(4, fromID);
            pstmt.setInt(5, number);
            pstmt.setString(6,fromID);
            pstmt.setString(7,toID);
            pstmt.setString(8,toID);
            pstmt.setString(9,fromID);

            rs=pstmt.executeQuery();

            while (rs.next()) {
                ChatBean bean = new ChatBean();
                bean.setChatID(rs.getInt(1));
                //xss 공격 방어위해 특수문자 치환해줌
                bean.setFromID(rs.getString(2).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));
                bean.setToID(rs.getString(3).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));
                bean.setChatContent(rs.getString(4).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));

                int chatTime = Integer.parseInt(rs.getString(5).substring(11,13));
                String timeType = "오전";
                if(chatTime >= 12) {
                    timeType = "오후";
                    chatTime -= 12;
                }
                bean.setChatTime(rs.getString(5).substring(0,11) + " " + timeType + " " + chatTime + ":" + rs.getString(5).substring(14,16)+"");
                list.add(bean);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //메세지 전송하는 메서드(정상 전송시 db에 저장) 데이터 들어갈때 한글처리에 유의할것
    public int submit(String fromID, String toID, String chatContent){
        getCon();
        int count = 0;
        try{
            String sql = "insert into chat values(null,?,?,?,now(),0)"; // chatRead가 0은 읽지 않은것

            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,fromID);
            pstmt.setString(2,toID);
            pstmt.setString(3,chatContent);

            count = pstmt.executeUpdate(); //성공시 count에 1 들어감
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  count; //1을 리턴하면 정상적으로 db에 저장된것
    }

    //메세지를 읽었는지 확인하는 메서드
    public int readChat(String fromID, String toID){
        getCon();
        int count = 0;
        try{
            String sql = "update chat set chatRead=1 where (fromID = ? and toID = ?)"; //메세지를 읽으면 chatRead를 1로 바꿔줌
            pstmt=con.prepareStatement(sql);
            //toID,fromID 교차해 넣음으로 받는사람 입장에서 메세지 읽은걸 확인함
            pstmt.setString(1,toID);
            pstmt.setString(2,fromID);

            count = pstmt.executeUpdate(); //성공시 count에 1 들어감
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return count; //1은 성공 0은 실패
    }

    //읽지 않은 메세지 개수 가져오기
    public int getAllUnreadChat(String userID) {
        getCon();
        int count = 0;

        try{
            String sql = "select count(chatID) from chat where toID=? and chatRead = 0";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,userID);

            rs = pstmt.executeQuery();

            if(rs.next()) {
                count = rs.getInt(1);
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return count;
    }


    public ArrayList<ChatBean> getBox(String userID) {
        ArrayList<ChatBean> list = new ArrayList<>();
        getCon();

        try {
            String sql = "select * from chat where chatID in (select max(chatID) from chat where toID = ? or fromID = ? group by fromID,toID)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userID);
            pstmt.setString(2, userID);


            rs=pstmt.executeQuery();

            while (rs.next()) {
                ChatBean bean = new ChatBean();
                bean.setChatID(rs.getInt(1));
                //xss 공격 방어위해 특수문자 치환해줌
                bean.setFromID(rs.getString(2).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));
                bean.setToID(rs.getString(3).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));
                bean.setChatContent(rs.getString(4).replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));

                int chatTime = Integer.parseInt(rs.getString(5).substring(11,13));
                String timeType = "오전";
                if(chatTime >= 12) {
                    timeType = "오후";
                    chatTime -= 12;
                }
                bean.setChatTime(rs.getString(5).substring(0,11) + " " + timeType + " " + chatTime + ":" + rs.getString(5).substring(14,16)+"");
                list.add(bean);
            }

            //최신글 한개만 나오게 하기위해 필터링
           for(int i = 0; i< list.size() ; i++) {
                ChatBean x = list.get(i);
                for(int j = 0; j< list.size(); j++) {
                    ChatBean y = list.get(j);
                    if(x.getFromID().equals(y.getToID()) && x.getToID().equals(y.getFromID())){
                        if(x.getChatID() < y.getChatID()){
                            list.remove(x);
                            i--;
                            break;
                        }else {
                            list.remove(y);
                            j--;
                        }
                    }
                }
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //읽지 않은 메세지 개수 가져오기 (대화 상대별로)
    public int getUnreadChat(String fromID,String toID) {
        getCon();
        int count = 0;

        try{
            String sql = "select count(chatID) from chat where fromID=? and toID=? and chatRead = 0";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,fromID);
            pstmt.setString(2,toID);

            rs = pstmt.executeQuery();

            if(rs.next()) {
                count = rs.getInt(1);
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return count;
    }
}
