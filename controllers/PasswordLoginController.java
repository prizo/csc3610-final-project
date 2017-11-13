package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;
import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;

public class PasswordLoginController {
	
	BorderPane root = TireShop.getRoot();
	Connection connection = TireShop.getConnection();
	ResultSet user = LoginController.getUser();
	
	@FXML
	private PasswordField txtPassword;
	
	@FXML
	private Button btnLogin;
	
	@FXML
	private Button btnBack;
	
	@FXML
	private void initialize() {
		
		txtPassword.setOnKeyPressed(e ->{

            if (e.getCode() == (KeyCode.ENTER)) {
                btnLogin.fire();
            }
        
		});
		
		btnLogin.setOnAction(e -> {
			// Retrieve user password from database
			String userPassword = null;
			try {
				userPassword = user.getString("password");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			String inputPassword = txtPassword.getText();
			// Hash input password
			String md5Hex = DigestUtils.md5Hex(inputPassword);
			
			if (userPassword.equals(md5Hex)) {
				try {
					StackPane pane = FXMLLoader.load(getClass().getResource
					  ("/views/Home.fxml"));
					root.setCenter(pane);

					System.out.println("Employee " + user.getString("firstName") + " " + user.getString("lastName") +
							" logged in on " + new Date());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			else {
				txtPassword.clear();
				Alert alert = new Alert(AlertType.ERROR, "Password incorrect!");
				alert.showAndWait();
			}	
		});
		
		btnBack.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Login.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
	}

}
