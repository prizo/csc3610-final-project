package controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class InvoiceController {
	
	BorderPane root = Main.getRoot();
	Connection con = Main.getConnection();
	
	@FXML
	private ListView<String> tireView;
	@FXML private ComboBox<String> cmbTireBrand;
	@FXML
	private ObservableList<String> tireNameList = FXCollections.observableArrayList();
	@FXML
	private ObservableList<String> tireBrandList = FXCollections.observableArrayList();
	@FXML
	private TextField tireBrand, tireName, rimDiameter, firstName, lastName, phoneNumber, tireQuantity, tirePrice,
			laborCost, em;
	@FXML
	private TextArea txtinvoice;
	@FXML
	private Button backButton, saveButton, clearButton;
	@FXML
	private RadioButton installTires;

	@FXML
	private void initialize() throws ClassNotFoundException, SQLException {
		
//		setTireNames();
		setTireBrands();
		
		tireView.setOnMouseClicked(e -> {

			try {
				String query = "select * from tires where name = '" + tireView.getSelectionModel().getSelectedItem() + "'";
				
				Statement statement = con.createStatement();
				// Queries the database for the selected item
				ResultSet results = statement.executeQuery(query);
				// Fills in the data fields with the queried items attributes
				while (results.next()) {
					tireName.setText(results.getString("name"));
					tireBrand.setText(results.getString("brand"));
					rimDiameter.setText(results.getString("rimdiameter"));
					tirePrice.setText("$" + results.getString("price"));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} 
		});

		clearButton.setOnAction((event) -> {
			ClearFields();

		});
		cmbTireBrand.setOnAction((event) ->{
			try {
				Statement statement = con.createStatement();
				String query = "select * from tires where brand = '" + cmbTireBrand.getSelectionModel().getSelectedItem() + "'";
				ResultSet results = statement.executeQuery(query);
				// Fills in the data fields with the queried items attributes
				tireNameList.removeAll(tireNameList);
				while (results.next()) {
					tireNameList.add(results.getString("name"));
				}
				tireView.setItems(tireNameList);
			} catch (SQLException  e1) {
				e1.printStackTrace();
			} 
		});
		
/*		allTiresButton.setOnAction((event) ->{
			try {
				setTireNames();
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});
*/	
		saveButton.setOnAction((event) -> {
			//String fname = firstName.getText().toString();// sets fxml text obkect to string from textfield
			//String lname = lastName.getText().toString();// sets fxml text obkect to string from textfield
			//String email = em.getText().toString();// sets fxml text obkect to string from textfield
			//String phone = phoneNumber.getText().toString();// sets fxml text obkect to string from textfield

			// print out info in console for testing
			//System.out.println("First Name: " + fname + "\n" + "Last Name: " + lname + "\n" + "Phone Number: " + phone
			//		+ "\n" + "Email: " + email + "\n");
			// gathers data entered from textfields -- puts in invoice text area;
			String invoice = 	("First Name: " + firstName.getText() + "\nLast Name: " + lastName.getText() + "\nPhone Number: " + 
								phoneNumber.getText() + "\nEmail: " + em.getText()+ "\nTire Name: " + tireName.getText() +
								"\nTire Brand: " + tireBrand.getText()  + "\nRim Diameter: " + rimDiameter.getText() +
								"\nQuantity: " + tireQuantity.getText() + "\nTire Price: " + tirePrice.getText() +
								"\nLabor Cost : $" + laborCost.getText());
			txtinvoice.setText(invoice);
			// Writes the string to a xls file
			try {

				FileWriter fw = new FileWriter("Customer_Info.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(invoice);
				bw.close();

			} catch (IOException e) {
				System.out.println("The file was not created");
			}
			ClearFields();
		});
		
		backButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Home.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
	}

/*	private void setTireNames() throws ClassNotFoundException, SQLException {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery("Select * from tires order by name");
		tireNameList.removeAll(tireNameList);
		// Adds every name from tires to the name list
		while (rs.next()) {
			tireNameList.add(rs.getString("name"));
		}
		// Loads the tire name list in to the tire view
		tireView.setItems(tireNameList);
		}
*/	
	private void setTireBrands() throws ClassNotFoundException, SQLException {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery("Select distinct brand from tires order by brand");
		tireBrandList.removeAll(tireBrandList);
		while(rs.next()) {
			tireBrandList.add(rs.getString("brand"));
		}
		// Loads the tire brand list in to the tire brand
		cmbTireBrand.setItems(tireBrandList);
	}
	private void ClearFields() {
		tireName.clear();
		tireBrand.clear();
		rimDiameter.clear();
		tireQuantity.clear();
		tirePrice.clear();
		laborCost.clear();
		firstName.clear();
		lastName.clear();
		phoneNumber.clear();
		em.clear();
		installTires.setSelected(false);
	}
}
