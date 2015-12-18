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
import model.Order;
import model.User;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({"/admin", "/admin/index.html"})
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("user", (User)request.getSession(true).getAttribute("user"));
		if (request.getParameter("book") != null) {
			// show the admin form for editing a book in inventory
			int bookId = Integer.parseInt(request.getParameter("book"));
			Book book = ((HashMap<Integer,Book>)request.getServletContext().getAttribute("books")).get(bookId);
			request.setAttribute("book", book);
			request.getRequestDispatcher("jsps/bookInventory.jsp").forward(request, response);;
		} else if (request.getParameter("order") != null) {
			// show the admin view of a particular order
			int orderId = Integer.parseInt(request.getParameter("order"));
			Order order = ((HashMap<Integer,Order>)request.getServletContext().getAttribute("orders")).get(orderId);
			HashMap<Integer,User> users = (HashMap<Integer,User>)request.getServletContext().getAttribute("users");
			request.setAttribute("users", users);
			request.setAttribute("order", order);
			request.getRequestDispatcher("/jsps/order.jsp").forward(request, response);
		} else {
			// show the main admin page.  Need the orders as array.  Need user hashmap
			HashMap<Integer,Order> orderMap = (HashMap<Integer, Order>) request.getServletContext().getAttribute("orders");
			ArrayList<Order> orders = new ArrayList<Order>(orderMap.values());
			request.setAttribute("orders", orders);
			request.setAttribute("users", (HashMap<Integer, User>)request.getServletContext().getAttribute("users"));
			request.getRequestDispatcher("jsps/admin.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("user", (User)request.getSession(true).getAttribute("user"));
		String bookIdInput = request.getParameter("updateBook");
		String orderInput = request.getParameter("updateOrder");
		if (bookIdInput != null) {
			// handle the update request, then pass back to book detail view.
			HashMap<Integer,Book> books = (HashMap<Integer,Book>)request.getServletContext().getAttribute("books");
			Book book = books.get(Integer.parseInt(bookIdInput));
			// make the changes
			book.setTitle(request.getParameter("title"));
			book.setAuthor(request.getParameter("author"));
			book.setPrice(Double.parseDouble(request.getParameter("price")));
			book.setLocalStock(Integer.parseInt(request.getParameter("localStock")));
			book.setRemoteStock(Integer.parseInt(request.getParameter("remoteStock")));
			// persist the changes
			books.put(book.getId(), book);
			request.getServletContext().setAttribute("books", books);
			// send back to book detail view
			request.setAttribute("book",book);
			request.setAttribute("adminMessage", "Inventory changes applied successfully");
			request.getRequestDispatcher("/jsps/bookDetail.jsp").forward(request, response);
		} else if (orderInput != null) {
			// handle the order update, then pass back to order detail view.
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			HashMap<Integer,Order> orderMap = (HashMap<Integer,Order>)request.getServletContext().getAttribute("orders");
			Order order = orderMap.get(orderId);
			if (orderInput.contentEquals("paid")) {
				order.setPaid(true);
				orderMap.put(orderId, order);
				request.setAttribute("adminMessage", "The order has been updated.");
			} else if (orderInput.contentEquals("delivered")) {
				order.setDelivered(true);
				orderMap.put(orderId, order);
				request.setAttribute("adminMessage", "The order has been updated.");
			} else if (orderInput.contentEquals("delete")) {
				orderMap.remove(orderId);
				request.setAttribute("adminMessage", "The order has been deleted.");
			}
			// persist the change.
			request.getServletContext().setAttribute("orders", orderMap);
			// pass back to a view.
			ArrayList<Order> orders = new ArrayList<Order>(orderMap.values());
			request.setAttribute("orders", orders);
			request.setAttribute("users", (HashMap<Integer, User>)request.getServletContext().getAttribute("users"));
			request.getRequestDispatcher("/jsps/admin.jsp").forward(request, response);
			
		} else {
			doGet(request,response);
		}
	}

}
