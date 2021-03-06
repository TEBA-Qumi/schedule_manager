package controllers.teams;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Account;
import models.Account_Team;
import models.Team;
import utils.DBUtil;

/**
 * Servlet implementation class TeamsEditServlet
 */
@WebServlet("/teams/edit")
public class TeamsEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeamsEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
      //セッションからアカウントオブジェクトを取得
        Account login_account = (Account)request.getSession().getAttribute("login_account");

        //idから該当するチームを取得
        Team t = em.find(Team.class, Integer.parseInt(request.getParameter("id")));
        Account_Team a_t = em.createNamedQuery("getTeam",Account_Team.class)
                .setParameter("account_Id", login_account)
                .setParameter("team_Id", t)
                .getSingleResult();

        em.close();

        request.setAttribute("team", t);
        request.setAttribute("_token", request.getSession().getId());
        request.getSession().setAttribute("team_id", t.getId());
        request.getSession().setAttribute("join_team", a_t.getTeam_Id());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/teams/edit.jsp");
        rd.forward(request, response);
    }

}
