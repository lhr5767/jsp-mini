package control;

import model.EvaluationDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/DeleteCon")
public class DeleteCon extends HttpServlet {

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
        response.setContentType("text/html;charset=utf-8");

        String userid = null;

        HttpSession session = request.getSession();
        if(session.getAttribute("userid") != null) {
            userid = (String) session.getAttribute("userid");
        }

        if(userid == null) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('로그인을 해주세요.')");
            script.println("history.go(-1)");
            script.println("</script>");
            script.close();
        }

        String evaluationID = null;

        if(request.getParameter("evaluationID") != null) {
            evaluationID = request.getParameter("evaluationID");
        }


        EvaluationDao evaluationDao = new EvaluationDao();

        //userid 비교해서 해당 사용자가 맞는 경우에만 삭제
        if(userid.equals(evaluationDao.getUserId(evaluationID))) {
            int result = evaluationDao.delete(evaluationID);
            if(result == 1) {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('삭제가 완료되었습니다')");
                script.println("location.href='index.jsp'");
                script.println("</script>");
                script.close();
            }else {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('데이터베이스 오류입니다')");
                script.println("history.go(-1)");
                script.println("</script>");
                script.close();
            }
        }else {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('자신이 작성한 글만 삭제 할 수 있습니다')");
            script.println("history.go(-1)");
            script.println("</script>");
            script.close();

        }



    }
}
