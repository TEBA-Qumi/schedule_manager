package controllers.schedules;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Account;
import models.Account_Team;
import models.Schedule;
import utils.DBUtil;

/**
 * Servlet implementation class SchedulesShowServlet
 */
@WebServlet("/schedules/show")
public class SchedulesShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SchedulesShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Account login_account = (Account)request.getSession().getAttribute("login_account");
        List<Account_Team> teams = em.createNamedQuery("getMyTeams", Account_Team.class)
                .setParameter("account_Id", login_account)
                .getResultList();

        Schedule s = em.find(Schedule.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        request.setAttribute("teams", teams);
        request.setAttribute("schedule", s);
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/schedules/show.jsp");
        rd.forward(request, response);
    }

}
