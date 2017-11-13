package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Employee;

public class AdminPageController {
	
	BorderPane root = TireShop.getRoot();
	Connection connection = TireShop.getConnection();
	ResultSet user = AdminLoginController.getUser();
	
	@FXML
	private VBox vbox;
	
	@FXML
	private void initialize() {
		startApp();
	}
	
	public void startApp() {

		Button create = new Button();
		create.setText("Create New Employee");
		
		create.setOnAction(e -> {
			create();
		});
		
		Button back = new Button();
		back.setText("Back");
		
		back.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Dashboard.fxml"));
				root.setCenter(pane);
				
				System.out.println("Admin " + user.getString("firstName") + " " + user.getString("lastName") +
						" logged out on " + new Date());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		List<Employee> data = read();
		vbox.setPadding(new Insets(10));
		Label idHeader = new Label("ID");
		Label firstNameHeader = new Label("First Name");
		Label lastNameHeader = new Label("Last Name");
		Label startDateHeader = new Label("Start Date");
		Label isAdminHeader = new Label("Admin");
		
		idHeader.setFont(new Font("Arial", 15));
		firstNameHeader.setFont(new Font("Arial", 15));
		lastNameHeader.setFont(new Font("Arial", 15));
		startDateHeader.setFont(new Font("Arial", 15));
		isAdminHeader.setFont(new Font("Arial", 15));
		
		idHeader.setStyle("-fx-text-fill: white;");
		firstNameHeader.setStyle("-fx-text-fill: white;");
		lastNameHeader.setStyle("-fx-text-fill: white;");
		startDateHeader.setStyle("-fx-text-fill: white;");
		isAdminHeader.setStyle("-fx-text-fill: white;");
		
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		
		gridPane.add(idHeader, 0, 0);
		gridPane.add(firstNameHeader, 1, 0);
		gridPane.add(lastNameHeader, 2, 0);
		gridPane.add(startDateHeader, 3, 0);
		gridPane.add(isAdminHeader, 4, 0);
		
		Separator separator = new Separator();
		separator.setOrientation(Orientation.HORIZONTAL);
		
		gridPane.add(separator, 0, 1, 7, 1);
		
		int i = 2;
		
		for (Employee employee : data) {
			
			Label id = new Label(Integer.toString(employee.getEmployeeID()));
			Label firstName = new Label(employee.getFirstName());
			Label lastName = new Label(employee.getLastName());
			Label startDate = new Label(employee.getStartDate().toString());
			String admin = employee.isAdmin() ? "Yes": "No";
			Label isAdmin = new Label(admin);
			
			id.setStyle("-fx-text-fill: white;");
			firstName.setStyle("-fx-text-fill: white;");
			lastName.setStyle("-fx-text-fill: white;");
			startDate.setStyle("-fx-text-fill: white;");
			isAdmin.setStyle("-fx-text-fill: white;");
			
			Button edit = new Button("Edit");
			edit.setOnAction(e -> {
				update(employee.getEmployeeID());
			});
			
			Button delete = new Button("Delete");
			delete.setOnAction(e -> {
				delete(employee.getEmployeeID());
			});
			
			gridPane.add(id, 0, i, 1, 1);
			gridPane.add(firstName, 1, i, 1, 1);
			gridPane.add(lastName, 2, i, 1, 1);
			gridPane.add(startDate, 3, i, 1, 1);
			gridPane.add(isAdmin, 4, i, 1, 1);
			gridPane.add(edit, 5, i, 1, 1);
			gridPane.add(delete, 6, i, 1, 1);
			i++;
			
		}
		
		vbox.getChildren().addAll(create, gridPane, back);
		
	}

