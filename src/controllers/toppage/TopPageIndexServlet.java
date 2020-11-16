package controllers.toppage;

import java.io.IOException;
import java.util.Calendar;
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
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/index.html")
public class TopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopPageIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Account login_account = (Account)request.getSession().getAttribute("login_account");


        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }
        //スケジュールの内容を取得
        List<Schedule> schedules = em.createNamedQuery("getMyAllSchedules", Schedule.class)
                                  .setParameter("account", login_account)
                                  .setFirstResult(15 * (page - 1))
                                  .setMaxResults(15)
                                  .getResultList();
//スケジュールの数を取得
        long schedules_count = (long)em.createNamedQuery("getMySchedulesCount", Long.class)
                                     .setParameter("account", login_account)
                                     .getSingleResult();

        List<Account_Team> teams = em.createNamedQuery("getMyTeams", Account_Team.class)
                .setFirstResult(15 * (page - 1))
                .setParameter("account_Id", login_account)
                .setMaxResults(15)
                .getResultList();

        em.close();
        //カレンダーのためのデータを取得
        Calendar cal = Calendar.getInstance();
        //現在の年と月を取得
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month++;
        //今月が何日あるか確認
        cal.set(year, month, 0);
        int thisMonthlastDay = cal.get(Calendar.DATE);

        request.setAttribute("year",year);
        request.setAttribute("month",month);
        request.setAttribute("date",thisMonthlastDay);

        request.setAttribute("teams", teams);
        request.setAttribute("schedules", schedules);
        request.setAttribute("schedules_count", schedules_count);
        request.setAttribute("page", page);

        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }

}
