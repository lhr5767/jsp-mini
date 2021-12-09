package control;

import model.ChatDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/ChatUnreadCon")
public class ChatUnreadCon extends HttpServlet {


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
            response.getWriter().write("0");
        }else {
            HttpSession session = request.getSession();
            if(!userID.equals((String) session.getAttribute("userID"))){
                response.getWriter().write("");
                return;
            }
            ChatDao chatDao = new ChatDao();
            response.getWriter().write(chatDao.getAllUnreadChat(userID)+""); //"" 추가해서 문자열로 반환
        }
    }
}
