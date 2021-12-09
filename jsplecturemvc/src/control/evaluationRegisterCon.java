package control;

import model.BoardBean;
import model.EvaluationBean;
import model.EvaluationDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/evaluationRegisterCon")
public class evaluationRegisterCon extends HttpServlet {

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

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        String userid = null;

        HttpSession session = request.getSession();
        if(session.getAttribute("userid") != null) {
            userid = (String) session.getAttribute("userid");
        }

        if(userid == null) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('로그인을 해주세요');");
            script.println("location.href='userLogin.jsp'");
            script.println("</script>");
            script.close();
        }



        EvaluationBean bean = new EvaluationBean();

        bean.setUserid(userid);
        bean.setLecturename(request.getParameter("lecturename"));
        bean.setProfessorname(request.getParameter("professorname"));
        bean.setLectureyear(Integer.parseInt(request.getParameter("lectureyear")));
        bean.setSemesterdivide(request.getParameter("semesterdivide"));
        bean.setLecturedivide(request.getParameter("lecturedivide"));
        bean.setTitle(request.getParameter("title"));
        bean.setContent(request.getParameter("content"));
        bean.setTotalscore(request.getParameter("totalscore"));
        bean.setCreditscore(request.getParameter("creditscore"));
        bean.setFeelscore(request.getParameter("feelscore"));
        bean.setLecturescore(request.getParameter("lecturescore"));

        EvaluationDao evaluationDao = new EvaluationDao();

        evaluationDao.write(bean);
        response.sendRedirect("EvaluationListCon");
    }
}
