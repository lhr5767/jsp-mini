package control;

import model.ChatBean;
import model.ChatDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/ChatListCon")
public class ChatListCon extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        //넘어온 데이터 받기
        String fromID = request.getParameter("fromID");
        String toID = request.getParameter("toID");
        String listType = request.getParameter("listType");

        //데이터 정상적으로 넘어왔는지 확인
        if (fromID == null || fromID.equals("") || toID == null || toID.equals("") || listType == null || listType.equals("")) {
            response.getWriter().write(""); //공백문자 반환
        }else if(listType.equals("ten")) {
            response.getWriter().write(getTen(fromID,toID));
        }else {
            try {
                HttpSession session = request.getSession();
                if(!fromID.equals((String) session.getAttribute("userID"))){
                    response.getWriter().write("");
                    return;
                }
                response.getWriter().write(getID(fromID,toID,listType));
            }catch (Exception e){
                response.getWriter().write("");
            }
        }
    }

    public String getTen(String fromID, String toID) {
        StringBuffer result = new StringBuffer(""); //공백문자열로 시작
        result.append("{\"result\":["); //Json형태로 반환하기 위해 설정

        ChatDao chatDao = new ChatDao();
        ArrayList<ChatBean> list = chatDao.getChatListByRecent(fromID,toID,100);

        if(list.size() == 0) return "";

        for (int i = 0; i< list.size(); i++) {
            result.append("[{\"value\": \"" + list.get(i).getFromID() +"\"},");
            result.append("{\"value\": \"" + list.get(i).getToID() +"\"},");
            result.append("{\"value\": \"" + list.get(i).getChatContent() +"\"},");
            result.append("{\"value\": \"" + list.get(i).getChatTime()+"\"}]");
            if( i != list.size() -1 ) result.append(","); //마지막 원소 아니면 ,를 찍어줌
        }
        //마지막 chatID 가져와서 연결
        result.append("], \"last\":\"" + list.get(list.size()-1).getChatID() +"\"}");
        chatDao.readChat(fromID,toID);
        return result.toString(); //JSon형태의 문자열 반환

    }


    public String getID(String fromID, String toID, String chatID) {
        StringBuffer result = new StringBuffer(""); //공백문자열로 시작
        result.append("{\"result\":["); //Json형태로 반환하기 위해 설정

        ChatDao chatDao = new ChatDao();
        ArrayList<ChatBean> list = chatDao.getChatListById(fromID,toID,chatID);

        if(list.size() == 0) return "";

        for (int i = 0; i< list.size(); i++) {
            result.append("[{\"value\": \"" + list.get(i).getFromID() +"\"},");
            result.append("{\"value\": \"" + list.get(i).getToID() +"\"},");
            result.append("{\"value\": \"" + list.get(i).getChatContent() +"\"},");
            result.append("{\"value\": \"" + list.get(i).getChatTime()+"\"}]");
            if( i != list.size() -1 ) result.append(","); //마지막 원소 아니면 ,를 찍어줌
        }
        //마지막 chatID 가져와서 연결
        result.append("], \"last\":\"" + list.get(list.size()-1).getChatID() +"\"}");
        chatDao.readChat(fromID,toID);
        return result.toString(); //JSon형태의 문자열 반환

    }
}
