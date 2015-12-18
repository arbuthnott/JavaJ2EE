package controllers;

import java.io.IOException;
import java.util.ArrayList;
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
 * Servlet implementation class ListServlet
 */
@WebServlet("/list")
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListServlet() {
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
		System.out.println("doGet method of ListServlet");
		HashMap<Integer, Book> books = (HashMap<Integer, Book>)request.getServletContext().getAttribute("books");
		ArrayList<Book> bookList;
		if (request.getParameter("auth") != null) {
			// display a particular author
			String reqAlias = request.getParameter("auth");
			bookList = new ArrayList<Book>();
			boolean authorFound = false;
			for (Book book: books.values()) {
				if (book.getAuthorAlias().contentEquals(reqAlias)) {
					bookList.add(book);
					System.out.println("Added book: " + book.getTitle());
					if (!authorFound) {
						request.setAttribute("author", book.getAuthor());
						request.setAttribute("subtitle", "All books by " + book.getAuthor());
						authorFound = true;
					}
				}
			}
		} else {
			bookList = new ArrayList<Book>(books.values());
			request.setAttribute("subtitle", "All books");
		}
		request.setAttribute("books", bookList);
		request.setAttribute("user", (User)request.getSession(true).getAttribute("user"));
		request.getRequestDispatcher("jsps/bookList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
