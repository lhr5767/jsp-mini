package control;

import model.EvaluationDao;
import model.LikeyDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/LikeCon")
public class LikeCon extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userid = null;

        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");

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
        LikeyDao likeyDao = new LikeyDao();

        int result = likeyDao.like(userid,evaluationID,getIP(request));

        if (result == 1) {
            result = evaluationDao.like(evaluationID);
            if(result == 1) {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('추천이 완료되었습니다.');");
                script.println("location.href='EvaluationListCon'");
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
            script.println("alert('이미 추천을 했습니다')");
            script.println("history.go(-1)");
            script.println("</script>");
            script.close();
        }


    }

    //ip구하는 메서드
    public static String getIP(HttpServletRequest request){
        String ip = request.getHeader("X-ForWARDED-FOR");
        if(ip == null || ip.length()==0){
            ip= request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length()==0){
            ip= request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length()==0){
            ip= request.getRemoteAddr();
        }
        return ip;
    }

}
