package controllers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import helperclasses.JDBCConnector;
import helperclasses.SceneSwitcher;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import misc.DateComparator;
import models.Employee;

public class AdminPageController {

	ResultSet user = AdminLoginController.getUser();

	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	Connection connection = new JDBCConnector().getConnection();

	@FXML
	private Button btnCreate, btnUpdate, btnDelete, btnBack;

	@FXML
	private TableView<Employee> tableEmployee;

	@FXML
	private TableColumn<Employee, Integer> colID;

	@FXML
	private TableColumn<Employee, String> colFName;

	@FXML
	private TableColumn<Employee, String> colLName;

	@FXML
	private TableColumn<Employee, Date> colDate;

	@FXML
	private TableColumn<Employee, String> colAdmin;

	@FXML
	private ObservableList<Employee> empData = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		show();
	}

	public void show() {

		btnCreate.setOnAction(e -> {
			create();
		});

		btnUpdate.setOnAction(e -> {
			if (tableEmployee.getSelectionModel().getSelectedItem() == null) {
				Alert alert = new Alert(AlertType.ERROR, "Please select an employee to update!");
				alert.showAndWait();
			} else {
				update(tableEmployee.getSelectionModel().getSelectedItem().getEmployeeID());
			}
		});

		btnDelete.setOnAction(e -> {
			if (tableEmployee.getSelectionModel().getSelectedItem() == null) {
				Alert alert = new Alert(AlertType.ERROR, "Please select an employee to delete!");
				alert.showAndWait();
			} else {
				delete(tableEmployee.getSelectionModel().getSelectedItem().getEmployeeID());
			}
		});

		btnBack.setOnAction(e -> {
			sceneSwitcher.switchScene(btnBack, "/views/Dashboard.fxml");

			try {
				System.out.println("Admin " + user.getString("firstName") + " " + user.getString("lastName")
						+ " logged out on " + new Date());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		List<Employee> data = read();

		Collections.sort(data, new DateComparator());

		empData.addAll(data);

		tableEmployee.setItems(empData);

		colID.setCellValueFactory(new Callback<CellDataFeatures<Employee, Integer>, ObservableValue<Integer>>() {
			public ObservableValue<Integer> call(CellDataFeatures<Employee, Integer> p) {
				ObservableValue<Integer> obs = new ReadOnlyObjectWrapper<>(p.getValue().getEmployeeID());
				return obs;
			}
		});

		colFName.setCellValueFactory(new Callback<CellDataFeatures<Employee, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Employee, String> p) {
				ObservableValue<String> obs = new ReadOnlyObjectWrapper<>(p.getValue().getFirstName());
				return obs;
			}
		});

		colLName.setCellValueFactory(new Callback<CellDataFeatures<Employee, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Employee, String> p) {
				ObservableValue<String> obs = new ReadOnlyObjectWrapper<>(p.getValue().getLastName());
				return obs;
			}
		});

		colDate.setCellValueFactory(new Callback<CellDataFeatures<Employee, Date>, ObservableValue<Date>>() {
			public ObservableValue<Date> call(CellDataFeatures<Employee, Date> p) {
				ObservableValue<Date> obs = new ReadOnlyObjectWrapper<>(p.getValue().getStartDate());
				return obs;
			}
		});

		colAdmin.setCellValueFactory(new Callback<CellDataFeatures<Employee, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Employee, String> p) {
				ObservableValue<String> obs = new ReadOnlyObjectWrapper<>(p.getValue().isAdmin() ? "Yes" : "No");
				return obs;
			}
		});

	}

	public void create() {

		Stage createStage = new Stage();
		StackPane stackPane = new StackPane();
		Scene scene = new Scene(stackPane);

		Label lblFName = new Label("First Name");
		Label lblLName = new Label("Last Name");
		Label lblDate = new Label("Start Date");
		Label lblAdmin = new Label("Admin");

		TextField firstName, lastName;
		DatePicker startDate;
		ComboBox<String> isAdmin;

		firstName = new TextField();
		firstName.setTooltip(new Tooltip("Enter First Name"));
		firstName.setMaxWidth(200);

		lastName = new TextField();
		lastName.setTooltip(new Tooltip("Enter Last Name"));
		lastName.setMaxWidth(200);

		startDate = new DatePicker();
		startDate.setTooltip(new Tooltip("Enter Start Date"));
		startDate.setMaxWidth(200);

		isAdmin = new ComboBox<>();
		isAdmin.getItems().addAll("Yes", "No");
		isAdmin.setTooltip(new Tooltip("Set Admin Permission"));
		isAdmin.setMaxWidth(200);

		Button savebtn = new Button("Save");
		savebtn.setTooltip(new Tooltip("Save"));

		savebtn.setOnAction(e -> {

			StringBuilder errorMessage = new StringBuilder();

			if (firstName.getText().length() == 0) {
				errorMessage.append("First name is empty.\n");
			}

			if (lastName.getText().length() == 0) {
				errorMessage.append("Last name is empty.\n");
			}
			if (startDate.getValue() == null) {
				errorMessage.append("Date is empty.\n");
			}
			if (errorMessage.length() != 0) {
				Alert alert = new Alert(AlertType.ERROR, errorMessage.toString());
				alert.setTitle("Missing Employee Information");
				alert.setHeaderText("Please fill in the empty fields.");
				alert.showAndWait();
			}

			else {

				// Convert DatePicker date to SQL date
				java.util.Date date = java.util.Date
						.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());

				// Set to non-admin by default
				byte admin = 0;
				// If admin field is "Yes" set variable to 1; else set to 0
				if (isAdmin.getValue() != null)
					admin = (byte) (isAdmin.getValue().equals("Yes") ? 1 : 0);

				// Execute prepared statement
				String query = "{call proc_InsertEmployee(?, ?, ?, ?, ?)}";
				try {
					CallableStatement cStmt = connection.prepareCall(query);
					cStmt.setString(1, firstName.getText());
					cStmt.setString(2, lastName.getText());
					cStmt.setDate(3, sqlDate);
					cStmt.setByte(4, admin);
					cStmt.registerOutParameter(5, java.sql.Types.TINYINT);
					cStmt.execute();
					int made = cStmt.getInt(5);

					if (made == 1) {
						Alert alert = new Alert(AlertType.INFORMATION, "Employee create successful!");
						alert.showAndWait();
					} else {
						Alert alert = new Alert(AlertType.WARNING, "Employee already exists!");
						alert.showAndWait();
					}

				} catch (SQLException e1) {
					Alert alert = new Alert(AlertType.ERROR, "Employee create failed!");
					e1.printStackTrace();
					alert.showAndWait();
				}

				sceneSwitcher.switchScene(btnCreate, "/views/AdminPage.fxml");

				((Node) (e.getSource())).getScene().getWindow().hide();
			}
		});

		VBox labels = new VBox(20);
		labels.getChildren().addAll(lblFName, lblLName, lblDate, lblAdmin);
		labels.setAlignment(Pos.BASELINE_RIGHT);
		labels.setPadding(new Insets(10));

		VBox fields = new VBox(10);
		fields.getChildren().addAll(firstName, lastName, startDate, isAdmin, savebtn);
		fields.setPadding(new Insets(10));

		HBox hbox = new HBox();
		hbox.getChildren().addAll(labels, fields);
		stackPane.getChildren().add(hbox);

		createStage.setTitle("New Employee");
		createStage.setScene(scene);
		createStage.show();

	}

	public List<Employee> read() {

		List<Employee> data = new ArrayList<>();

		String query = "select * from employees";
		try {
			Statement statement = connection.createStatement();
			ResultSet rset = statement.executeQuery(query);

			while (rset.next()) {
				Employee employee = new Employee(rset.getInt(1), rset.getString(2), rset.getString(3),
						rset.getString(5), rset.getDate(4), rset.getBoolean(6));
				data.add(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return data;
	}

	public void update(int id) {

		Employee employee = null;

		String query = "select * from employees where employeeID = " + id;
		try {
			Statement statement = connection.createStatement();
			ResultSet rset = statement.executeQuery(query);

			while (rset.next()) {
				employee = new Employee(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(5),
						rset.getDate(4), rset.getBoolean(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Stage updateStage = new Stage();
		StackPane stackPane = new StackPane();
		Scene scene = new Scene(stackPane);

		Label lblFName = new Label("First Name");
		Label lblLName = new Label("Last Name");
		Label lblDate = new Label("Start Date");
		Label lblAdmin = new Label("Admin");

		TextField firstName, lastName;
		DatePicker startDate;
		ComboBox<String> isAdmin;

		firstName = new TextField(employee.getFirstName());
		firstName.setTooltip(new Tooltip("Enter First Name"));
		firstName.setMaxWidth(200);

		lastName = new TextField(employee.getLastName());
		lastName.setTooltip(new Tooltip("Enter Last Name"));
		lastName.setMaxWidth(200);

		startDate = new DatePicker(employee.getStartDate().toLocalDate());
		startDate.setTooltip(new Tooltip("Enter Start Date"));
		startDate.setMaxWidth(200);

		String admin = employee.isAdmin() ? "Yes" : "No";
		isAdmin = new ComboBox<>();
		isAdmin.getItems().addAll("Yes", "No");
		isAdmin.setTooltip(new Tooltip("Set Admin Permission"));
		isAdmin.setValue(admin);
		isAdmin.setMaxWidth(200);

		Button savebtn = new Button("Save");
		savebtn.setTooltip(new Tooltip("Save"));

		savebtn.setOnAction(e -> {

			// Prepare query
			String updateQuery = "update Employees " + "set firstName = ?, lastName = ?, startDate = ?, isAdmin = ? "
					+ "where employeeID = " + id;

			// Convert DatePicker date to SQL date
			java.util.Date date = java.util.Date
					.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());

			// If admin field is "Yes" set variable to 1; else set to 0
			byte updateAdmin = (byte) (isAdmin.getValue().equals("Yes") ? 1 : 0);

			// Execute prepared statement
			try {
				PreparedStatement preparedStmt = connection.prepareStatement(updateQuery);
				preparedStmt.setString(1, firstName.getText());
				preparedStmt.setString(2, lastName.getText());
				preparedStmt.setDate(3, sqlDate);
				preparedStmt.setByte(4, updateAdmin);
				preparedStmt.execute();

				Alert alert = new Alert(AlertType.INFORMATION, "Employee update successful!");
				alert.showAndWait();
			} catch (SQLException e1) {
				Alert alert = new Alert(AlertType.ERROR, "Employee update failed!");
				alert.showAndWait();
			}

			sceneSwitcher.switchScene(btnUpdate, "/views/AdminPage.fxml");

			((Node) (e.getSource())).getScene().getWindow().hide();

		});

		Button resetbtn = new Button("Reset Password");
		resetbtn.setTooltip(new Tooltip("Reset Password"));

		resetbtn.setOnAction(e -> {

			String updateQuery = "update Employees " + "set password = null " + "where employeeID = " + id;

			try {
				Statement statement = connection.createStatement();
				statement.execute(updateQuery);

				Alert alert = new Alert(AlertType.INFORMATION, "Password reset successful!");
				alert.showAndWait();
			} catch (SQLException e1) {
				Alert alert = new Alert(AlertType.ERROR, "Password reset failed!");
				alert.showAndWait();
			}

			sceneSwitcher.switchScene(btnUpdate, "/views/AdminPage.fxml");

			((Node) (e.getSource())).getScene().getWindow().hide();
		});

		VBox labels = new VBox(20);
		labels.getChildren().addAll(lblFName, lblLName, lblDate, lblAdmin);
		labels.setAlignment(Pos.BASELINE_RIGHT);
		labels.setPadding(new Insets(10));

		VBox fields = new VBox(10);
		fields.getChildren().addAll(firstName, lastName, startDate, isAdmin, savebtn, resetbtn);
		fields.setPadding(new Insets(10));

		HBox hbox = new HBox();
		hbox.getChildren().addAll(labels, fields);
		stackPane.getChildren().add(hbox);

		updateStage.setTitle("Update Employee");
		updateStage.setScene(scene);
		updateStage.show();

	}

	public void delete(int id) {

		Employee employee = null;

		String query = "select * from employees where employeeID = " + id;
		try {
			Statement statement = connection.createStatement();
			ResultSet rset = statement.executeQuery(query);

			while (rset.next()) {
				employee = new Employee(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(5),
						rset.getDate(4), rset.getBoolean(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Deleting " + employee.getFirstName() + " " + employee.getLastName());
		alert.setHeaderText(
				"Are you sure you want to delete " + employee.getFirstName() + " " + employee.getLastName() + "?");
		alert.setContentText("This action can't be undone!");
		ButtonType yes = new ButtonType("Yes");
		ButtonType no = new ButtonType("No");
		alert.getButtonTypes().setAll(yes, no);
		Optional<ButtonType> result = alert.showAndWait();
		try {
			if (result.get() == yes) {

				String deleteQuery = "delete from Employees " + "where employeeID = " + id;

				try {
					Statement statement = connection.createStatement();
					statement.execute(deleteQuery);

					Alert alert1 = new Alert(AlertType.INFORMATION, "Employee delete successful!");
					alert1.showAndWait();
				} catch (SQLException e1) {
					Alert alert1 = new Alert(AlertType.ERROR, "Employee delete failed!");
					alert1.showAndWait();
				}
			}
		} catch (NoSuchElementException ex) {
			ex.printStackTrace();
		}

		sceneSwitcher.switchScene(btnDelete, "/views/AdminPage.fxml");

	}

}
