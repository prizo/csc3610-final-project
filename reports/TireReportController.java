package reports;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import models.Tire;

public class TireReportController {

	BorderPane root = TireShop.getRoot();
	Connection con = TireShop.getConnection();

	@FXML
	private TextField sumField;
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
	private Button backButton, employeeButton, tireButton, customerButton, orderButton, invoiceButton;
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


		sumField.setText(Integer.toString(masterData.size()));
		
		backButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Dashboard.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		employeeButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/reports/EmployeeReport.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		tireButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/reports/TireReport.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		customerButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/reports/CustomerReport.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		orderButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/reports/OrderReport.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		invoiceButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/reports/InvoiceReport.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
	}
}
