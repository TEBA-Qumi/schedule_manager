package controllers.toppage;

import java.io.IOException;
import java.util.ArrayList;
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
            //スケジュールの数を取得
            long schedules_count = (long)em.createNamedQuery("getTeamsSchedulesCount", Long.class)
                    .setParameter("share_flag", id)
                    .getSingleResult();


            em.close();

            request.setAttribute("schedules", schedules);
            request.setAttribute("page", page);
            request.setAttribute("id", id);
            request.setAttribute("schedules_count", schedules_count);
        }

        //カレンダーのためのデータを取得
        Calendar cal = Calendar.getInstance();
        //現在の年と月を取得
        int year = cal.get(Calendar.YEAR);

      //指定月がある場合
        int month;
        try{
            month = Integer.parseInt(request.getParameter("month"));
        }
        //指定が無ければ
          catch(Exception e){
            month =  cal.get(Calendar.MONTH);
        }

        //今月1日の曜日を取得
        cal.set(year,month,1);
        //今月の1日の曜日を求める※0=日曜　6=土曜にする(ArrayListのスタートが0なので合わせる)
        int firstWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;

        //今月が何日あるか確認
        cal.set(year, (month + 1), 0);
        int thisMonthlastDay = cal.get(Calendar.DATE);


        //カレンダー
        List<Integer> week = new ArrayList<Integer>();

        //カレンダーの空白制御
        int day = 1;
        for(int i = 0; ; i++){
            //カレンダーの先頭空白
            if(i < firstWeek){
                week.add(null);
            //日を入れる
            }else if(thisMonthlastDay >= day){
                week.add(day++);
            }
            //終日を迎えたらループを終了
            if(thisMonthlastDay < day) break;
        }

        if(month > 12 ){
            year++;
        }
        //カレンダーに使う値をセット
        request.setAttribute("year",year);
        request.setAttribute("month",(month + 1));
        request.setAttribute("date",thisMonthlastDay);

        request.setAttribute("week",week);

        request.setAttribute("nextMonth",(month + 1));
        request.setAttribute("prevMonth",(month - 1));

        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/teams.jsp");
        rd.forward(request, response);
    }

}
