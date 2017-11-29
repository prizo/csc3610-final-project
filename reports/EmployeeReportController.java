package reports;

import java.sql.Connection;
import java.sql.Date;
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
import models.Employee;

public class EmployeeReportController {
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	Connection con = new JDBCConnector().getConnection();

	@FXML
	private TextField sumField;

	@FXML
	private TableColumn<Employee, Integer> employeeIDColumn;
	@FXML
	private TableColumn<Employee, String> firstNameColumn;
	@FXML
	private TableColumn<Employee, String> lastNameColumn;
	@FXML
	private TableColumn<Employee, Date> startDateColumn;
	@FXML
	private TableColumn<Employee, Boolean> isAdminColumn;
	@FXML
	private TableView<Employee> employeeTable;
	@FXML
	private ObservableList<Employee> masterData = FXCollections.observableArrayList();
	@FXML
	private Button backButton, employeeButton, tireButton, customerButton, orderButton, invoiceButton;
	@FXML
	private void initialize() {

		String query = "Select * from employees";
		ResultSet rs;
		try {
			rs = con.createStatement().executeQuery(query);
			while (rs.next()) {
				//Creates an employee using data from the database then adds it to masterData
				Employee test = new Employee(rs.getInt("employeeID"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getString("password"), rs.getDate("startDate"),
						rs.getBoolean("isAdmin"));
				masterData.add(test);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Sets the list of employees in to the table
		employeeTable.setItems(masterData);
		
		//Assigns the employees values in to the columns
		employeeIDColumn.setCellValueFactory(new Callback<CellDataFeatures<Employee, Integer>, ObservableValue<Integer>>(){
			public ObservableValue<Integer> call(CellDataFeatures<Employee, Integer> p) {
				ObservableValue<Integer> obs = new ReadOnlyObjectWrapper<>(p.getValue().getEmployeeID());
				return obs;
			}
		});
		
		firstNameColumn.setCellValueFactory(new Callback<CellDataFeatures<Employee, String>, ObservableValue<String>>(){
			public ObservableValue<String> call(CellDataFeatures<Employee, String> p) {
				ObservableValue<String> obs = new ReadOnlyObjectWrapper<>(p.getValue().getFirstName());
				return obs;
			}
		});
		
		lastNameColumn.setCellValueFactory(new Callback<CellDataFeatures<Employee, String>, ObservableValue<String>>(){
			public ObservableValue<String> call(CellDataFeatures<Employee, String> p) {
				ObservableValue<String> obs = new ReadOnlyObjectWrapper<>(p.getValue().getLastName());
				return obs;
			}
		});
		
		startDateColumn.setCellValueFactory(new Callback<CellDataFeatures<Employee, Date>, ObservableValue<Date>>(){
			public ObservableValue<Date> call(CellDataFeatures<Employee, Date> p) {
				ObservableValue<Date> obs = new ReadOnlyObjectWrapper<>(p.getValue().getStartDate());
				return obs;
			}
		});
		
		isAdminColumn.setCellValueFactory(new Callback<CellDataFeatures<Employee, Boolean>, ObservableValue<Boolean>>(){
			public ObservableValue<Boolean> call(CellDataFeatures<Employee, Boolean> p) {
				ObservableValue<Boolean> obs = new ReadOnlyObjectWrapper<>(p.getValue().isAdmin());
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
