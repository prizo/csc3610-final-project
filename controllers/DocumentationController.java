package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class DocumentationController {
	
	BorderPane root = TireShop.getRoot();
	
	@FXML
	private Hyperlink linkGitHub;
	
	@FXML
	private Hyperlink linkSchema;
	
	@FXML
	private Hyperlink linkOct26;
	
	@FXML
	private Hyperlink linkNov09;
	
	@FXML
	private Button btnBack;
	
	@FXML
	private void initialize() {
		
		linkGitHub.setOnAction(e -> {
			linkGitHub = new Hyperlink("https://github.com/prizo/csc3610-final-project");
			TireShop.getHostService().showDocument(linkGitHub.getText());
		});
		
		linkSchema.setOnAction(e -> {
			
		});
		
		linkOct26.setOnAction(e -> {
			try {
				Desktop.getDesktop().open(new File("resources/Oct-26-2017.pdf"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		linkNov09.setOnAction(e -> {
			try {
				Desktop.getDesktop().open(new File("resources/Nov-09-2017.pdf"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		btnBack.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Dashboard.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
	}

}
