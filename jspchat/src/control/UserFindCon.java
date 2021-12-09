package control;

import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UserFindCon")
public class UserFindCon extends HttpServlet {

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

        UserDao userDao = new UserDao();

        String userID = request.getParameter("userID");

        if(userID == null || userID.equals("")) { //데이터 넘어오지 않은경우
            response.getWriter().write("-1");
        }else if (userDao.registerCheck(userID)==0) { //회원등록이 된 사용자인 경우
            try {
                response.getWriter().write(find(userID));
            }catch (Exception e) {
                e.printStackTrace();
                response.getWriter().write("-1");
            }
        }else {
            response.getWriter().write("-1");
        }
    }

    public String find(String userID) throws Exception{
        UserDao userDao = new UserDao();
        StringBuffer result = new StringBuffer("");
        result.append("{\"userProfile\":\"" + userDao.getProfile(userID)+"\"}");

        return result.toString();
    }
}
