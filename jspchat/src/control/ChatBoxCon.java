package control;

import model.ChatBean;
import model.ChatDao;
import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/ChatBoxCon")
public class ChatBoxCon extends HttpServlet {

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

        String userID = request.getParameter("userID");


        if(userID == null || userID.equals("")){
            response.getWriter().write("");
        }else {
            try{
                HttpSession session = request.getSession();
                if(!userID.equals((String) session.getAttribute("userID"))){
                    response.getWriter().write("");
                    return;
                }
               response.getWriter().write(getBox(userID));

            }catch (Exception e){
                response.getWriter().write("");
            }
        }
    }

    public String getBox(String userID) {

        StringBuffer result = new StringBuffer(""); //공백문자열로 시작
        result.append("{\"result\":["); //Json형태로 반환하기 위해 설정

        ChatDao chatDao = new ChatDao();
        ArrayList<ChatBean> list = chatDao.getBox(userID);

        if(list.size() == 0) return "";

        for (int i = list.size()-1 ; i>=0 ; i--) {
            String unread ="";
            String userProfile = "";
            if(userID.equals(list.get(i).getToID())){
                unread = chatDao.getUnreadChat(list.get(i).getFromID(),userID) +"";
                if(unread.equals("0")) unread =""; //안읽은 메세지가 없을때
            }

            if(userID.equals(list.get(i).getToID())) {
                 userProfile = new UserDao().getProfile(list.get(i).getFromID());
            }else {
                 userProfile = new UserDao().getProfile(list.get(i).getToID());
            }
            result.append("[{\"value\": \"" + list.get(i).getFromID() +"\"},");
            result.append("{\"value\": \"" + list.get(i).getToID() +"\"},");
            result.append("{\"value\": \"" + list.get(i).getChatContent() +"\"},");
            result.append("{\"value\": \"" + list.get(i).getChatTime()+"\"},");
            result.append("{\"value\": \"" + unread + "\"},");
            result.append("{\"value\": \"" + userProfile + "\"}]"); //사용자의 프로필사진 나오게끔 하기위해
            if( i != 0 ) result.append(","); //마지막 원소 아니면 ,를 찍어줌
        }
        //마지막 chatID 가져와서 연결
        result.append("], \"last\":\"" + list.get(list.size()-1).getChatID() +"\"}");

        return result.toString(); //JSon형태의 문자열 반환

    }

}
