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

import models.Team;
import models.validators.TeamValidator;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class TeamsUpdateServlet
 */
@WebServlet("/teams/update")
public class TeamsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeamsUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Team t = em.find(Team.class, (Integer)(request.getSession().getAttribute("team_id")));

            // 現在の値と異なるチーム番号が入力されていたら
            // 重複チェックを行う指定をする
            Boolean team_code_duplicate_check = true;
            if(t.getTeam_code().equals(request.getParameter("team_code"))) {
                team_code_duplicate_check = false;
            } else {
                t.setTeam_code(request.getParameter("team_code"));
            }

            // パスワード欄に入力があったら
            // パスワードの入力値チェックを行う指定をする
            Boolean password_check_flag = true;
            String password = request.getParameter("password");
            if(password == null || password.equals("")) {
                password_check_flag = false;
            } else {
                t.setPassword(
                        EncryptUtil.getPasswordEncrypt(
                                password,
                                (String)this.getServletContext().getAttribute("pepper")
                                )
                        );
            }

            t.setName(request.getParameter("name"));
            t.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            List<String> errors = TeamValidator.validate(t, team_code_duplicate_check, password_check_flag);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("team", t);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/teams/edit.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "更新が完了しました。");

                request.getSession().removeAttribute("team_id");

                response.sendRedirect(request.getContextPath() + "/");
            }
        }

    }

}
