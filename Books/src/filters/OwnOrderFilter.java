package filters;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import model.Order;
import model.User;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class OwnOrderFilter
 */
public class OwnOrderFilter implements Filter {

    /**
     * Default constructor. 
     */
    public OwnOrderFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Served by the OwnOrderFilter");
		
		String inputOrderId = request.getParameter("id");
		if (inputOrderId != null) {
			int orderId = Integer.parseInt(inputOrderId);
			Order order = ((HashMap<Integer,Order>)request.getServletContext().getAttribute("orders")).get(orderId);
			User user = (User) ((HttpServletRequest)request).getSession().getAttribute("user");
			if (user.getId() == order.getUserId()) {
				// carry on then
				chain.doFilter(request, response);
			} else {
				// redirect to profile page?
				request.getRequestDispatcher("/profile").forward(request, response);
			}
		} else {
			// redirect to profile page?
			request.getRequestDispatcher("/profile").forward(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
