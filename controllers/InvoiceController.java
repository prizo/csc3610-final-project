package controllers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import helperclasses.JDBCConnector;
import helperclasses.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import models.Customer;
import models.Invoice;
import models.Order;
import models.Tire;

public class InvoiceController {

	ResultSet user = LoginController.getUser();
	Tire searchTire = SearchController.getTire();

	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	Connection con = new JDBCConnector().getConnection();

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
	private TextField tireBrand, tireName, rimDiameter, firstName, lastName, phoneNumber, tirePrice, laborCost, em;
	@FXML
	private Spinner<Integer> tireQuantity;
	@FXML
	private TextArea txtinvoice;
	@FXML
	private Button backButton, saveButton, clearButton, customerButton;
	@FXML
	private RadioButton installTires;

	@FXML
	private void initialize() throws ClassNotFoundException, SQLException {
		IntegerSpinnerValueFactory quantityValues = new IntegerSpinnerValueFactory(1, 16, 1);
		tireQuantity.setValueFactory(quantityValues);
		laborCost.setText("0");
		setSearchTire();
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
					searchTire.setName(results.getString("name"));
					searchTire.setBrand(results.getString("brand"));
					searchTire.setRimDiameter(results.getInt("rimdiameter"));
					searchTire.setPrice(results.getInt("price"));
					searchTire.setTireID(results.getInt("tireID"));
					setSearchTire();
				}

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		clearButton.setOnAction((event) -> {
			clearFields();

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

			Customer invoiceCust = CreateCustomer();
			Order invoiceOrder = CreateOrder();
			//Checks that the customer and order aren't null before trying to create an invoice.
			if (invoiceCust != null && invoiceOrder != null) {
				CreateInvoice(invoiceCust, invoiceOrder);

				String invoiceString = ("First Name: " + firstName.getText() + "\nLast Name: " + lastName.getText()
						+ "\nPhone Number: " + phoneNumber.getText() + "\nEmail: " + em.getText() + "\nTire Name: "
						+ tireName.getText() + "\nTire Brand: " + tireBrand.getText() + "\nRim Diameter: "
						+ rimDiameter.getText() + "\nQuantity: " + tireQuantity.getValue() + "\nTire Price: "
						+ tirePrice.getText() + "\nLabor Cost : $" + laborCost.getText());
				txtinvoice.setText(invoiceString);
				double totalPrice = (tireQuantity.getValue() * Double.parseDouble(tirePrice.getText())
						+ Double.parseDouble(laborCost.getText()));
				String orderText = "You ordered " + tireQuantity.getValue() + " " + tireName.getText()
						+ "\nEach tire cost $" + tirePrice.getText() + "\nYour labor cost is $" + laborCost.getText()
						+ "\nYour total price is $" + totalPrice;
				Alert invoiceAlert = new Alert(AlertType.INFORMATION);
				invoiceAlert.setTitle("Order Placed");
				invoiceAlert.setHeaderText("Thank you for shopping with group 4 tire shop.");
				invoiceAlert.setContentText(orderText);
				invoiceAlert.showAndWait();
				clearFields();
				sendEmail(invoiceCust.getEmail(), orderText);
			}
		});

		backButton.setOnAction(e -> {
			sceneSwitcher.switchScene(backButton, "/views/Home.fxml");
		});

		installTires.setOnAction(e -> {
			if (installTires.isSelected()) {
				laborCost.setText("" + (10 * (tireQuantity.getValue())));
			} else
				laborCost.setText("0");
		});

