package filters;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import model.Book;
import model.Order;
import model.OrderItem;
import model.User;

/**
 * This filter is meant to load data from file into the contexts if it is not already there.
 * It also save the data back to the files on destroy.
 */
public class DataFilter implements Filter {
	
	private ServletContext context;

    /**
     * Default constructor. 
     */
    public DataFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	@SuppressWarnings("unchecked")
	public void destroy() {
		// write the contents of books and users back to file.
		try {
			URL bookURL = context.getResource("WEB-INF/books.txt");
			String bookPath = bookURL.getPath().substring(1); // removed initial slash
			BufferedWriter bw = new BufferedWriter(new FileWriter(bookPath));
			HashMap<Integer, Book> books = (HashMap<Integer, Book>)context.getAttribute("books");
			for (Book book: books.values()) {
				bw.write(book.getEntry());
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			URL userURL = context.getResource("WEB-INF/users.txt");
			String userPath = userURL.getPath().substring(1); // removed initial slash
			BufferedWriter bw = new BufferedWriter(new FileWriter(userPath));
			HashMap<Integer, User> users = (HashMap<Integer, User>)context.getAttribute("users");
			for (User user: users.values()) {
				bw.write(user.getEntry());
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			URL itemURL = context.getResource("WEB-INF/orderItems.txt");
			String itemPath = itemURL.getPath().substring(1); // removed initial slash
			BufferedWriter bw = new BufferedWriter(new FileWriter(itemPath));
			ArrayList<OrderItem> items = (ArrayList<OrderItem>)context.getAttribute("orderItems");
			for (OrderItem item: items) {
				bw.write(item.getEntry());
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Served by DataFilter");
		
		if (context.getAttribute("books") == null) {
			loadBooks();
		}
		if (context.getAttribute("users") == null) {
			loadUsers();
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		context = fConfig.getServletContext();
		
		System.out.println("Init method of DataFilter");
		if (context.getAttribute("books") == null) {
			loadBooks();
		}
		if (context.getAttribute("users") == null) {
			loadUsers();
		}
		if (context.getAttribute("orderItems") == null) {
			try {
				loadOrderItems();
				buildOrders();
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void buildOrders() {
		HashMap<Integer,Order> orders = new HashMap<Integer,Order>();
		ArrayList<OrderItem> items = (ArrayList<OrderItem>)context.getAttribute("orderItems");
		for (OrderItem item: items) {
			// if the order doesn't already exist, create it.  If it does, update it.
			if (orders.containsKey(item.getOrderId())) {
				orders.get(item.getOrderId()).addItem(item);
			} else {
				Order order = new Order(item);
				orders.put(item.getOrderId(), order);
			}
		}
		context.setAttribute("orders", orders);
	}

	private void loadOrderItems() throws java.text.ParseException {
		ArrayList<OrderItem> items = new ArrayList<OrderItem>();
		try {
			URL itemURL = context.getResource("WEB-INF/orderItems.txt");
			String itemPath = itemURL.getPath().substring(1); // removed initial slash
			BufferedReader br = new BufferedReader(new FileReader(itemPath));
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty()) {
					String data[] = line.split("::");
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Date date = formatter.parse(data[5]);
					items.add(new OrderItem(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]),
							Integer.parseInt(data[3]), Double.parseDouble(data[4]), date));
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		context.setAttribute("orderItems", items);
	}

	private void loadBooks() {
		HashMap<Integer, Book> books = new HashMap<Integer, Book>();
		try {
			URL bookURL = context.getResource("WEB-INF/books.txt");
			String bookPath = bookURL.getPath().substring(1); // removed initial slash
			BufferedReader br = new BufferedReader(new FileReader(bookPath));
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty()) {
					String data[] = line.split("::");
					books.put(Integer.parseInt(data[0]),
							new Book(Integer.parseInt(data[0]),	data[1], data[2], Double.parseDouble(data[3]),
									Integer.parseInt(data[4]), Integer.parseInt(data[5]))
					);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		context.setAttribute("books", books);
	}
	
	private void loadUsers() {
		HashMap<Integer, User> users = new HashMap<Integer, User>();
		try {
			URL userURL = context.getResource("WEB-INF/users.txt");
			String userPath = userURL.getPath().substring(1); // removed initial slash
			BufferedReader br = new BufferedReader(new FileReader(userPath));
			String line;
			while ((line = br.readLine()) != null) {
				String data[] = line.split("::");
				users.put(Integer.parseInt(data[0]),
						new User(Integer.parseInt(data[0]),	data[1], data[2], data[3], "1".contentEquals(data[4]), data[5])
				);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		context.setAttribute("users", users);
	}
}
