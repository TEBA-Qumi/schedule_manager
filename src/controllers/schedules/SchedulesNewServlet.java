package controllers.schedules;

import java.io.IOException;
import java.sql.Date;
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
 * Servlet implementation class SchedulesNewServlet
 */
@WebServlet("/schedules/new")
public class SchedulesNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SchedulesNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
      //セッションIDを取得
        request.setAttribute("_token", request.getSession().getId());

        //Scheduleオブジェクトを作成
        Schedule s = new Schedule();
        //作成されたオブジェクトに日付を格納
        s.setSchedule_date(new Date(System.currentTimeMillis()));

        Account login_account = (Account)request.getSession().getAttribute("login_account");

        List<Account_Team> teams = em.createNamedQuery("getMyTeams", Account_Team.class)
                .setParameter("account_Id", login_account.getAccount_ids())
                .getResultList();

        em.close();

        request.setAttribute("schedule", s);
        request.setAttribute("teams", teams);
        //requestオブジェクトに格納されたデータをnew.jspに送信
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/schedules/new.jsp");
        rd.forward(request, response);
    }

}
