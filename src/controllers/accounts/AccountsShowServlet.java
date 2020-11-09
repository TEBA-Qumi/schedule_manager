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
 * Servlet implementation class AccountsShowServlet
 */
@WebServlet("/accounts/show")
public class AccountsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountsShowServlet() {
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

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/accounts/show.jsp");
        rd.forward(request, response);
    }

}
