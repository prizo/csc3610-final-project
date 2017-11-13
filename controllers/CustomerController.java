package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class CustomerController {

	BorderPane root = TireShop.getRoot();
	Connection con = TireShop.getConnection();
	ResultSet user = LoginController.getUser();

	@FXML
	private TextField firstName, lastName, phoneNumber, em;

	@FXML
	private Button backButton, customerButton;

	@FXML
	private void initialize() throws ClassNotFoundException, SQLException {

		backButton.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource("/views/Invoice.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});

		customerButton.setOnAction(e -> {

			StringBuilder errorMessage = new StringBuilder();
			/*
			 * Pattern numberCheck = Pattern.compile ("((?=.*\\d))"); Pattern emailCheck =
			 * Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}"); Matcher
			 * firstNameMatch = numberCheck.matcher(firstName.getText()); Matcher
			 * lastNameMatch = numberCheck.matcher(lastName.getText()); Matcher
			 * phoneNumberMatch = numberCheck.matcher(phoneNumber.getText()); Matcher
			 * emailMatch = emailCheck.matcher(em.getText());
			 * 
			 * if (firstNameMatch.matches() || firstName.getText().length() == 0) {
			 * errorMessage.append("First name can't contain a number.\n"); }
			 * 
			 * if (lastNameMatch.matches() || lastName.getText().length() == 0) {
			 * errorMessage.append("Last name can't contain a number.\n"); } if
			 * (!phoneNumberMatch.matches() || phoneNumber.getText().length() == 0) {
			 * errorMessage.append("Phone number has to have numbers.\n"); } if
			 * (!emailMatch.matches() || em.getText().length() == 0) {
			 * errorMessage.append("Email is not in a valid format.\n"); }
			 */
			if (errorMessage.length() != 0) {
				Alert alert = new Alert(AlertType.ERROR, errorMessage.toString());
				alert.showAndWait();
			}

			else {
				try {
					String query = " insert into customers (firstName, lastName, phoneNumber, email)"
							+ " values (?, ?, ?, ?)";

					PreparedStatement preparedStmt = con.prepareStatement(query);
					preparedStmt.setString(1, firstName.getText());
					preparedStmt.setString(2, lastName.getText());
					preparedStmt.setString(3, phoneNumber.getText());
					preparedStmt.setString(4, em.getText());

					preparedStmt.execute();

				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});

	}
}
