package control;

import model.BoardBean;
import model.BoardDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "BoardInfoCon", value = "/BoardInfoCon")
public class BoardInfoCon extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request,response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //num 가져오기
        int num = Integer.parseInt(request.getParameter("num"));
        //db연결해 하나의 게시글 가져오기
        BoardDAO boardDAO = new BoardDAO();

        BoardBean bean = boardDAO.getOneBoard(num);

        //jsp로 데이터 넘기기
        request.setAttribute("bean",bean);
        RequestDispatcher dispatcher = request.getRequestDispatcher("boardinfo.jsp");
        dispatcher.forward(request,response);
    }
}
