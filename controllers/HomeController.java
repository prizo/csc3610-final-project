package controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class HomeController {
	
	BorderPane root = TireShop.getRoot();
	ResultSet user = LoginController.getUser();
	
	@FXML
	private Hyperlink linkSearch;
	
	@FXML
	private Hyperlink linkInvoice;
	
	@FXML
	private Hyperlink linkAppointment;
	
	@FXML
	private Hyperlink linkLogout;
	
	@FXML
	private Text txtInfo;
	
	@FXML
	private void initialize() {
		
		try {
			txtInfo.setText("Logged in as " + user.getString("firstName") + " " + user.getString("lastName") + " on " + new Date());
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		linkSearch.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Search.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		linkInvoice.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Invoice.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		linkAppointment.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Appointment.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		linkLogout.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Login.fxml"));
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
