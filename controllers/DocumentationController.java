package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import helperclasses.HostServicesProvider;
import helperclasses.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

public class DocumentationController {
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	@FXML
	private Hyperlink githubLink;
	
	@FXML
	private Hyperlink pdfOct26;
	
	@FXML
	private Hyperlink pdfNov09;
	
	@FXML
	private Hyperlink pdfNov30;
	
	@FXML
	private Button btnBack;
	
	@FXML
	private void initialize() {
		
		githubLink.setOnAction(e -> {
			HostServicesProvider.INSTANCE.getHostServices().showDocument(githubLink.getText());
		});
		
		pdfOct26.setOnAction(e -> {
			try {
				Desktop.getDesktop().open(new File("resources/Oct-26-2017.pdf"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		pdfNov09.setOnAction(e -> {
			try {
				Desktop.getDesktop().open(new File("resources/Nov-09-2017.pdf"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		pdfNov30.setOnAction(e -> {
			
		});
		
		btnBack.setOnAction(e -> {
			sceneSwitcher.switchScene(btnBack, "/views/Dashboard.fxml");
		});
		
	}

}
