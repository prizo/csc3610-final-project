package models;

import java.sql.Date;

import javafx.beans.value.ObservableValue;

public class Employee {
	
	private int employeeID;		// PK
	private String firstName;
	private String lastName;
	private String password;
	private Date startDate;
	private boolean isAdmin;
	
	private ObservableValue<Integer> employeeIDProperty;
	private ObservableValue<String> firstNameProperty;
	private ObservableValue<String> lastNameProperty;
	private ObservableValue<String> passwordProperty;
	private ObservableValue<Date> startDateProperty;
	private ObservableValue<Boolean> isAdminProperty;
	
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

	
	public ObservableValue<Integer> getEmployeeIDProperty() {
		return employeeIDProperty;
	}

	public void setEmployeeIDProperty(ObservableValue<Integer> employeeIDProperty) {
		this.employeeIDProperty = employeeIDProperty;
	}

	public ObservableValue<String> getFirstNameProperty() {
		return firstNameProperty;
	}

	public void setFirstNameProperty(ObservableValue<String> firstNameProperty) {
		this.firstNameProperty = firstNameProperty;
	}

	public ObservableValue<String> getLastNameProperty() {
		return lastNameProperty;
	}

	public void setLastNameProperty(ObservableValue<String> lastNameProperty) {
		this.lastNameProperty = lastNameProperty;
	}

	public ObservableValue<String> getPasswordProperty() {
		return passwordProperty;
	}

	public void setPasswordProperty(ObservableValue<String> passwordProperty) {
		this.passwordProperty = passwordProperty;
	}

	public ObservableValue<Date> getStartDateProperty() {
		return startDateProperty;
	}

	public void setStartDateProperty(ObservableValue<Date> startDateProperty) {
		this.startDateProperty = startDateProperty;
	}

	public ObservableValue<Boolean> getIsAdminProperty() {
		return isAdminProperty;
	}

	public void setIsAdminProperty(ObservableValue<Boolean> isAdminProperty) {
		this.isAdminProperty = isAdminProperty;
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
