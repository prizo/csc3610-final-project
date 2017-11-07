package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.TireShop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import models.Customer;
import models.Invoice;
import models.Order;

public class InvoiceController {

	BorderPane root = TireShop.getRoot();
	Connection con = TireShop.getConnection();
	ResultSet user = LoginController.getUser();

	@FXML
	private ListView<String> tireView;
	@FXML
	private ComboBox<String> cmbTireBrand, cmbCustomer;
	@FXML
	private ObservableList<String> tireNameList = FXCollections.observableArrayList();
	@FXML
	private ObservableList<String> tireBrandList = FXCollections.observableArrayList();
	@FXML
	private ObservableList<String> customerList = FXCollections.observableArrayList();
	@FXML
	private TextField tireBrand, tireName, rimDiameter, firstName, lastName, phoneNumber, tireQuantity, tirePrice,
			laborCost, em, tireID;
	@FXML
	private TextArea txtinvoice;
	@FXML
	private Button backButton, saveButton, clearButton, customerButton;
	@FXML
	private RadioButton installTires;

	@FXML
	private void initialize() throws ClassNotFoundException, SQLException {

		// setTireNames();
		setTireBrands();
		setCustomers();

		tireView.setOnMouseClicked(e -> {

			try {
				String query = "select * from tires where name = '" + tireView.getSelectionModel().getSelectedItem()
						+ "'";

				Statement statement = con.createStatement();
				// Queries the database for the selected item
				ResultSet results = statement.executeQuery(query);
				// Fills in the data fields with the queried items attributes
				while (results.next()) {
					tireName.setText(results.getString("name"));
					tireBrand.setText(results.getString("brand"));
					rimDiameter.setText(results.getString("rimdiameter"));
					tirePrice.setText("$" + results.getString("price"));
					tireID.setText(results.getString("tireID"));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		clearButton.setOnAction((event) -> {
			ClearFields();

		});
		cmbTireBrand.setOnAction((event) -> {
			try {
				Statement statement = con.createStatement();
				String query = "select * from tires where brand = '"
						+ cmbTireBrand.getSelectionModel().getSelectedItem() + "'";
				ResultSet results = statement.executeQuery(query);
				// Fills in the data fields with the queried items attributes
				tireNameList.removeAll(tireNameList);
				while (results.next()) {
					tireNameList.add(results.getString("name"));
				}
				tireView.setItems(tireNameList);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		cmbCustomer.setOnAction((event) -> {
			try {
				Statement statement = con.createStatement();
				String query = "select * from customers where email = '"
						+ cmbCustomer.getSelectionModel().getSelectedItem() + "'";
				ResultSet results = statement.executeQuery(query);
				// Fills in the data fields with the queried items attributes
				while (results.next()) {
					firstName.setText(results.getString("firstName"));
					lastName.setText(results.getString("lastName"));
					phoneNumber.setText(results.getString("phoneNumber"));
					em.setText(results.getString("email"));
				}
				tireView.setItems(tireNameList);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		saveButton.setOnAction((event) -> {

			Customer invoiceCust = CheckCustomer();
			Order invoiceOrder = CheckOrder();
			Invoice invoice = CheckInvoice(invoiceCust, invoiceOrder);
			
			String invoiceString = 	("First Name: " + firstName.getText() + "\nLast Name: " + lastName.getText() + "\nPhone Number: " + 
					phoneNumber.getText() + "\nEmail: " + em.getText()+ "\nTire Name: " + tireName.getText() +
					"\nTire Brand: " + tireBrand.getText()  + "\nRim Diameter: " + rimDiameter.getText() +
					"\nQuantity: " + tireQuantity.getText() + "\nTire Price: " + tirePrice.getText() +
					"\nLabor Cost : $" + laborCost.getText());
			txtinvoice.setText(invoiceString);
			ClearFields();
		});

		backButton.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource("/views/Home.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		customerButton.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource("/views/Customer.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});

		
	}

	
	


	/*
	 * private void setTireNames() throws ClassNotFoundException, SQLException {
	 * Statement statement = con.createStatement(); ResultSet rs =
	 * statement.executeQuery("Select * from tires order by name");
	 * tireNameList.removeAll(tireNameList); // Adds every name from tires to the
	 * name list while (rs.next()) { tireNameList.add(rs.getString("name")); } //
	 * Loads the tire name list in to the tire view tireView.setItems(tireNameList);
	 * }
	 */
	private Customer CheckCustomer() {
		
		StringBuilder errorMessage = new StringBuilder();

		
		if (firstName.getText().length() == 0) {
			errorMessage.append("First name is empty.\n");			
		}
		
		if (lastName.getText().length() == 0) {
			errorMessage.append("Last name is empty.\n");
		}
		if (phoneNumber.getText().length() == 0) {
			errorMessage.append("Phone number is empty.\n");
		}
		if (em.getText().length() == 0) {
			errorMessage.append("Email is empty.\n");
		}			
		if (errorMessage.length() != 0){
			Alert alert = new Alert(AlertType.ERROR, errorMessage.toString() );
			alert.showAndWait();
		}

		else {
			try {
				String query = " insert into customers (firstName, lastName, phoneNumber, email)"
				        + " values (?, ?, ?, ?)";

				      PreparedStatement preparedStmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				      preparedStmt.setString (1, firstName.getText());
				      preparedStmt.setString (2, lastName.getText());
				      preparedStmt.setString (3, phoneNumber.getText());
				      preparedStmt.setString (4, em.getText());
				      
				      preparedStmt.execute();
				      ResultSet rs = preparedStmt.getGeneratedKeys();
				      if(rs.next()) {
					      int customerID = rs.getInt(1);
					      Customer cust = new Customer(customerID, firstName.getText(), lastName.getText(), phoneNumber.getText(), em.getText());
					      rs.close();
					      preparedStmt.close();
					      return cust;
				      }
			
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return null;
		
	}
	
	private Order CheckOrder() {
		
		StringBuilder errorMessage = new StringBuilder();
		
		if (tireName.getText().length() == 0) {
			errorMessage.append("Select a tire.\n");
		}
		
		if (tireQuantity.getText().length() == 0) {
			errorMessage.append("Enter a quantity.\n");
		}
		
		if (laborCost.getText().length() == 0) {
			errorMessage.append("Enter a labor cost.\n");
		}
		
		if (errorMessage.length() != 0){
			Alert alert = new Alert(AlertType.ERROR, errorMessage.toString() );
			alert.showAndWait();
		}
		
		else {
			try {
				String query = " insert into orders (tireID, orderDate, quantity, laborCost)"
				        + " values (?, ?, ?, ?)";
				Date orderDate = new Date();
				java.sql.Date sqlOrderDate = new java.sql.Date(orderDate.getTime());
				      PreparedStatement preparedStmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				      preparedStmt.setString (1, tireID.getText());
				      preparedStmt.setDate (2, (java.sql.Date) sqlOrderDate);
				      preparedStmt.setString (3, tireQuantity.getText());		
				      preparedStmt.setDouble(4, Double.parseDouble(laborCost.getText()));
				      preparedStmt.execute();		
				      ResultSet rs = preparedStmt.getGeneratedKeys();
				      if(rs.next()) {
					      int orderID = rs.getInt(1);
					      Order order = new Order(orderID, sqlOrderDate, Integer.parseInt(tireQuantity.getText()), Double.parseDouble(laborCost.getText()), Integer.parseInt(tireID.getText()));
					      rs.close();
					      preparedStmt.close();
					      return order;
				      }
			}
			
			 catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	private Invoice CheckInvoice(Customer cust, Order order) {
		try {
			String query = " insert into invoices (employeeID, customerID, orderID, invoiceDate)"
			        + " values (?, ?, ?, ?)";
			Date invoiceDate = new Date();
			java.sql.Date sqlInvoiceDate = new java.sql.Date(invoiceDate.getTime());
			      PreparedStatement preparedStmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			      preparedStmt.setInt (1, user.getInt("employeeID"));
			      preparedStmt.setInt (2, cust.getCustomerID());
			      preparedStmt.setInt (3, order.getOrderID());	
			      preparedStmt.setDate(4, sqlInvoiceDate);
			      preparedStmt.execute();		
			      ResultSet rs = preparedStmt.getGeneratedKeys();
			      if(rs.next()) {
				      int invoiceID = rs.getInt(1);
				      Invoice invoice = new Invoice(invoiceID, sqlInvoiceDate, cust.getCustomerID(), user.getInt("employeeID"), order.getOrderID());		      
				      rs.close();
				      preparedStmt.close();
				      return invoice;
			      }
		}
		
		 catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		

		return null;
	}
	
	private void setTireBrands() throws ClassNotFoundException, SQLException {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery("Select distinct brand from tires order by brand");
		tireBrandList.removeAll(tireBrandList);
		while (rs.next()) {
			tireBrandList.add(rs.getString("brand"));
		}
		// Loads the tire brand list in to the tire brand
		cmbTireBrand.setItems(tireBrandList);
	}
	
	private void setCustomers() throws ClassNotFoundException, SQLException {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery("Select email from customers");
		customerList.removeAll(customerList);
		while (rs.next()) {
			customerList.add(rs.getString("email"));
		}
		// Loads the tire brand list in to the tire brand
		cmbCustomer.setItems(customerList);
	}

	private void ClearFields() {
		tireName.clear();
		tireBrand.clear();
		rimDiameter.clear();
		tireQuantity.clear();
		tirePrice.clear();
		laborCost.clear();
		firstName.clear();
		lastName.clear();
		phoneNumber.clear();
		em.clear();
		installTires.setSelected(false);
	}
}
