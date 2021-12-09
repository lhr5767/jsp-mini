package control;

import model.EvaluationBean;
import model.EvaluationDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/EvaluationListCon")
public class EvaluationListCon extends HttpServlet {


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
        String lecturedivide = "전체";
        String searchtype = "최신순";
        String search ="";
        int pageNumber = 0;


        if(request.getParameter("lecturedivide")!=null){
            lecturedivide = request.getParameter("lecturedivide");
        }
        if(request.getParameter("searchtype")!=null){
            searchtype = request.getParameter("searchtype");
        }
        if(request.getParameter("search")!=null){
            search = request.getParameter("search");
        }
        if(request.getParameter("pageNumber") != null){
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }

        EvaluationDao evaluationDao = new EvaluationDao();
        ArrayList<EvaluationBean> list = evaluationDao.getList(lecturedivide,searchtype,search,pageNumber);

        request.setAttribute("list",list);
        request.setAttribute("lecturedivide",lecturedivide);
        request.setAttribute("searchtype",searchtype);
        request.setAttribute("search",search);
        request.setAttribute("pageNumber",pageNumber);

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");

        dispatcher.forward(request,response);





    }
}
