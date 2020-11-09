package controllers.accounts;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Account;
import utils.DBUtil;

/**
 * Servlet implementation class AccountsEditServlet
 */
@WebServlet("/accounts/edit")
public class AccountsEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountsEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Account a = em.find(Account.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        request.setAttribute("account", a);
        request.setAttribute("_token", request.getSession().getId());
        request.getSession().setAttribute("account_id", a.getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/accounts/edit.jsp");
        rd.forward(request, response);
    }

}
