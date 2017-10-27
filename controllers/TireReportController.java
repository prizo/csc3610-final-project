package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import application.TireShop;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import models.Employee;

public class TireReportController {

BorderPane root = TireShop.getRoot();
Connection con = TireShop.getConnection();
	
	@FXML
	private TableView<ObservableList<String>> table;
	
	@FXML 
	private Button backButton, employeeButton;
	
	@FXML
	private ObservableList<ObservableList<String>> data;
	
	@FXML
	private void initialize() {
		
		backButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Home.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		employeeButton.setOnAction(e -> {


			try {
				String query = "Select * from employees";
				ResultSet rs = con.createStatement().executeQuery(query);
				
				for(int i = 0; i<rs.getMetaData().getColumnCount(); i++) {
					final int j = i;
					TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i+1));					
//					col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){   
//						public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param){
//							return new SimpleStringProperty(param.getValue().get(j).toString());
//						}
//					});     
					table.getColumns().addAll(col);
					System.out.println("Column " + i);
				}
				
				while(rs.next()) {
				ObservableList<String> row = FXCollections.observableArrayList();
					for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
						row.add(rs.getString(i));
					}
					data.add(row);
				}
				
				table.setItems(data);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		
	}
}
