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
import models.Schedule;
import utils.DBUtil;

/**
 * Servlet implementation class DailySchedulesServlet
 */
@WebServlet("/schedules/daily")
public class DailySchedulesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DailySchedulesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }
        //チームidと日付が指定されていた場合
        if(request.getParameter("schedule_date") != null && request.getParameter("id") != null){
            //チームidを取得
            int id = Integer.parseInt(request.getParameter("id"));
            //日付データを取得
            Date schedule_date = new Date(System.currentTimeMillis());
            String rd_str = request.getParameter("schedule_date");
            if(rd_str != null && !rd_str.equals("")){
                schedule_date = Date.valueOf(request.getParameter("schedule_date"));
            }

          //日付ごとのスケジュールを取得
            List<Schedule> schedules = em.createNamedQuery("getTeamDailySchedules", Schedule.class)
                    .setParameter("id", id)
                    .setParameter("schedule_date", schedule_date)
                    .setFirstResult(15 * (page - 1))
                    .setMaxResults(15)
                    .getResultList();

            em.close();

            request.setAttribute("schedules", schedules);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/schedules/teamDaily.jsp");
            rd.forward(request, response);
        }else{
            //セッションからアカウントオブジェクトを取得
            Account login_account = (Account)request.getSession().getAttribute("login_account");
            //日付データを取得
            Date schedule_date = new Date(System.currentTimeMillis());
            String rd_str = request.getParameter("schedule_date");
            if(rd_str != null && !rd_str.equals("")){
                schedule_date = Date.valueOf(request.getParameter("schedule_date"));
            }

            //日付ごとのスケジュールを取得
            List<Schedule> schedules = em.createNamedQuery("getMyDailySchedules", Schedule.class)
                    .setParameter("account", login_account)
                    .setParameter("schedule_date", schedule_date)
                    .setFirstResult(15 * (page - 1))
                    .setMaxResults(15)
                    .getResultList();

            em.close();

            request.setAttribute("schedules", schedules);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/schedules/daily.jsp");
            rd.forward(request, response);
        }

    }

}
