package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/image")
@MultipartConfig
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in doPost of image servlet");
		HashMap<Integer,User> users = (HashMap<Integer,User>)request.getServletContext().getAttribute("users");
		User user = (User)request.getSession().getAttribute("user");
		String filename = "" + user.getId() + ".png";
		// need to fill imgPath with the path to the desired folder.
		//String imgPath = request.getServletContext().getRealPath("/") + "userPics\\" + filename;
		
		File base = new File(System.getProperty("catalina.home"));
		//File uploadDir = new File(base.getAbsolutePath() + File.separator + "userPics");
		String imgPath = base.getAbsolutePath() + File.separator + "userPics" + File.separator + filename;
		
		System.out.println("Path: " + imgPath);
		
		InputStream fileContent = null;
		
		try(OutputStream out = new FileOutputStream(new File(imgPath))) {
			fileContent = request.getPart("image").getInputStream();
			int read = 0;
			final byte[] bytes = new byte[1024];
			while ((read = fileContent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			request.setAttribute("profileMessage", "Your profile picture is uploaded");
			// modify the user
			URL newURL = request.getServletContext().getResource("userPics" + File.separator + filename);
			String newPath = newURL.getPath().substring(1);
			
			user.setImagePath(newPath);
			users.put(user.getId(), user);
			request.getServletContext().setAttribute("users", users);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/profile").forward(request, response);
	}

}
