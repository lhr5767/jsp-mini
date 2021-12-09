package control;

import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UserRegisterCheckCon")
public class UserRegisterCheckCon extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String userID = request.getParameter("userID");

        if(userID == null || userID.equals("")){
            response.getWriter().write("-1");
        }

        UserDao userDao = new UserDao();

        //결과값 문자열 형태로 반환하기 위해 "" 붙혀줌줌
       response.getWriter().write(userDao.registerCheck(userID) + "");
    }
}
