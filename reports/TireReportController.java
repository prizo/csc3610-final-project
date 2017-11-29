package reports;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import helperclasses.JDBCConnector;
import helperclasses.SceneSwitcher;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import models.Tire;

public class TireReportController {
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	Connection con = new JDBCConnector().getConnection();

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
			sceneSwitcher.switchScene(backButton, "/views/Dashboard.fxml");
		});
		
		employeeButton.setOnAction(e -> {
			sceneSwitcher.switchScene(employeeButton, "/reports/EmployeeReport.fxml");
		});
		
		tireButton.setOnAction(e -> {
			sceneSwitcher.switchScene(tireButton, "/reports/TireReport.fxml");
		});
		
		customerButton.setOnAction(e -> {
			sceneSwitcher.switchScene(customerButton, "/reports/CustomerReport.fxml");
		});
		
		orderButton.setOnAction(e -> {
			sceneSwitcher.switchScene(orderButton, "/reports/OrderReport.fxml");
		});
		
		invoiceButton.setOnAction(e -> {
			sceneSwitcher.switchScene(invoiceButton, "/reports/InvoiceReport.fxml");
		});
	}
}
