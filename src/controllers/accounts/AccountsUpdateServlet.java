package controllers.accounts;

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
import models.validators.AccountValidator;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class AccountsUpdateServlet
 */
@WebServlet("/accounts/update")
public class AccountsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountsUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //セッションIDを取得
        String _token = (String)request.getParameter("_token");

        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Account a = em.find(Account.class, (Integer)(request.getSession().getAttribute("account_id")));

            // 現在の値と異なるアカウント番号が入力されていたら
            // 重複チェックを行う指定をする
            Boolean code_duplicate_check = true;
            if(a.getCode().equals(request.getParameter("code"))) {
                code_duplicate_check = false;
            } else {
                a.setCode(request.getParameter("code"));
            }

            // パスワード欄に入力があったら
            // パスワードの入力値チェックを行う指定をする
            Boolean password_check_flag = true;
            String password = request.getParameter("password");
            if(password == null || password.equals("")) {
                password_check_flag = false;
            } else {
                a.setPassword(
                        EncryptUtil.getPasswordEncrypt(
                                password,
                                (String)this.getServletContext().getAttribute("pepper")
                                )
                        );
            }

            a.setName(request.getParameter("name"));
            a.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            a.setDelete_flag(0);

            List<String> errors = AccountValidator.validate(a, code_duplicate_check, password_check_flag);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("account", a);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/accounts/edit.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "更新が完了しました。");

                request.getSession().removeAttribute("account_id");

                response.sendRedirect(request.getContextPath() + "/");
            }
        }

    }

}
