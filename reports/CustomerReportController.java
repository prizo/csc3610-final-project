package reports;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import helperclasses.JDBCConnector;
import helperclasses.SceneSwitcher;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import models.Customer;

public class CustomerReportController {
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	Connection con = new JDBCConnector().getConnection();

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
				Customer test = new Customer(rs.getInt("customerID"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getString("phoneNumber"), rs.getString("email"));
				masterData.add(test);
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
			sceneSwitcher.switchScene(backButton, "/views/Dashboard.fxml");
		});
		
		employeeButton.setOnAction(e -> {
			sceneSwitcher.switchScene(employeeButton, "/reports/EmployeeReport.fxml");
		});
		
		tireButton.setOnAction(e -> {
			sceneSwitcher.switchScene(tireButton, "/reports/TireReport.fxml");
		});
		
		customerButton.setOnAction(e -> {
			sceneSwitcher.switchScene(customerButton, "/reports/CustomerReport.fxml");
		});
		
		orderButton.setOnAction(e -> {
			sceneSwitcher.switchScene(orderButton, "/reports/OrderReport.fxml");
		});
		
		invoiceButton.setOnAction(e -> {
			sceneSwitcher.switchScene(invoiceButton, "/reports/InvoiceReport.fxml");
		});
	}
}
