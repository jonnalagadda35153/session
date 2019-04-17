package com.servlet.session;

import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;
/**
 * Servlet implementation class LoginServlet
 *
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private final static Logger logger = Logger.getLogger(LoginServlet.class.getName());
    private final String username = "admin";
    private final String password = "password";

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        // get request parameters for username and password
        String username = request.getParameter("username");
        String password = request.getParameter("pwd");

        logger.info("Trying to login with username: "+username);
        logger.info("Password as: "+password);



        if (this.username.equals(username) && this.password.equals(password)) {
            //get the old session and invalidate
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
                logger.info("Invalidating old session");
            }
            //generate a new session
            HttpSession newSession = request.getSession(true);
            logger.info("Creating a new session");

            //setting session to expiry in 5 mins
            newSession.setMaxInactiveInterval(5*60);

            Cookie message = new Cookie("message", "Welcome");
            response.addCookie(message);
            response.sendRedirect("admin/LoginSuccess.jsp");
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>Either username or password is wrong.</font>");
            rd.include(request, response);
            logger.info("Incorrect username or password");
        }
    }
}