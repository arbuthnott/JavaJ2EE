/**
 * 
 */
package model;

/**
 * @author Chris Arbuthnott
 *
 */
public class User {

	private int id;
	private String name;
	private String email;
	private String password;
	private boolean isAdmin;
	private String imagePath;;
	
	public User(int id, String name, String email, String password, boolean isAdmin, String path) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.isAdmin = isAdmin;
		this.imagePath = (path != null) ? path : "userPics/default.png";
	}
	
	public void setImagePath(String path) {
		this.imagePath = path;
	}
	
	public String getImagePath() {
		if (imagePath == "userPics/default.png") {
			return imagePath;
		} else {
			return "userPics/" + id + ".png";
		}
	}
	
	public boolean validate(String eml, String pwd) {
		return (this.email.contentEquals(eml) && this.password.contentEquals(pwd));
	}
	
	public String getEntry() {
		String isAd = isAdmin ? "1" : "0";
		return "" + id + "::" + name + "::" + email + "::" + password + "::" + isAd + "::" + imagePath;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the isAdmin
	 */
	public boolean isAdmin() {
		return isAdmin;
	}
	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
