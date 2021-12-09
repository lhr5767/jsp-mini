package control;

import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

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

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession();

        String userid = null;
        String userpassword = null;

        userid = request.getParameter("userID");
        userpassword = request.getParameter("userPass");

        UserDao userDao = new UserDao();

        int result = userDao.login(userid,userpassword);
        if(result == 1) { //로그인 성공
            session.setAttribute("userid",userid);
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("location.href='index.jsp'");
            script.println("</script>");
            script.close();

        }else if(result == 0) { //비밀번호 틀린경우
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('비밀번호가 틀립니다')");
            script.println("history.go(-1);");
            script.println("</script>");
            script.close();

        }else if(result == -1) { //아이디 없는 경우
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('존재하지않는 아이디 입니다')");
            script.println("history.go(-1);");
            script.println("</script>");
            script.close();


        }
    }
}
