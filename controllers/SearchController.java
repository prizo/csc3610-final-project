package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.TireShop;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import models.Tire;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class SearchController {

	BorderPane root = TireShop.getRoot();
	Connection con = TireShop.getConnection();
	static Tire searchTire = new Tire();

	@FXML
	private TextField filterField;
	@FXML
	private TableView<Tire> tireTable;
	@FXML
	private TableColumn<Tire, Integer> tireIDColumn;
	@FXML
	private TableColumn<Tire, String> nameColumn;
	@FXML
	private TableColumn<Tire, String> brandColumn;
	@FXML
	private TableColumn<Tire, Integer> rimDiameterColumn;
	@FXML
	private TableColumn<Tire, Double> priceColumn;
	@FXML
	private ObservableList<Tire> masterData = FXCollections.observableArrayList();
	@FXML
	private Button backButton, addButton;

	@FXML
	private void initialize() {

		String query = "Select * from tires";
		ResultSet rs;
		try {
			rs = con.createStatement().executeQuery(query);
			while (rs.next()) {
				
				Tire test= new Tire(rs.getInt("tireID"), rs.getString("name"),
						rs.getDouble("price"), rs.getString("brand"), rs.getInt("rimDiameter"));
				masterData.add(test);
			}
			System.out.println(masterData.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tireTable.setItems(masterData);
		tireIDColumn.setCellValueFactory(new Callback<CellDataFeatures<Tire, Integer>, ObservableValue<Integer>>(){
			public ObservableValue<Integer> call(CellDataFeatures<Tire, Integer> p) {
				ObservableValue<Integer> obs = new ReadOnlyObjectWrapper<>(p.getValue().getTireID());
				return obs;
			}
		});
		
		nameColumn.setCellValueFactory(new Callback<CellDataFeatures<Tire, String>, ObservableValue<String>>(){
			public ObservableValue<String> call(CellDataFeatures<Tire, String> p) {
				ObservableValue<String> obs = new ReadOnlyObjectWrapper<>(p.getValue().getName());
				return obs;
			}
		});
		
		brandColumn.setCellValueFactory(new Callback<CellDataFeatures<Tire, String>, ObservableValue<String>>(){
			public ObservableValue<String> call(CellDataFeatures<Tire, String> p) {
				ObservableValue<String> obs = new ReadOnlyObjectWrapper<>(p.getValue().getBrand());
				return obs;
			}
		});
		
		rimDiameterColumn.setCellValueFactory(new Callback<CellDataFeatures<Tire, Integer>, ObservableValue<Integer>>(){
			public ObservableValue<Integer> call(CellDataFeatures<Tire, Integer> p) {
				ObservableValue<Integer> obs = new ReadOnlyObjectWrapper<>(p.getValue().getRimDiameter());
				return obs;
			}
		});
		
		priceColumn.setCellValueFactory(new Callback<CellDataFeatures<Tire, Double>, ObservableValue<Double>>(){
			public ObservableValue<Double> call(CellDataFeatures<Tire, Double> p) {
				ObservableValue<Double> obs = new ReadOnlyObjectWrapper<>(p.getValue().getPrice());
				return obs;
			}
		});

		FilteredList<Tire> filteredData = new FilteredList<>(masterData, p -> true);

		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(tire -> {
				// If filter text is empty, display all Tires.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare tire name and tire brand of every Tire with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (tire.getName().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches tire name
				} else if (tire.getBrand().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches brand
				}
				return false; // Does not match.
			});
		});

		SortedList<Tire> sortedData = new SortedList<>(filteredData);

		sortedData.comparatorProperty().bind(tireTable.comparatorProperty());

		tireTable.setItems(sortedData);
		
		backButton.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Home.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		addButton.setOnAction(e -> {
			
			 searchTire = new Tire(tireTable.getSelectionModel().getSelectedItem());
			
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Invoice.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});	
		
	}
	
	public static Tire getTire() {
		return searchTire;
	}
}
