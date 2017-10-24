package models;

import java.sql.Date;
import java.sql.Time;

public class Appointment {
	
	private int appointmentID;		// PK
	private Date appointmentDate;
	private Time appointmentTime;
	private int customerID;			// FK
	private int employeeID;			// FK
	
	// Constructor for receiving objects from database
	public Appointment(int appointmentID, Date appointmentDate, Time appointmentTime, int customerID, int employeeID) {
		this.appointmentID = appointmentID;
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
		this.customerID = customerID;
		this.employeeID = employeeID;
	}

	// Constructor for inserting objects into database
	public Appointment(Date appointmentDate, Time appointmentTime, int customerID, int employeeID) {
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
		this.customerID = customerID;
		this.employeeID = employeeID;
	}

	public int getAppointmentID() {
		return appointmentID;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public Time getAppointmentTime() {
		return appointmentTime;
	}

	public int getCustomerID() {
		return customerID;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	@Override
	public String toString() {
		return "Appointment [appointmentID=" + appointmentID + ", appointmentDate=" + appointmentDate
				+ ", appointmentTime=" + appointmentTime + ", customerID=" + customerID + ", employeeID=" + employeeID
				+ "]";
	}

}
