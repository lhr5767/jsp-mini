package control;

import model.BoardBean;
import model.BoardDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/BoardReWriteProcCon")
public class BoardReWriteProcCon extends HttpServlet {

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
        //넘어온 데이터를 bean에 저장
        BoardBean bean = new BoardBean();

        bean.setWriter(request.getParameter("writer"));
        bean.setSubject(request.getParameter("subject"));
        bean.setEmail(request.getParameter("email"));
        bean.setPassword(request.getParameter("password"));
        bean.setContent(request.getParameter("content"));

        bean.setRef(Integer.parseInt(request.getParameter("ref")));
        bean.setRe_step(Integer.parseInt(request.getParameter("re_step")));
        bean.setRe_level(Integer.parseInt(request.getParameter("re_level")));

        BoardDAO boardDAO = new BoardDAO();
        boardDAO.reInsertBoard(bean);

        RequestDispatcher dispatcher = request.getRequestDispatcher("BoardListCon");
        dispatcher.forward(request,response);
    }
}
