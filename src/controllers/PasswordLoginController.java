package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;
import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
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
	private void initialize() {
		
		btnLogin.setOnAction(e -> {
			// Retrieve user password from database
			String userPassword = null;
			try {
				userPassword = user.getString(2);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			String inputPassword = txtPassword.getText();
			// Hash input password
			String md5Hex = DigestUtils.md5Hex(inputPassword);
			
			if (userPassword.equals(md5Hex)) {
				try {
					AnchorPane pane = FXMLLoader.load(getClass().getResource
					  ("/views/Home.fxml"));
					root.setCenter(pane);

					System.out.println("Employee " + user.getString(1) +
							" logged in on " + new Date());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			else {
				System.out.println("Password incorrect!");
			}	
		});
		
	}

}
