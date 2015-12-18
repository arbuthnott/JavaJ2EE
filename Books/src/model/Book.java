package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Book {

	private int id;
	private String title;
	private String author;
	private double price;
	private int localStock;
	private int remoteStock;
	
	public static int LOCAL_DELIVERY_TIME = 2;
	public static int REMOTE_DELIVERY_TIME = 5;
	
	
	public Book(int id, String title, String author, double price, int localStock, int remoteStock) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.price = price;
		this.localStock = localStock;
		this.remoteStock = remoteStock;
	}
	
	public String getEntry() {
		return "" + id + "::" + title + "::" + author + "::" + String.format("%.2f", price) + "::" + localStock + "::" + remoteStock;
	}
	
	// This method detects the arrival date for ordering the input quantity of books.
	// If the quantity is beyond both local and remote stocks, null is returned.
	public Date getArrivalDate(int quant) {
		Date date;
		if (quant <= this.localStock) {
			date = new Date(new Date().getTime() + 1000*60*60*24*LOCAL_DELIVERY_TIME);
		} else if (quant <= this.localStock + this.remoteStock) {
			date = new Date(new Date().getTime() + 1000*60*60*24*REMOTE_DELIVERY_TIME);
		} else {
			date = null;
		}
		return date;
	}
	
	// Buy the input number of books, subtracting from local Stock first.
	// return the number that are actually available.
	public int buy(int quant) {
		if (quant <= this.localStock) {
			localStock -= quant;
			return quant;
		} else if (quant <= this.localStock + this.remoteStock) {
			remoteStock -= (quant - localStock);
			localStock = 0;
			return quant;
		} else {
			int available = localStock + remoteStock;
			localStock = 0;
			remoteStock = 0;
			return available;
		}
	}
	
	public double getPrice(int quantity) {
		return quantity * price;
	}
	
	public String getPriceString(int quantity) {
		double total = quantity * price;
		return String.format("$%.2f", total);
	}
	
	public String getAuthorAlias() {
		ArrayList<String> words = new ArrayList<String>(Arrays.asList(author.split(" ")));
		String alias = "";
		for (String word: words) {
			alias += word.substring(0,1).toUpperCase();
			alias += word.substring(1).toLowerCase();
		}
		return alias;
	}
	
	public String getTitleAlias() {
		ArrayList<String> words = new ArrayList<String>(Arrays.asList(title.split(" ")));
		String alias = "";
		for (String word: words) {
			alias += word.substring(0,1).toUpperCase();
			alias += word.substring(1).toLowerCase();
		}
		return alias;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the localStock
	 */
	public int getLocalStock() {
		return localStock;
	}
	/**
	 * @param localStock the localStock to set
	 */
	public void setLocalStock(int localStock) {
		this.localStock = localStock;
	}
	/**
	 * @return the remoteStock
	 */
	public int getRemoteStock() {
		return remoteStock;
	}
	/**
	 * @param remoteStock the remoteStock to set
	 */
	public void setRemoteStock(int remoteStock) {
		this.remoteStock = remoteStock;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
}
