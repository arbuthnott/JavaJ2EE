package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import model.User;

/**
 * Servlet Filter implementation class AdminFilter
 */
public class AdminFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AdminFilter() {
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Served by the AdminFilter");
		User user = (User)((HttpServletRequest) request).getSession().getAttribute("user");
		if (user.isAdmin()) {
			// on your way then.
			chain.doFilter(request, response);
		} else {
			request.setAttribute("loginMessage", "Please log in as an admin user to continue");
			String rawURL = ((HttpServletRequest)request).getRequestURL().toString();
			String destination = rawURL.substring(rawURL.indexOf("/books") + 6);
			request.setAttribute("destination", destination);
			request.getRequestDispatcher("/jsps/login.jsp").forward(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
