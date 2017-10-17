package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class LoginController {
	
	BorderPane root = TireShop.getRoot();
	Connection connection = TireShop.getConnection();
	
	// Access user throughout application
	public static ResultSet user;
	
	@FXML
	private TextField txtID;
	
	@FXML
	private Button btnEnter;
	
	@FXML
	private void initialize() {
		
		btnEnter.setOnAction(e -> {
			String id = txtID.getText();
			String query = "select * from Employee where employeeId = " + id;
			
			try {
				Statement statement = connection.createStatement();
				
				user = statement.executeQuery(query);
				
				if (user.next()) {
					if (user.getString(2) == null) {
						try {
							AnchorPane pane = FXMLLoader.load(getClass().getResource
							  ("/views/PasswordCreate.fxml"));
							root.setCenter(pane);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
					else {
						try {
							AnchorPane pane = FXMLLoader.load(getClass().getResource
							  ("/views/PasswordLogin.fxml"));
							root.setCenter(pane);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
				else {
					System.out.println("Invalid ID!");
				}
			}
			catch (SQLException ex) {
				System.out.println("Invalid ID!");
			}
		});
		
	}
	
	public static ResultSet getUser() {
		return user;
	}

}
