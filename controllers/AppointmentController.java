package controllers;

import helperclasses.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AppointmentController {
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	@FXML
	private Button btnBack;
	
	@FXML
	private void initialize() {
		
		btnBack.setOnAction(e -> {
			sceneSwitcher.switchScene(btnBack, "/views/Home.fxml");
		});
		
	}

}
