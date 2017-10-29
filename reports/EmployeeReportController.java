package reports;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import models.Employee;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class EmployeeReportController {

	BorderPane root = TireShop.getRoot();
	Connection con = TireShop.getConnection();

	@FXML
	private TextField filterField;
	@FXML
	private TableView<Employee> employeeTable;
	@FXML
	private TableColumn<Employee, Integer> employeeIDColumn;
	@FXML
	private TableColumn<Employee, String> firstNameColumn;
	@FXML
	private TableColumn<Employee, String> lastNameColumn;
	@FXML
	private TableColumn<Employee, String> passwordColumn;
	@FXML
	private TableColumn<Employee, Date> startDateColumn;
	@FXML
	private TableColumn<Employee, Boolean> isAdminColumn;
	@FXML
	private ObservableList<Employee> masterData = FXCollections.observableArrayList();
	@FXML
	private Button backButton;

	@FXML
	private void initialize() {

		String query = "Select * from employees";
		ResultSet rs;
		try {
			rs = con.createStatement().executeQuery(query);
			while (rs.next()) {

				Employee emp = new Employee(rs.getInt("employeeID"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getString("password"), rs.getDate("startDate"),
						rs.getBoolean("isAdmin"));
				masterData.add(emp);
				System.out.println(emp.toString());
			}
			System.out.println(masterData.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		employeeTable.setItems(masterData);
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
		
		passwordColumn.setCellValueFactory(new Callback<CellDataFeatures<Employee, String>, ObservableValue<String>>(){
			public ObservableValue<String> call(CellDataFeatures<Employee, String> p) {
				ObservableValue<String> obs = new ReadOnlyObjectWrapper<>(p.getValue().getPassword());
				return obs;
			}
		});
		
//		startDateColumn.setCellValueFactory(new Callback<CellDataFeatures<Employee, Date>, ObservableValue<Date>>(){
//			public ObservableValue<Date> call(CellDataFeatures<Employee, String> p) {
//				ObservableValue<Date> obs = new ReadOnlyObjectWrapper<>(p.getValue().getStartDate());
//				return obs;
//			}
//		});
		
		isAdminColumn.setCellValueFactory(new Callback<CellDataFeatures<Employee, Boolean>, ObservableValue<Boolean>>(){
			public ObservableValue<Boolean> call(CellDataFeatures<Employee, Boolean> p) {
				ObservableValue<Boolean> obs = new ReadOnlyObjectWrapper<>(p.getValue().isAdmin());
				return obs;
			}
		});

		FilteredList<Employee> filteredData = new FilteredList<>(masterData, p -> true);

		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(employee -> {
				// If filter text is empty, display all Employees.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every Employee with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (employee.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				} else if (employee.getLastName().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});

		SortedList<Employee> sortedData = new SortedList<>(filteredData);

		sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());

		employeeTable.setItems(sortedData);
		
		backButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Home.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
	}
}
