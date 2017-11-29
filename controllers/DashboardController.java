package controllers;

import helperclasses.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DashboardController {
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	@FXML
	private Button btnLogin, btnAdmin, btnReport, btnDocument;
	
	@FXML
	private void initialize() {
		
		btnLogin.setOnAction(e -> {
			sceneSwitcher.switchScene(btnLogin, "/views/Login.fxml");
		});
		
		btnAdmin.setOnAction(e -> {
			sceneSwitcher.switchScene(btnAdmin, "/views/AdminLogin.fxml");
		});
		
		btnReport.setOnAction(e -> {
			sceneSwitcher.switchScene(btnReport, "/reports/EmployeeReport.fxml");
		});
		
		btnDocument.setOnAction(e -> {
			sceneSwitcher.switchScene(btnDocument, "/views/Documentation.fxml");
		});
		
	}

}

