package control;

import model.BoardDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/BoardWriteProcCon")
public class BoardWriteProcCon extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        request.setCharacterEncoding("utf-8");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        HttpSession session = request.getSession();

        String userid = null;
        if(session.getAttribute("userid") != null) {
            userid = (String)session.getAttribute("userid");
        }

        //url로 직접 접근할수도 있으므로 userid에 대한 null처리(로그인 했는지에대한 여부)
        if (userid == null) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('로그인을 해주세요')");
            script.println("location.href='userLogin.jsp'");
            script.println("</script>");
            script.close();
        }else {

            BoardDao boardDao = new BoardDao();
            boardDao.write(title,userid,content);
            response.sendRedirect("BoardListCon");

        }





    }
}
