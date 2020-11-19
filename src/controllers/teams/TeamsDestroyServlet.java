package controllers.teams;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Account_Team;
import utils.DBUtil;

/**
 * Servlet implementation class TeamsDestroyServlet
 */
@WebServlet("/teams/destroy")
public class TeamsDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeamsDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();


            Account_Team t = em.createNamedQuery("exitTeam", Account_Team.class)
                    .setParameter("team_Id",request.getSession().getAttribute("join_team"))
                    .getSingleResult();

            em.getTransaction().begin();
            em.remove(t);       // データ削除
            em.getTransaction().commit();
            em.close();

            // セッションスコープ上の不要になったデータを削除
            request.getSession().removeAttribute("team_id");
            request.getSession().removeAttribute("join_team");

            // indexページへリダイレクト
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

}
