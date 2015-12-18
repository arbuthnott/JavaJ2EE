package controllers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Book;
import model.User;

/**
 * Servlet implementation class FrontServlet
 */
@WebServlet({ "/index.jsp" })
public class FrontServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet method of FrontServlet");
		User user = (User)request.getSession(true).getAttribute("user");
		String username = (user != null) ? user.getName() : "Guest";
		request.setAttribute("username", username);
		request.setAttribute("user", (User)request.getSession(true).getAttribute("user"));
		if (request.getParameter("id") != null) {
			// display the book detail.
			HashMap<Integer, Book> books = (HashMap<Integer, Book>)request.getServletContext().getAttribute("books");
			Book book = books.get(Integer.parseInt(request.getParameter("id").toString()));
			if (book != null) {
				request.setAttribute("book", book);
				request.getRequestDispatcher("jsps/bookDetail.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("jsps/front.jsp").forward(request, response);
			}
		} else {
			// display the home page
			request.getRequestDispatcher("jsps/front.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// pass on
		doGet(request, response);
	}

}
