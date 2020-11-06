package controllers.schedules;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Schedule;

/**
 * Servlet implementation class SchedulesMewServlet
 */
@WebServlet("/schedules/new")
public class SchedulesMewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SchedulesMewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //セッションIDを取得
        request.setAttribute("_token", request.getSession().getId());

        //Scheduleオブジェクトを作成
        Schedule s = new Schedule();
        //作成されたオブジェクトに日付を格納
        s.setSchedule_date(new Date(System.currentTimeMillis()));
        request.setAttribute("schedule", s);

        //requestオブジェクトに格納されたデータをnew.jspに送信
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/schedules/new.jsp");
        rd.forward(request, response);
    }

}
