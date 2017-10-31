package reports;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
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
import models.Order;

public class OrderReportController {

	BorderPane root = TireShop.getRoot();
	Connection con = TireShop.getConnection();

	@FXML
	private TextField sumField;
	@FXML
	private TableView<Order> orderTable;
	@FXML
	private TableColumn<Order, Integer> orderIDColumn;
	@FXML
	private TableColumn<Order, Integer> tireIDColumn;
	@FXML
	private TableColumn<Order, Integer> quantityColumn;
	@FXML
	private TableColumn<Order, Date> orderDateColumn;
	@FXML
	private TableColumn<Order, Double> laborCostColumn;
	@FXML
	private ObservableList<Order> masterData = FXCollections.observableArrayList();
	@FXML
	private Button backButton, employeeButton, tireButton, customerButton, orderButton, invoiceButton;
	@FXML
	private void initialize() {

		String query = "Select * from orders";
		ResultSet rs;
		try {
			rs = con.createStatement().executeQuery(query);
			while (rs.next()) {
				//Creates an order using data from the database then adds it to masterData
				
				Order test = new Order(rs.getInt("orderID"), rs.getDate("orderDate"), rs.getInt("quantity"), 
							rs.getDouble("laborCost"), rs.getInt("tireID"));
				masterData.add(test);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Sets the list of orders in to the table
		orderTable.setItems(masterData);
		
		//Assigns the orders values in to the columns
		orderIDColumn.setCellValueFactory(new Callback<CellDataFeatures<Order, Integer>, ObservableValue<Integer>>(){
			public ObservableValue<Integer> call(CellDataFeatures<Order, Integer> p) {
				ObservableValue<Integer> obs = new ReadOnlyObjectWrapper<>(p.getValue().getOrderID());
				return obs;
			}
		});
		
		tireIDColumn.setCellValueFactory(new Callback<CellDataFeatures<Order, Integer>, ObservableValue<Integer>>(){
			public ObservableValue<Integer> call(CellDataFeatures<Order, Integer> p) {
				ObservableValue<Integer> obs = new ReadOnlyObjectWrapper<>(p.getValue().getTireID());
				return obs;
			}
		});
		
		quantityColumn.setCellValueFactory(new Callback<CellDataFeatures<Order, Integer>, ObservableValue<Integer>>(){
			public ObservableValue<Integer> call(CellDataFeatures<Order, Integer> p) {
				ObservableValue<Integer> obs = new ReadOnlyObjectWrapper<>(p.getValue().getQuantity());
				return obs;
			}
		});
		
		orderDateColumn.setCellValueFactory(new Callback<CellDataFeatures<Order, Date>, ObservableValue<Date>>(){
			public ObservableValue<Date> call(CellDataFeatures<Order, Date> p) {
				ObservableValue<Date> obs = new ReadOnlyObjectWrapper<>(p.getValue().getOrderDate());
				return obs;
			}
		});
		
		laborCostColumn.setCellValueFactory(new Callback<CellDataFeatures<Order, Double>, ObservableValue<Double>>(){
			public ObservableValue<Double> call(CellDataFeatures<Order, Double> p) {
				ObservableValue<Double> obs = new ReadOnlyObjectWrapper<>(p.getValue().getLaborCost());
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
