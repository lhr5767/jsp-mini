package control;

import model.BoardBean;
import model.BoardDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "BoardWriteProcCon", value = "/BoardWriteProcCon")
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

        request.setCharacterEncoding("euc-kr");
        //빈클래스로 데이터 읽어드림
        BoardBean bean = new BoardBean();

        bean.setWriter(request.getParameter("writer"));
        bean.setSubject(request.getParameter("subject"));
        bean.setEmail(request.getParameter("email"));
        bean.setPassword(request.getParameter("password"));
        bean.setContent(request.getParameter("content"));

        //데이터 넣기
        BoardDAO boardDAO = new BoardDAO();
        boardDAO.insertBoard(bean);

        RequestDispatcher dispatcher = request.getRequestDispatcher("BoardListCon");
        dispatcher.forward(request,response);
    }
}
