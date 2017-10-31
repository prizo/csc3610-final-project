package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;
import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PasswordCreateController {
	
	BorderPane root = TireShop.getRoot();
	Connection connection = TireShop.getConnection();
	ResultSet user = LoginController.getUser();
	
	@FXML
	private PasswordField txtPassword;
	
	@FXML
	private Button btnCreate;
	
	@FXML
	private void initialize() {
		txtPassword.setOnKeyPressed(e ->{

            if (e.getCode() == (KeyCode.ENTER)) {
                btnCreate.fire();
            }
        
    });
		btnCreate.setOnAction(e -> {
			String id = "";
			try {
				id = user.getString("employeeID");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			String inputPassword = txtPassword.getText();
			Pattern passwordCheck = Pattern.compile	("((?=.*\\d)" + //Checks for a number
													"(?=.*[a-z])" + //Checks for a lowercase letter
													"(?=.*[A-Z])" + //Checks for a uppercase letter
													".{6,20})");    //Checks that the length is between 6-20
			Matcher match = passwordCheck.matcher(inputPassword);
			if(match.matches()) {
				// Hash input password
				String md5Hex = DigestUtils.md5Hex(inputPassword);					
				
				// Update password in database
				try {
					Statement statement = connection.createStatement();
					String insert = "update employees set password = '" + md5Hex + "' where employeeID = " + id;
					statement.executeUpdate(insert);
				
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				
				try {
					AnchorPane pane = FXMLLoader.load(getClass().getResource
					  ("/views/Home.fxml"));
					root.setCenter(pane);
					
					System.out.println("Employee " + user.getString("employeeID") +
							" logged in on " + new Date());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			else {
				Alert alert = new Alert(AlertType.ERROR, 	"Password must be between 6-20 characters, contain a number," +
															" and an uppercase letter");
				alert.showAndWait();
			}
			
		});
		
	}

}
