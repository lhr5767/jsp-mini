package control;

import model.BoardBean;
import model.BoardDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/BoardUpdateCon")
public class BoardUpdateCon extends HttpServlet {


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
        response.setContentType("text/html; charset=utf-8");


        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();

        String userid = null;

        if(session.getAttribute("userid") != null) {
            userid = (String) session.getAttribute("userid");
        }

        if(userid == null) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('로그인을 해주세요')");
            script.println("location.href='userLogin.jsp'");
            script.println("</script>");
            script.close();
        }

        int bnum = Integer.parseInt(request.getParameter("bnum"));

        BoardBean bean = new BoardBean();
        BoardBean bean2 = new BoardBean();

        BoardDao boardDao = new BoardDao();

        bean = boardDao.getOneBoard(bnum);

        bean2.setBnum(bean.getBnum());
        bean2.setUserid(bean.getUserid());
        bean2.setDelcheck(bean.getDelcheck());
        bean2.setDate(bean.getDate());
        bean2.setTitle(bean.getTitle().replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));
        bean2.setContent(bean.getContent().replaceAll(" ","&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>"));


        request.setAttribute("bean",bean2);

        //boardUpdateForm.jsp 로 데이터 넘기기
        RequestDispatcher dispatcher = request.getRequestDispatcher("boardUpdateForm.jsp");
        dispatcher.forward(request,response);

    }


}
