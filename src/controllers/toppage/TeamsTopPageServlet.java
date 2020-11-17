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

import models.Schedule;
import utils.DBUtil;

/**
 * Servlet implementation class TeamsTopPageServlet
 */
@WebServlet("/teams/topPage")
public class TeamsTopPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeamsTopPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        if(request.getParameter("id") != null){
            //チームIdを取得
            Integer id = Integer.parseInt(request.getParameter("id"));

            int page;
            try{
                page = Integer.parseInt(request.getParameter("page"));
            } catch(Exception e) {
                page = 1;
            }

            //チームごとのスケジュールの内容を取得
            List<Schedule> schedules = em.createNamedQuery("getTeamsAllSchedules", Schedule.class)
                    .setFirstResult(15 * (page - 1))
                    .setParameter("share_flag", id)
                    .setMaxResults(15)
                    .getResultList();

            em.close();

            request.setAttribute("schedules", schedules);
            request.setAttribute("page", page);
        }
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


        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/teams.jsp");
        rd.forward(request, response);
    }

}
