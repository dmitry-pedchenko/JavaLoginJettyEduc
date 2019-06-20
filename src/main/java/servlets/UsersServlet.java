package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersServlet extends HttpServlet {
    private String contentType = "text/html;charset=utf-8";
    private final AccountService accountService;

    public UsersServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(contentType);
        Gson gson = new Gson();
        response.getWriter().println(gson.toJson(accountService));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                  HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("loginSignUp");
        String pass = request.getParameter("passwordSignUp");
        String email = request.getParameter("email");

        if (login == null || pass == null) {
            response.setContentType(contentType);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserProfile userProfile = accountService.getUserByLogin(login);
        if (userProfile != null) {
            response.setContentType(contentType);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        accountService.addNewUser(new UserProfile(login, pass, email));
        response.setContentType(contentType);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPut(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

    }

    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

    }
}
