package reports;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.TireShop;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import models.Customer;

public class CustomerReportController {

	BorderPane root = TireShop.getRoot();
	Connection con = TireShop.getConnection();

	@FXML
	private TextField sumField;
	@FXML
	private TableView<Customer> customerTable;
	@FXML
	private TableColumn<Customer, Integer> customerIDColumn;
	@FXML
	private TableColumn<Customer, String> firstNameColumn;
	@FXML
	private TableColumn<Customer, String> lastNameColumn;
	@FXML
	private TableColumn<Customer, String> phoneNumberColumn;
	@FXML
	private TableColumn<Customer, String> emailColumn;
	@FXML
	private ObservableList<Customer> masterData = FXCollections.observableArrayList();
	@FXML
	private Button backButton, employeeButton, tireButton, customerButton, orderButton, invoiceButton;
	@FXML
	private void initialize() {

		String query = "Select * from customers";
		ResultSet rs;
		try {
			rs = con.createStatement().executeQuery(query);
			while (rs.next()) {
				//Creates an customer using data from the database then adds it to masterData
				Customer emp = new Customer(rs.getInt("customerID"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getString("phoneNumber"), rs.getString("email"));
				masterData.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Sets the list of customers in to the table
		customerTable.setItems(masterData);
		
		//Assigns the customers values in to the columns
		customerIDColumn.setCellValueFactory(new Callback<CellDataFeatures<Customer, Integer>, ObservableValue<Integer>>(){
			public ObservableValue<Integer> call(CellDataFeatures<Customer, Integer> p) {
				ObservableValue<Integer> obs = new ReadOnlyObjectWrapper<>(p.getValue().getCustomerID());
				return obs;
			}
		});
		
		firstNameColumn.setCellValueFactory(new Callback<CellDataFeatures<Customer, String>, ObservableValue<String>>(){
			public ObservableValue<String> call(CellDataFeatures<Customer, String> p) {
				ObservableValue<String> obs = new ReadOnlyObjectWrapper<>(p.getValue().getFirstName());
				return obs;
			}
		});
		
		lastNameColumn.setCellValueFactory(new Callback<CellDataFeatures<Customer, String>, ObservableValue<String>>(){
			public ObservableValue<String> call(CellDataFeatures<Customer, String> p) {
				ObservableValue<String> obs = new ReadOnlyObjectWrapper<>(p.getValue().getLastName());
				return obs;
			}
		});
		
		phoneNumberColumn.setCellValueFactory(new Callback<CellDataFeatures<Customer, String>, ObservableValue<String>>(){
			public ObservableValue<String> call(CellDataFeatures<Customer, String> p) {
				ObservableValue<String> obs = new ReadOnlyObjectWrapper<>(p.getValue().getPhoneNumber());
				return obs;
			}
		});
		
		emailColumn.setCellValueFactory(new Callback<CellDataFeatures<Customer, String>, ObservableValue<String>>(){
			public ObservableValue<String> call(CellDataFeatures<Customer, String> p) {
				ObservableValue<String> obs = new ReadOnlyObjectWrapper<>(p.getValue().getEmail());
				return obs;
			}
		});

		sumField.setText(Integer.toString(masterData.size()));
		
		backButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/reports/Home.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		employeeButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/reports/EmployeeReport.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		tireButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/reports/TireReport.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		customerButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/reports/CustomerReport.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		orderButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/reports/OrderReport.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		invoiceButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/reports/InvoiceReport.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
	}
}