	public void create() {

		Stage createStage = new Stage();
		StackPane stackPane = new StackPane();
		Scene scene = new Scene(stackPane);
		
		TextField firstName, lastName, startDate, isAdmin;
		
		firstName = new TextField();
		firstName.setTooltip(new Tooltip("Enter First Name"));
		firstName.setPromptText("First Name");
		firstName.setMaxWidth(200);
		
		lastName = new TextField();
		lastName.setTooltip(new Tooltip("Enter Last Name"));
		lastName.setPromptText("Last Name");
		lastName.setMaxWidth(200);
		
		startDate = new TextField();
		startDate.setTooltip(new Tooltip("Enter Start Date"));
		startDate.setPromptText("Start Date");
		startDate.setMaxWidth(200);
		
		isAdmin = new TextField();
		isAdmin.setTooltip(new Tooltip("Enter Admin Permission"));
		isAdmin.setPromptText("Is Admin? (Yes or No)");
		isAdmin.setMaxWidth(200);
		
		Button savebtn = new Button("Save");
		savebtn.setTooltip(new Tooltip("Save"));
		
		savebtn.setOnAction(e -> {
			
			// Prepare query
			String query = "insert into Employees (firstName, lastName, startDate, password, isAdmin) "
					+ "values(?, ?, ?, ?, ?)";
			
			// If admin field is "Yes" or "yes" set variable to 1; else set to 0
			byte admin;
			if(isAdmin.getText().equals("Yes") || isAdmin.getText().equals("yes"))
				admin = 1;
			else
				admin = 0;
			
			// Execute prepared statement
			try {
				PreparedStatement preparedStmt = connection.prepareStatement(query);
				preparedStmt.setString(1, firstName.getText());
				preparedStmt.setString(2, lastName.getText());
				preparedStmt.setString(3, startDate.getText());
				preparedStmt.setString(4, null);
				preparedStmt.setByte(5, admin);
				preparedStmt.execute();
				
				Alert alert = new Alert(AlertType.INFORMATION, "Adding employee was successful!");
				alert.showAndWait();
			} catch (SQLException e1) {
				Alert alert = new Alert(AlertType.ERROR, "Adding employee failed!");
				alert.showAndWait();
			}
			
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/AdminPage.fxml"));
				root.setCenter(pane);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			((Node)(e.getSource())).getScene().getWindow().hide();
			
		});
		
		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(firstName, lastName, startDate, isAdmin, savebtn);
		vbox.setPadding(new Insets(10));
		stackPane.getChildren().add(vbox);
		
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
				Employee employee = new Employee
						(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(5),
								rset.getDate(4), rset.getBoolean(6));
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
				employee = new Employee
						(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(5),
								rset.getDate(4), rset.getBoolean(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Stage updateStage = new Stage();
		StackPane stackPane = new StackPane();
		Scene scene = new Scene(stackPane);
		
		TextField firstName, lastName, startDate, isAdmin;
		
		firstName = new TextField(employee.getFirstName());
		firstName.setTooltip(new Tooltip("Enter First Name"));
		firstName.setPromptText("First Name");
		firstName.setMaxWidth(200);
		
		lastName = new TextField(employee.getLastName());
		lastName.setTooltip(new Tooltip("Enter Last Name"));
		lastName.setPromptText("Last Name");
		lastName.setMaxWidth(200);
		
		startDate = new TextField(employee.getStartDate().toString());
		startDate.setTooltip(new Tooltip("Enter Start Date"));
		startDate.setPromptText("Start Date");
		startDate.setMaxWidth(200);
		
		String admin = employee.isAdmin() ? "Yes": "No";
		isAdmin = new TextField(admin);
		isAdmin.setTooltip(new Tooltip("Enter Admin Permission"));
		isAdmin.setPromptText("Is Admin? (Yes or No)");
		isAdmin.setMaxWidth(200);
		
		Button savebtn = new Button("Save");
		savebtn.setTooltip(new Tooltip("Save"));
		
		savebtn.setOnAction(e -> {
			
			// Prepare query
			String updateQuery = "update Employees "
					+ "set firstName = ?, lastName = ?, startDate = ?, isAdmin = ? "
					+ "where employeeID = " + id;
			
			// If admin field is "Yes" or "yes" set variable to 1; else set to 0
			byte updateAdmin;
			if(isAdmin.getText().equals("Yes") || isAdmin.getText().equals("yes"))
				updateAdmin = 1;
			else
				updateAdmin = 0;
			
			// Execute prepared statement
			try {
				PreparedStatement preparedStmt = connection.prepareStatement(updateQuery);
				preparedStmt.setString(1, firstName.getText());
				preparedStmt.setString(2, lastName.getText());
				preparedStmt.setString(3, startDate.getText());
				preparedStmt.setByte(4, updateAdmin);
				preparedStmt.execute();
				
				Alert alert = new Alert(AlertType.INFORMATION, "Updating employee was successful!");
				alert.showAndWait();
			} catch (SQLException e1) {
				Alert alert = new Alert(AlertType.ERROR, "Updating employee failed!");
				alert.showAndWait();
			}
			
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/AdminPage.fxml"));
				root.setCenter(pane);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			((Node)(e.getSource())).getScene().getWindow().hide();
			
		});
		
		Button resetbtn = new Button("Reset Password");
		resetbtn.setTooltip(new Tooltip("Reset Password"));
		
		resetbtn.setOnAction(e -> {

			String updateQuery = "update Employees "
					+ "set password = null "
					+ "where employeeID = " + id;
			
			try {
				Statement statement = connection.createStatement();
				statement.execute(updateQuery);
				
				Alert alert = new Alert(AlertType.INFORMATION, "Resetting password was successful!");
				alert.showAndWait();
			} catch (SQLException e1) {
				Alert alert = new Alert(AlertType.ERROR, "Resetting password failed!");
				alert.showAndWait();
			}
			
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/AdminPage.fxml"));
				root.setCenter(pane);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			((Node)(e.getSource())).getScene().getWindow().hide();
		});
		
		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(firstName, lastName, startDate, isAdmin, savebtn, resetbtn);
		vbox.setPadding(new Insets(10));
		stackPane.getChildren().add(vbox);
		
		updateStage.setTitle("Edit Employee");
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
				employee = new Employee
						(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(5),
								rset.getDate(4), rset.getBoolean(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Deleting " + employee.getFirstName() + " " + employee.getLastName());
		alert.setHeaderText("Are you sure you want to delete " + employee.getFirstName() + " "
				+ employee.getLastName() + "?");
		alert.setContentText("This action can't be undone!");
		Optional<ButtonType> result = alert.showAndWait();
		
		try {
			if (result.get() == ButtonType.OK) {
				
				String deleteQuery = "delete from Employees "
						+ "where employeeID = " + id;
				
				try {
					Statement statement = connection.createStatement();
					statement.execute(deleteQuery);
					
					Alert alert1 = new Alert(AlertType.INFORMATION, "Deleting employee was successful!");
					alert1.showAndWait();
				} catch (SQLException e1) {
					Alert alert1 = new Alert(AlertType.ERROR, "Deleting employee failed!");
					alert1.showAndWait();
				}
				
				try {
					StackPane pane = FXMLLoader.load(getClass().getResource
					  ("/views/AdminPage.fxml"));
					root.setCenter(pane);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
			}
		} catch (NoSuchElementException ex) {}
		
	}

}
