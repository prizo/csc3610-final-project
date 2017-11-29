package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;

import helperclasses.JDBCConnector;
import helperclasses.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PasswordCreateController {

	ResultSet user = LoginController.getUser();
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	Connection connection = new JDBCConnector().getConnection();
	
	@FXML
	private PasswordField txtPassword;
	
	@FXML
	private Button btnCreate;
	
	@FXML
	private Button btnBack;
	
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
				
				sceneSwitcher.switchScene(btnCreate, "/views/Home.fxml");
				
				try {
					System.out.println("Employee " + user.getString("firstName") + " " + user.getString("lastName") +
							" logged in on " + new Date());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			else {
				Alert alert = new Alert(AlertType.ERROR, 	"Password must be between 6-20 characters, contain a number," +
															" and an uppercase letter");
				alert.showAndWait();
			}
			
		});
		
		btnBack.setOnAction(e -> {
			sceneSwitcher.switchScene(btnBack, "/views/Login.fxml");
		});
		
	}

}
