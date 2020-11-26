package controllers.teams;

import java.io.IOException;
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
import models.Account_Team;
import models.Team;
import models.validators.TeamValidator;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class TeamsCreateServlet
 */
@WebServlet("/teams/create")
public class TeamsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeamsCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId()) ){
            EntityManager em = DBUtil.createEntityManager();
            //チームオブジェクトを作成
            Team t = new Team();
            //入力された値をオブジェクトに格納
            t.setTeam_code(request.getParameter("team_code"));
            t.setName(request.getParameter("name"));
            t.setPassword(
                EncryptUtil.getPasswordEncrypt(
                    request.getParameter("password"),
                        (String)this.getServletContext().getAttribute("pepper")
                    )
                );
            //現在の日時を取得し、オブジェクトに格納
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            t.setCreated_at(currentTime);
            t.setUpdated_at(currentTime);

            List<String> errors = TeamValidator.validate(t, true, true);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("team", t);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/teams/new.jsp");
                rd.forward(request, response);
            } else {
                //チームの作成を確定
                em.getTransaction().begin();
                em.persist(t);

                //作成したチームに参加させる
                Account_Team a_t = new Account_Team();
              //a_tオブジェクトにそれぞれのオブジェクトを格納
                a_t.setAccount_Id((Account) request.getSession().getAttribute("login_account"));
                a_t.setTeam_Id(t);
                em.persist(a_t);
                em.getTransaction().commit();
                request.getSession().setAttribute("flush", "チームの作成が完了しました。");
                em.close();

                response.sendRedirect(request.getContextPath() + "/");
            }
        }
    }

}
