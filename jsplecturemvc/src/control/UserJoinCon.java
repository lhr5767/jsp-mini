package control;

import model.UserBean;
import model.UserDao;
import util.SHA256;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/UserJoinCon")
public class UserJoinCon extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {


        //alert 창 출력시 한글깨짐 방지
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession();


        request.setCharacterEncoding("utf-8");

        String userid = null;

        String userpassword = null;
        String useremail = null;

        //넘어온 데이터 받기
        userid = request.getParameter("userID");


        userpassword = request.getParameter("userPass");


        useremail = request.getParameter("userEmail");

        UserDao userDao = new UserDao();
        UserBean bean = new UserBean();

        bean.setUserid(userid);
        bean.setUserpassword(userpassword);
        bean.setUseremail(useremail);
        bean.setUseremailhash(SHA256.getSHA256(useremail));
        bean.setUseremailhaschecked(false);

        int result = userDao.join(bean);
        if(result == 0) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('이미 존재하는 아이디 입니다');");
            script.println("history.go(-1);");
            script.println("</script>");
            script.close();
        }else {
            session.setAttribute("userid",userid);
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("location.href='emailSendActionCon';");
            script.println("</script>");
            script.close();

        }


    }

}
