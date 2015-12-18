package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Book;
import model.Order;
import model.OrderItem;
import model.User;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// display the cart.  Will need a list of order items belonging to the logged in user.
		System.out.println("In the cartservlet doGet method");
		
		// get some date from the session and the context.
		Order cartOrder = (Order) request.getSession(true).getAttribute("cartOrder");
		User user = (User)request.getSession(true).getAttribute("user");
		HashMap<Integer,Book> books = (HashMap<Integer,Book>)request.getServletContext().getAttribute("books");
		if (cartOrder == null || cartOrder.getUserId() != user.getId()) {
			// create an empty cart for the current user
			cartOrder = new Order(generateOrderId(request.getServletContext()), user.getId());
			request.getSession(true).setAttribute("cartOrder", cartOrder);
		}
		
		// package up data for the view.
		request.setAttribute("books", books);
		request.setAttribute("user", user);
		request.setAttribute("cartOrder", cartOrder);
		request.getRequestDispatcher("jsps/cart.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// respond to items added to/ removed from cart.  Then pass on to doGet.
		String bookIdInput = request.getParameter("buyBook");
		if (bookIdInput != null) {
			// trying to add something to cart
			buyBook(request, Integer.parseInt(bookIdInput));
		} else if (request.getParameter("clearCart") != null) {
			// trying to clear the cart
			clearCart(request);
		} else if (request.getParameter("placeOrder") != null) {
			// trying to order the cart contents
			placeOrder(request);
		}
		// pass to doGet and display the cart.
		doGet(request, response);
	}
	
	@SuppressWarnings("unchecked")
	private void placeOrder(HttpServletRequest request) {
		Order cartOrder = (Order)request.getSession(true).getAttribute("cartOrder");
		if (cartOrder == null) {
			request.setAttribute("cartMessage", "Sorry, your cart has expired.  Order not placed.");
			return;
		}
		Order newOrder = new Order(cartOrder.getId(), cartOrder.getUserId()); // currently empty
		ArrayList<OrderItem> cartItems = cartOrder.getItems();
		HashMap<Integer,Book> books = (HashMap<Integer,Book>)request.getServletContext().getAttribute("books");
		HashMap<Integer,Order> orders = (HashMap<Integer,Order>)request.getServletContext().getAttribute("orders");
		ArrayList<OrderItem> orderItems = (ArrayList<OrderItem>)request.getServletContext().getAttribute("orderItems");
		boolean notAsOrdered = false;
		for (OrderItem item: cartItems) {
			Book book = books.get(item.getBookId());
			int actualBought = book.buy(item.getQuantity());
			if (actualBought < item.getQuantity()) {
				item.setQuantity(actualBought);
				notAsOrdered = true;
			}
			newOrder.addItem(item);
			orderItems.add(item);
		}
		if (notAsOrdered) {
			request.setAttribute("cartMessage", "Order placed, but some items became unavailable.  See the order for details");
		} else {
			request.setAttribute("cartMessage", "Your order has been placed. Check your orders for details.");
		}
		// make sure data persists, and clear the cart.
		request.getSession(true).setAttribute("cartOrder", null);
		orders.put(newOrder.getId(), newOrder);
		request.getServletContext().setAttribute("orders",orders);
		request.getServletContext().setAttribute("books", books);
		request.getServletContext().setAttribute("orderItems", orderItems);
	}

	private void clearCart(HttpServletRequest request) {
		request.getSession(true).setAttribute("cartOrder", null);
		request.setAttribute("cartMessage", "Your Cart has been Cleared");
	}

	@SuppressWarnings("unchecked")
	private void buyBook(HttpServletRequest request, int bookId) {
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		Book book = ((HashMap<Integer,Book>)request.getServletContext().getAttribute("books")).get(bookId);
		User user = (User)request.getSession(true).getAttribute("user");
		Date availDate = book.getArrivalDate(quantity);
		if (availDate != null) {
			// the requested number are available.  Add this order item to the cart order.
			Order cartOrder = (Order)request.getSession().getAttribute("cartOrder");
			if (cartOrder == null || cartOrder.getUserId() != user.getId()) {
				// have to create a new one.
				int orderId = generateOrderId(request.getServletContext());
				// create the order item and use it to create the order.
				OrderItem item = new OrderItem(orderId, user.getId(), book.getId(), quantity, book.getPrice(quantity),availDate);
				cartOrder = new Order(item);
				request.getSession(true).setAttribute("cartOrder", cartOrder);
			} else {
				// order exists, just add the item
				OrderItem item = new OrderItem(cartOrder.getId(), user.getId(), book.getId(), quantity, book.getPrice(quantity),availDate);
				cartOrder.addItem(item);
				request.getSession(true).setAttribute("cartOrder", cartOrder);
			}
			request.setAttribute("cartMessage", "Requested items added to Cart");
		} else {
			// that number of items no longer available
			request.setAttribute("cartMessage", "Sorry, the items you requested are no longer available");
		}
	}

	@SuppressWarnings("unchecked")
	private int generateOrderId(ServletContext context) {
		HashMap<Integer, Order> orderMap = (HashMap<Integer,Order>)context.getAttribute("orders");
		ArrayList<Order> orders = (orderMap == null) ? new ArrayList<Order>() : new ArrayList<Order>(orderMap.values());
		int maxId = 0;
		for (Order order: orders) {
			maxId = Math.max(maxId, order.getId());
		}
		return maxId + 1;
	}

}
