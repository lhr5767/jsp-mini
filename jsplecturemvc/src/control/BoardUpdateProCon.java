package control;

import model.BoardDao;
import model.EvaluationDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/BoardUpdateProCon")
public class BoardUpdateProCon extends HttpServlet {

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
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int bnum = Integer.parseInt(request.getParameter("bnum"));

        BoardDao boardDao = new BoardDao();

        //로그인한 유저가 쓴 게시글인지 확인
        if(userid.equals(boardDao.getUserId(bnum))){
            boardDao.update(bnum,title,content);
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('수정이 완료되었습니다')");
            script.println("location.href='BoardListCon'");
            script.println("</script>");
            script.close();
        }else {

            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('자신이 작성한 글만 수정 할 수 있습니다')");
            script.println("history.go(-1)");
            script.println("</script>");
            script.close();
        }



    }
}
