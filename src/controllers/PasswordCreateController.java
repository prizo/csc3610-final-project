package controllers;

import java.sql.Statement;
import application.TireShop;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;

public class PasswordCreateController {
	
	BorderPane root = TireShop.getRoot();
	Statement statement = TireShop.getStatement();
	
	@FXML
	private PasswordField pwdPassword;
	
	@FXML
	private Button btnCreate;
	
	@FXML
	private void initialize() {
		
		btnCreate.setOnAction(e -> {
			
		});
		
	}

}
