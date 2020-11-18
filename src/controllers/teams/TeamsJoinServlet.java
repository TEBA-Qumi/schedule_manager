package controllers.teams;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Account;
import models.Account_Team;
import models.Team;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class TeamsJoinServlet
 */
@WebServlet("/teams/join")
public class TeamsJoinServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeamsJoinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/teams/join.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Boolean check_result = false;

        String team_code = request.getParameter("team_code");
        String plain_pass = request.getParameter("password");

        Team t = null;

        if(team_code != null && !team_code.equals("") && plain_pass != null && !plain_pass.equals("")) {
            EntityManager em = DBUtil.createEntityManager();

            String password = EncryptUtil.getPasswordEncrypt(
                    plain_pass,
                    (String)this.getServletContext().getAttribute("pepper")
                    );

     // チーム番号とパスワードが正しいかチェックする
        try {
            //正しかった場合、チームオブジェクトを取得
            t = em.createNamedQuery("checkJoinCodeAndPassword", Team.class)
                  .setParameter("team_code", team_code)
                  .setParameter("pass", password)
                  .getSingleResult();
        } catch(NoResultException ex) {}

        em.close();

        if(t != null) {
            check_result = true;
        }
    }

    if(!check_result) {
        // 認証できなかったらログイン画面に戻る
        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("hasError", true);
        request.setAttribute("team_code", team_code);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/teams/join.jsp");
        rd.forward(request, response);
    } else {
        // 認証できたらログイン状態にしてトップページへリダイレクト
        EntityManager em = DBUtil.createEntityManager();

        Account_Team a_t = new Account_Team();
        //a_tオブジェクトにそれぞれのオブジェクトを格納
        a_t.setAccount_Id((Account) request.getSession().getAttribute("login_account"));
        a_t.setTeam_Id(t);

        em.getTransaction().begin();
        em.persist(a_t);
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", "参加しました。");
        response.sendRedirect(request.getContextPath() + "/");
    }

    }

}
