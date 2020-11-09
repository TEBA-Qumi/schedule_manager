package controllers.schedules;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
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
import models.validators.ScheduleValidator;
import utils.DBUtil;

/**
 * Servlet implementation class SchedulesCreateServlet
 */
@WebServlet("/schedules/create")
public class SchedulesCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SchedulesCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //セッションIDを取得
        String _token = (String)request.getParameter("_token");
        //取得したセッションIDと現在のセッションIDが一致した場合に実行する
        if(_token != null && _token.equals(request.getSession().getId()) ){
            EntityManager em = DBUtil.createEntityManager();

            Schedule s = new Schedule();

            //ログインしているアカウントのデータを取得
            s.setAccount((Account)request.getSession().getAttribute("login_account"));
            //現在の日付を取得
            Date schedule_date = new Date(System.currentTimeMillis());
            //スケジュールの日付をString型で取得
            String rd_str = request.getParameter("schedule_date");
            //取得したすけじゅーる　の日付をschedule_dateに格納
            if(rd_str != null && !rd_str.equals("")){
                schedule_date = Date.valueOf(request.getParameter("schedule_date"));
            }
            s.setSchedule_date(schedule_date);
            //フォームで入力された値を取得
            s.setTitle(request.getParameter("title"));
            s.setContent(request.getParameter("content"));
            s.setShare_flag(Integer.parseInt(request.getParameter("share_flag")) );

            //日時を取得
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            s.setCreated_at(currentTime);
            s.setUpdated_at(currentTime);
            s.setFinish_flag(0);

            //入力されたデータにエラーがあるかチェック
            List<String> errors = ScheduleValidator.validate(s);
            //エラーがあった際は追加フォームに戻る
            if(errors.size() > 0){
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("schedule", s);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/schedules/new.jsp");
                rd.forward(request, response);
            //エラーが無ければ取得したデータをDBに追加し、一覧ページにリダイレクト
            } else {
                em.getTransaction().begin();
                em.persist(s);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "追加しました。");

                response.sendRedirect(request.getContextPath() + "/");
            }

        }
    }

}
