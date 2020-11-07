package controllers.schedules;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Account;
import models.Schedule;
import utils.DBUtil;

/**
 * Servlet implementation class SchedulesEditServlet
 */
@WebServlet("/schedules/edit")
public class SchedulesEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SchedulesEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Schedule s = em.find(Schedule.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        Account login_account = (Account)request.getSession().getAttribute("login_account");
        if(s != null && login_account.getId() == s.getAccount().getId()) {
            request.setAttribute("schedule", s);
            request.setAttribute("_token", request.getSession().getId());
            request.getSession().setAttribute("schedule_id", s.getId());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/schedules/edit.jsp");
        rd.forward(request, response);
    }

}
