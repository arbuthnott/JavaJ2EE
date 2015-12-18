package controllers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// check if a logout is requested
		String action = request.getParameter("action");
		if (action != null && action.contentEquals("logout")) {
			request.getSession(true).setAttribute("user", null);
			request.setAttribute("loginMessage", "You are logged out");
		}
		
		// forward to the login page
		request.setAttribute("user", (User)request.getSession(true).getAttribute("user"));
		request.getRequestDispatcher("jsps/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// try to log in.
		System.out.println("In the doPost of login servlet");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		//System.out.println("Got data: " + email + ", " + password);
		if (email != null && password != null) {
			// we have creds to check.
			HttpSession session = ((HttpServletRequest) request).getSession(true);
			HashMap<Integer,User> users = (HashMap<Integer,User>)request.getServletContext().getAttribute("users");
			boolean valid = false;
			for (User user: users.values()) {
				if (user.validate(email, password)) {
					// found a match
					//System.out.println("Found matching user: " + user.getName());
					session.setAttribute("user", user);
					valid = true;
					break;
				}
			}
			if (!valid) {
				// these creds don't match records.  Back to login.
				request.setAttribute("loginMessage", "Bad email or password. Please check and retry.");
				request.getRequestDispatcher("jsps/login.jsp").forward(request, response);
			} else {
				// check if there's a destination in mind.
				String destination = (request.getParameter("destination")!=null) ? request.getParameter("destination") : "/books/index.jsp";
				request.setAttribute("user", (User)request.getSession(true).getAttribute("user"));
				request.getRequestDispatcher(destination).forward(request, response);
			}
		} else {
			// creds are missing - back to login.
			request.setAttribute("user", (User)request.getSession(true).getAttribute("user"));
			request.getRequestDispatcher("jsps/login.jsp").forward(request, response);
		}
	}

}
