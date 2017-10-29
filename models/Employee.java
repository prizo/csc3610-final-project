package models;

import java.sql.Date;

public class Employee {
	
	private int employeeID;		// PK
	private String firstName;
	private String lastName;
	private String password;
	private Date startDate;
	private boolean isAdmin;
	
	// Constructor for receiving objects from database
	public Employee(int employeeID, String firstName, String lastName, String password, Date startDate,boolean isAdmin) {
		this.employeeID = employeeID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.startDate = startDate;
		this.isAdmin = isAdmin;
	}

	// Constructor for inserting objects into database
	public Employee(String firstName, String lastName, String password, Date startDate, boolean isAdmin) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.startDate = startDate;
		this.isAdmin = isAdmin;
	}

	

	public int getEmployeeID() {
		return employeeID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public Date getStartDate() {
		return startDate;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + ", startDate=" + startDate + ", isAdmin=" + isAdmin + "]";
	}

}
