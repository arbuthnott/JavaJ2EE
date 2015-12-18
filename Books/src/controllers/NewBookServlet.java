package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Book;
import model.User;

/**
 * Servlet implementation class NewBookServlet
 */
@WebServlet("/admin/new")
public class NewBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// display the new book form
		request.setAttribute("user", (User)request.getSession(true).getAttribute("user"));
		request.getRequestDispatcher("/jsps/newBook.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("user", (User)request.getSession(true).getAttribute("user"));
		// handle new inventory creations.  Then back to view page for new book with an adminMessage
		if (request.getParameter("newBook") != null) {
			String title = request.getParameter("title");
			String author = request.getParameter("author");
			double price = Double.parseDouble(request.getParameter("price"));
			int localStock = Integer.parseInt(request.getParameter("localStock"));
			int remoteStock = Integer.parseInt(request.getParameter("remoteStock"));
			int id = generateBookId(request);
			Book book = new Book(id, title, author, price, localStock, remoteStock);
			HashMap<Integer,Book> books = (HashMap<Integer,Book>)request.getServletContext().getAttribute("books");
			books.put(id, book);
			// persist the data
			request.getServletContext().setAttribute("books", books);
			request.setAttribute("book", book);
			request.setAttribute("adminMessage", title + " has been added to the inventory");
			request.getRequestDispatcher("/jsps/bookDetail.jsp").forward(request, response);
		} else {
			// send on back to book list.
			HashMap<Integer,Book> bookMap = (HashMap<Integer,Book>)request.getServletContext().getAttribute("books");
			ArrayList<Book> books = new ArrayList<Book>(bookMap.values());
			request.setAttribute("books", books);
			request.getRequestDispatcher("/jsps/bookList.jsp").forward(request, response);
		}
	}

	@SuppressWarnings("unchecked")
	private int generateBookId(HttpServletRequest request) {
		int max = 0;
		HashMap<Integer,Book> books = (HashMap<Integer,Book>)request.getServletContext().getAttribute("books");
		for (Book book: books.values()) {
			if (book.getId() > max) {
				max = book.getId();
			}
		}
		return max + 1;
	}

}
