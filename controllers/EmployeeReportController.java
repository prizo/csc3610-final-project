package controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.TireShop;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
	private void initialize() {

		String query = "Select * from employees";
		ResultSet rs;
		try {
			rs = con.createStatement().executeQuery(query);
			while (rs.next()) {

				Employee emp = new Employee(rs.getInt("employeeID"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getString("password"), rs.getDate("startDate"),
						rs.getBoolean("isAdmin"));
//				masterData.add(new Employee(rs.getInt("employeeID"), rs.getString("firstName"),
//						rs.getString("lastName"), rs.getString("password"), rs.getDate("startDate"),
//						rs.getBoolean("isAdmin")));
				masterData.add(emp);
				System.out.println(emp.toString());
			}
			System.out.println(masterData.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		employeeTable.setItems(masterData);
		employeeIDColumn.setCellValueFactory(cellData ->(cellData.getValue().getEmployeeIDProperty()));
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
		passwordColumn.setCellValueFactory(cellData -> cellData.getValue().getPasswordProperty());
		startDateColumn.setCellValueFactory(cellData -> cellData.getValue().getStartDateProperty());
		isAdminColumn.setCellValueFactory(cellData -> cellData.getValue().getIsAdminProperty());

		FilteredList<Employee> filteredData = new FilteredList<>(masterData, p -> true);
		// 2. Set the filter Predicate whenever the filter changes.
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

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<Employee> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		employeeTable.setItems(sortedData);
	}

}
