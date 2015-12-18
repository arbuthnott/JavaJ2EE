package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderItem {
	
	private int orderId;
	private int userId;
	private int bookId;
	private int quantity;
	private double price;
	private Date arrivalDate;
	
	public OrderItem(int orderId, int userId, int bookId, int quantity, double price, Date arrivalDate) {
		this.setOrderId(orderId);
		this.setUserId(userId);
		this.setBookId(bookId);
		this.setQuantity(quantity);
		this.setPrice(price);
		this.setArrivalDate(arrivalDate);
	}
	
	public boolean equals(OrderItem otherItem) {
		return (this.orderId == otherItem.getOrderId() &&
				this.userId == otherItem.getUserId() &&
				this.bookId == otherItem.getBookId() &&
				this.quantity == otherItem.getQuantity() &&
				this.arrivalDate == otherItem.getArrivalDate()
		);
				
	}
	
	public String getEntry() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(this.arrivalDate);
		return ""+this.orderId + "::" + this.userId + "::" + this.bookId + "::" + this.quantity + "::" + this.price + "::" + date;
	}

	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the bookId
	 */
	public int getBookId() {
		return bookId;
	}

	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	
	public String getPriceString() {
		return String.format("$%.2f", price);
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the arrivalDate
	 */
	public Date getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * @param arrivalDate the arrivalDate to set
	 */
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
