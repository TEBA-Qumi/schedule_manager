package controllers.teams;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Team;
import utils.DBUtil;

/**
 * Servlet implementation class TeamsShowServlet
 */
@WebServlet("/teams/show")
public class TeamsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeamsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        //idからチームを取得
        Team t = em.find(Team.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        request.setAttribute("team", t);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/teams/show.jsp");
        rd.forward(request, response);
    }

}
