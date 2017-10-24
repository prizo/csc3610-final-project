package models;

import java.sql.Date;

public class Order {
	
	private int orderID;		// PK
	private Date orderDate;
	private int quantity;
	private int tireID;			// FK
	
	// Constructor for receiving objects from database
	public Order(int orderID, Date orderDate, int quantity, int tireID) {
		this.orderID = orderID;
		this.orderDate = orderDate;
		this.quantity = quantity;
		this.tireID = tireID;
	}

	// Constructor for inserting objects into database
	public Order(Date orderDate, int quantity, int tireID) {
		this.orderDate = orderDate;
		this.quantity = quantity;
		this.tireID = tireID;
	}

	public int getOrderID() {
		return orderID;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getTireID() {
		return tireID;
	}

	@Override
	public String toString() {
		return "Order [orderID=" + orderID + ", orderDate=" + orderDate + ", quantity=" + quantity + ", tireID="
				+ tireID + "]";
	}

}
