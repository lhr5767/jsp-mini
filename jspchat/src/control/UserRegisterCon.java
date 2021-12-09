package control;

import model.UserBean;
import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UserRegisterCon")
public class UserRegisterCon extends HttpServlet {

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

        UserBean bean = new UserBean();
        String userID = request.getParameter("userID");
        String userName = request.getParameter("userName");
        String userAge =  request.getParameter("userAge");
        String userGender = request.getParameter("userGender");
        String userEmail = request.getParameter("userEmail");
        String userProfile = request.getParameter("userProfile");
        String userPassword1 = request.getParameter("userPassword1");
        String userPassword2 = request.getParameter("userPassword2");


        //입력이 정상적으로 이루어지지 않은경우 프로필 사진은 안넣을수도 있으므로 제외
        if (userID == null || userID.equals("") || userName == null || userName.equals("") ||
                userAge == null || userAge.equals("") || userGender == null || userGender.equals("") ||
                userEmail == null || userEmail.equals("") || userPassword1 == null || userPassword1.equals("") || userPassword2 == null || userPassword2.equals("")) {

            request.getSession().setAttribute("messageType", "오류 메세지");
            request.getSession().setAttribute("messageContent", "모든 내용을 입력하세요");
            response.sendRedirect("Join.jsp");
            return;
        }

        //가입시 입력한 비밀번호가 서로 다른경우
        if(userPassword1.equals(userPassword2) == false) {
            request.getSession().setAttribute("messageType","오류 메세지");
            request.getSession().setAttribute("messageContent","비밀번호가 서로 다릅니다");
            response.sendRedirect("Join.jsp");
            return;
        }

        bean.setUserID(userID);
        bean.setUserName(userName);
        bean.setUserPassword(userPassword1);
        bean.setUserAge(Integer.parseInt(userAge));
        bean.setUserGender(userGender);
        bean.setUserEmail(userEmail);
        //회원가입시 프로필에는 공백 넣어줌
        bean.setUserProfile("");





        UserDao userDao = new UserDao();
        int result = userDao.register(bean);

        if(result == 1) {
            request.getSession().setAttribute("userID",userID); //회원가입 성공하면 userID 세션에 넣어줌
            request.getSession().setAttribute("messageType","성공 메세지");
            request.getSession().setAttribute("messageContent","회원가입에 성공 했습니다.");
            response.sendRedirect("index.jsp");
            return;
        } else {
            request.getSession().setAttribute("messageType","오류 메세지");
            request.getSession().setAttribute("messageContent","이미 존재하는 회원입니다.");
            response.sendRedirect("Join.jsp");
            return;
        }

    }
}