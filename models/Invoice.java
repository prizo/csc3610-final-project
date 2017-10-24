package models;

import java.sql.Date;

public class Invoice {
	
	private int invoiceID;		// PK
	private Date invoiceDate;
	private int customerID;		// FK
	private int employeeID;		// FK
	private int orderID;		// FK
	private int tireID;			// FK
	
	// Constructor for receiving objects from database
	public Invoice(int invoiceID, Date invoiceDate, int customerID, int employeeID, int orderID, int tireID) {
		this.invoiceID = invoiceID;
		this.invoiceDate = invoiceDate;
		this.customerID = customerID;
		this.employeeID = employeeID;
		this.orderID = orderID;
		this.tireID = tireID;
	}

	// Constructor for inserting objects into database
	public Invoice(Date invoiceDate, int customerID, int employeeID, int orderID, int tireID) {
		this.invoiceDate = invoiceDate;
		this.customerID = customerID;
		this.employeeID = employeeID;
		this.orderID = orderID;
		this.tireID = tireID;
	}

	public int getInvoiceID() {
		return invoiceID;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public int getCustomerID() {
		return customerID;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public int getOrderID() {
		return orderID;
	}

	public int getTireID() {
		return tireID;
	}

	@Override
	public String toString() {
		return "Invoice [invoiceID=" + invoiceID + ", invoiceDate=" + invoiceDate + ", customerID=" + customerID
				+ ", employeeID=" + employeeID + ", orderID=" + orderID + ", tireID=" + tireID + "]";
	}

}
