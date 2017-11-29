package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;

import helperclasses.JDBCConnector;
import helperclasses.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;

public class PasswordLoginController {

	ResultSet user = LoginController.getUser();
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	Connection connection = new JDBCConnector().getConnection();
	
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
				sceneSwitcher.switchScene(btnLogin, "/views/Home.fxml");
				
				try {
					System.out.println("Employee " + user.getString("firstName") + " " + user.getString("lastName") +
							" logged in on " + new Date());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			else {
				txtPassword.clear();
				Alert alert = new Alert(AlertType.ERROR, "Password incorrect!");
				alert.showAndWait();
			}	
		});
		
		btnBack.setOnAction(e -> {
			sceneSwitcher.switchScene(btnBack, "/views/Login.fxml");
		});
		
	}

}
