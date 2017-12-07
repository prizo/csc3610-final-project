package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import helperclasses.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class HomeController {
	
	ResultSet user = LoginController.getUser();
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	@FXML
	private Button btnSearch;
	
	@FXML
	private Button btnInvoice;
	
	@FXML
	private Button btnLogout;
	
	@FXML
	private Text txtInfo;
	
	@FXML
	private void initialize() {
		
		try {
			txtInfo.setText("Logged in as " + user.getString("firstName") + " " 
					+ user.getString("lastName") + " on " + new Date());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		btnSearch.setOnAction(e -> {
			sceneSwitcher.switchScene(btnSearch, "/views/Search.fxml");
		});
		
		btnInvoice.setOnAction(e -> {
			sceneSwitcher.switchScene(btnInvoice, "/views/Invoice.fxml");
		});
		
		btnLogout.setOnAction(e -> {
			sceneSwitcher.switchScene(btnLogout, "/views/Dashboard.fxml");

			try {
				System.out.println("Employee " + user.getString("firstName") + " " 
						+ user.getString("lastName") + " logged out on " + new Date());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			user = null;
		});
		
	}

}