		tireQuantity.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (installTires.isSelected()) {
				laborCost.setText("" + (10 * tireQuantity.getValue()));
			} else
				laborCost.setText("0");
		});
	}

	private void setSearchTire() {
		tireName.setText(searchTire.getName());
		tireBrand.setText(searchTire.getBrand());
		tirePrice.setText(Double.toString(searchTire.getPrice()));
		rimDiameter.setText(Integer.toString(searchTire.getRimDiameter()));
	}

	private Customer CreateCustomer() {

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
		if (errorMessage.length() != 0) {
			Alert alert = new Alert(AlertType.ERROR, errorMessage.toString());
			alert.setTitle("Missing Customer Information");
			alert.setHeaderText("Please fill in the empty fields.");
			alert.showAndWait();
		}

		else {
			try {
				String query = "{call proc_InsertCustomer(?, ?, ?, ?, ?)}";

				CallableStatement cStmt = con.prepareCall(query);
				cStmt.setString(1, firstName.getText());
				cStmt.setString(2, lastName.getText());
				cStmt.setString(3, phoneNumber.getText());
				cStmt.setString(4, em.getText());
				cStmt.registerOutParameter(5, java.sql.Types.INTEGER);
				cStmt.execute();
				int customerID = cStmt.getInt(5);

				Customer cust = new Customer(customerID, firstName.getText(), lastName.getText(), phoneNumber.getText(),
						em.getText());

				return cust;

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	private Order CreateOrder() {

		StringBuilder errorMessage = new StringBuilder();

		if (searchTire.getName().equals("")) {
			errorMessage.append("Please select a tire.\n");
		}
		
		if (errorMessage.length() != 0) {
			Alert alert = new Alert(AlertType.ERROR, errorMessage.toString());
			alert.setTitle("Missing Tire");
			alert.setHeaderText("Tire not selected.");
			alert.showAndWait();
		}

		else {
			try {
				String query = " insert into orders (tireID, orderDate, quantity, laborCost)" + " values (?, ?, ?, ?)";
				Date orderDate = new Date();
				java.sql.Date sqlOrderDate = new java.sql.Date(orderDate.getTime());
				PreparedStatement preparedStmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				preparedStmt.setInt(1, searchTire.getTireID());
				preparedStmt.setDate(2, (java.sql.Date) sqlOrderDate);
				preparedStmt.setInt(3, tireQuantity.getValue());
				preparedStmt.setDouble(4, Double.parseDouble(laborCost.getText()));
				preparedStmt.execute();
				ResultSet rs = preparedStmt.getGeneratedKeys();
				if (rs.next()) {
					int orderID = rs.getInt(1);
					Order order = new Order(orderID, sqlOrderDate, tireQuantity.getValue(),
							Double.parseDouble(laborCost.getText()), searchTire.getTireID());
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

	private Invoice CreateInvoice(Customer cust, Order order) {
		try {
			String query = " insert into invoices (employeeID, customerID, orderID, invoiceDate)"
					+ " values (?, ?, ?, ?)";
			Date invoiceDate = new Date();
			java.sql.Date sqlInvoiceDate = new java.sql.Date(invoiceDate.getTime());
			PreparedStatement preparedStmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setInt(1, user.getInt("employeeID"));
			preparedStmt.setInt(2, cust.getCustomerID());
			preparedStmt.setInt(3, order.getOrderID());
			preparedStmt.setDate(4, sqlInvoiceDate);
			preparedStmt.execute();
			ResultSet rs = preparedStmt.getGeneratedKeys();
			if (rs.next()) {
				int invoiceID = rs.getInt(1);
				Invoice invoice = new Invoice(invoiceID, sqlInvoiceDate, cust.getCustomerID(),
						user.getInt("employeeID"), order.getOrderID());
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
		ResultSet rs = statement.executeQuery("Select email from customers order by email ASC");
		customerList.removeAll(customerList);
		while (rs.next()) {
			customerList.add(rs.getString("email"));
		}
		// Loads the customer emails in to the customer combo box
		cmbCustomer.setItems(customerList);
	}

	private void clearFields() {
		tireName.clear();
		tireBrand.clear();
		rimDiameter.clear();
		tireQuantity.getValueFactory().setValue(1);
		tirePrice.clear();
		laborCost.setText("0");
		firstName.clear();
		lastName.clear();
		phoneNumber.clear();
		em.clear();
		installTires.setSelected(false);
		txtinvoice.clear();

	}

	private void sendEmail(String recipientEmail, String emailText) {
		final String username = "Group4TireShop@gmail.com";
		final String password = "csc3810'";

		Properties props = new Properties();
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("no-reply@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
			message.setSubject("Thank you for ordering tires using group 4 tire shop");
			message.setText(emailText);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}