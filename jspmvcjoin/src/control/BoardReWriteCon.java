package control;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/BoardReWriteCon")
public class BoardReWriteCon extends HttpServlet {

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

        int ref = Integer.parseInt(request.getParameter("ref"));
        int re_step = Integer.parseInt(request.getParameter("re_step"));
        int re_level = Integer.parseInt(request.getParameter("re_level"));

        request.setAttribute("ref",ref);
        request.setAttribute("re_step",re_step);
        request.setAttribute("re_level",re_level);

        RequestDispatcher dispatcher = request.getRequestDispatcher("boardrewriteform.jsp");
        dispatcher.forward(request,response);
    }
}
