package controllers;

import java.io.IOException;
import java.sql.Statement;
import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class HomeController {
	
	BorderPane root = TireShop.getRoot();
	Statement statement = TireShop.getStatement();
	
	@FXML
	private Hyperlink linkSearch;
	
	@FXML
	private Hyperlink linkInvoice;
	
	@FXML
	private Hyperlink linkAppointment;
	
	@FXML
	private Hyperlink linkLogout;
	
	@FXML
	private void initialize() {
		
		linkSearch.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/Search.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		linkInvoice.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/Invoice.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		linkAppointment.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/Appointment.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		linkLogout.setOnAction(e -> {
			
		});
		
	}

}
