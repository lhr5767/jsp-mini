package control;

import model.BoardBean;
import model.BoardDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/ViewCon")
public class ViewCon extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        //view.jsp로 데이터 넘기기
        RequestDispatcher dispatcher = request.getRequestDispatcher("view.jsp");

        dispatcher.forward(request,response);

    }
}
