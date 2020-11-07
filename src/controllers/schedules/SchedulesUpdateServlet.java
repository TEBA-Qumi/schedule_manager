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

import models.Schedule;
import models.validators.ScheduleValidator;
import utils.DBUtil;

/**
 * Servlet implementation class SchedulesUpdateServlet
 */
@WebServlet("/schedules/update")
public class SchedulesUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SchedulesUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //セッションIDを取得
        String _token = (String)request.getParameter("_token");
        //セッションIDが現在のセッションIDと合っていたら下記の処理を行う
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();
            //投稿のIDと一致するデータを取得
            Schedule s = em.find(Schedule.class, (Integer)(request.getSession().getAttribute("schedule_id")));
            //フォームに入力された値をs オブジェクトに格納
            s.setSchedule_date(Date.valueOf(request.getParameter("schedule_date")));
            s.setTitle(request.getParameter("title"));
            s.setContent(request.getParameter("content"));
            s.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            //入力された値に不備があるかのチェック
            List<String> errors = ScheduleValidator.validate(s);
            if(errors.size() > 0) {
                //エラーがあったら下記の処理を行う
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("report", s);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/schedules/edit.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "更新が完了しました。");

                request.getSession().removeAttribute("schedule_id");

                response.sendRedirect(request.getContextPath() + "/schedules/index");
            }
        }
    }

}
