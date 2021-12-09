package control;

import model.ChatDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/ChatSubmitCon")
public class ChatSubmitCon extends HttpServlet {

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
        String chatContent = request.getParameter("chatContent");

        //데이터 정상적으로 넘어왔는지 확인
        if(fromID == null || fromID.equals("") || toID == null || toID.equals("") || chatContent == null || chatContent.equals("")){
            response.getWriter().write("0"); //0은 db에 저장 안된것
        } else if (fromID.equals(toID)) { //자기 자신에게 메세지 보내지 못하도록 하기위해
            response.getWriter().write("-1");
        }
        else {
            HttpSession session = request.getSession();
            if(!fromID.equals((String) session.getAttribute("userID"))){
                response.getWriter().write("");
                return;
            }
            ChatDao chatDao = new ChatDao();
            int result = chatDao.submit(fromID,toID,chatContent);
            response.getWriter().write(result+""); //string 으로 리턴하기위해 ""붙혀줌
        }
    }
}
