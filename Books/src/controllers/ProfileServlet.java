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
import model.Order;
import model.User;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
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
	@SuppressWarnings({ "unchecked" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet method of ProfileServlet");
		User user = (User)request.getSession(true).getAttribute("user");
		request.setAttribute("user", user);
		HashMap<Integer,Order> ordersMap = (HashMap<Integer,Order>)request.getServletContext().getAttribute("orders");
		ArrayList<Order> orders = new ArrayList<Order>();
		for (Order order: ordersMap.values()) {
			if (order.getUserId() == user.getId()) {
				orders.add(order);
			}
		}
		
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("jsps/profile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// handle changes to profile
		System.out.println("In doPost method of profile servlet");
		String inputImageId = request.getParameter("imageUpload");
		String inputEditId = request.getParameter("editProfile");
		String inputUpdateId = request.getParameter("updateProfile");
		
		if (inputEditId != null) {
			// go to edit form
			request.setAttribute("user", (User)request.getSession(true).getAttribute("user"));
			request.getRequestDispatcher("/jsps/profileEdit.jsp").forward(request, response);
		} else if (inputUpdateId != null) {
			int userId = Integer.parseInt(inputUpdateId);
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			HashMap<Integer,User> users = (HashMap<Integer,User>)request.getServletContext().getAttribute("users");
			User user = users.get(userId);
			user.setName(name);
			user.setEmail(email);
			users.put(userId, user);
			request.getSession(true).setAttribute("user", user);
			request.getServletContext().setAttribute("users",users);
			request.setAttribute("profileMessage", "Your Profile has been updated");
			doGet(request, response);
		} else {
			doGet(request, response);
		}
	}

}
