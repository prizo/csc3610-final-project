package controllers;

import java.sql.Statement;
import application.TireShop;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;

public class PasswordLoginController {
	
	BorderPane root = TireShop.getRoot();
	Statement statement = TireShop.getStatement();
	
	@FXML
	private PasswordField pwdPassword;
	
	@FXML
	private Button btnLogin;
	
	@FXML
	private void initialize() {
		
		btnLogin.setOnAction(e -> {
			
		});
		
	}

}
