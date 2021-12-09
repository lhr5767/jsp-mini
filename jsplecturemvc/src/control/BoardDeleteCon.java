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

@WebServlet("/BoardDeleteCon")
public class BoardDeleteCon extends HttpServlet {

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

        String userid = null;

        HttpSession session = request.getSession();

        if (session.getAttribute("userid") != null) {
            userid = (String) session.getAttribute("userid");
        }

        if (userid == null) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('로그인을 해주세요')");
            script.println("location.href='userLogin.jsp'");
            script.println("</script>");
            script.close();
        } else {
            int bnum = Integer.parseInt(request.getParameter("bnum"));
            BoardDao boardDao = new BoardDao();
            boardDao.delete(bnum);

            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('삭제 되었습니다')");
            script.println("location.href='BoardListCon'");
            script.println("</script>");
            script.close();

        }
    }
}
