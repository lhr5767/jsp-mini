package control;

import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UserLoginCon")
public class UserLoginCon extends HttpServlet {


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
        String userPassword = request.getParameter("userPassword");

        //데이터 정상적으로 넘어왔는지 확인
       if(userID == null || userID.equals("") || userPassword == null || userPassword.equals("")) {
            request.getSession().setAttribute("messageType","오류 메세지");
            request.getSession().setAttribute("messageContent","모든 내용을 입력해주세요");
            response.sendRedirect("Login.jsp");
            return;
       }
        UserDao userDao = new UserDao();

       int result = userDao.login(userID,userPassword);

       if(result == 1) {
           request.getSession().setAttribute("userID",userID);
           request.getSession().setAttribute("messageType","성공 메세지");
           request.getSession().setAttribute("messageContent","로그인에 성공했습니다");
           response.sendRedirect("index.jsp");

       }else if(result == 0) {
           request.getSession().setAttribute("messageType","오류 메세지");
           request.getSession().setAttribute("messageContent","비밀번호 혹은 아이디가 틀립니다");
           response.sendRedirect("Login.jsp");

       }

    }
}
