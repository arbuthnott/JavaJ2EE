package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Order {
	
	private int id;
	private int userId;
	private ArrayList<OrderItem> items;
	private boolean paid;
	private boolean delivered;
	
	public Order(OrderItem item) {
		this.id = item.getOrderId();
		this.userId = item.getUserId();
		this.items = new ArrayList<OrderItem>();
		this.items.add(item);
		this.setPaid(false);
		this.setDelivered(false);
	}
	
	public Order(int orderId, int userId) {
		this.id = orderId;
		this.userId = userId;
		this.items = new ArrayList<OrderItem>();
		this.setPaid(false);
		this.setDelivered(false);
	}
	
	public void addItem(OrderItem item) {
		this.items.add(item);
	}
	
	public ArrayList<OrderItem> getItems() {
		return items;
	}
	
	public double getPrice() {
		double price = 0;
		for (OrderItem item: this.items) {
			price += item.getPrice();
		}
		return price;
	}
	
	public String getPriceString() {
		double price = getPrice();
		return String.format("$%.2f", price);
	}
	
	public Date getReadyDate() {
		Date date = new Date();
		for (OrderItem item: this.items) {
			if (item.getArrivalDate().after(date)) {
				date = item.getArrivalDate();
			}
		}
		return date;
	}
	
	public String getDateString() {
		Date date = getReadyDate();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getQuantity() {
		int quant = 0;
		for (OrderItem item: this.items) {
			quant += item.getQuantity();
		}
		return quant;
	}
	
	public int getUserId() {
		return this.userId;
	}

	/**
	 * @return the paid
	 */
	public boolean isPaid() {
		return paid;
	}

	/**
	 * @param paid the paid to set
	 */
	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	/**
	 * @return the delivered
	 */
	public boolean isDelivered() {
		return delivered;
	}

	/**
	 * @param delivered the delivered to set
	 */
	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

}
