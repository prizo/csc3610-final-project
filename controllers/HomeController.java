package controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class HomeController {
	
	BorderPane root = TireShop.getRoot();
	ResultSet user = LoginController.getUser();
	
	@FXML
	private Button btnSearch;
	
	@FXML
	private Button btnInvoice;
	
	@FXML
	private Button btnAppointment;
	
	@FXML
	private Button btnLogout;
	
	@FXML
	private Text txtInfo;
	
	@FXML
	private void initialize() {
		
		try {
			txtInfo.setText("Logged in as " + user.getString("firstName") + " " 
					+ user.getString("lastName") + " on " + new Date());
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		btnSearch.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Search.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		btnInvoice.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Invoice.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		btnAppointment.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Appointment.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		btnLogout.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Dashboard.fxml"));
				root.setCenter(pane);

				System.out.println("Employee " + user.getString("firstName") + " " + user.getString("lastName") +
						" logged out on " + new Date());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			user = null;
		});
		
	}

}
