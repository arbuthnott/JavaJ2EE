package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoggedInFilter
 */
public class LoggedInFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoggedInFilter() {
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
		System.out.println("Served by LoggedInFilter");
		// check if logged in.
		HttpSession session = ((HttpServletRequest) request).getSession(true);
		if (session.getAttribute("user") != null) {
			// you are logged in, go on ahead.
			chain.doFilter(request, response);
		} else {
			// divert to login
			request.setAttribute("loginMessage", "Please log in to proceed");
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
