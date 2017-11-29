package reports;

import java.sql.Connection;
import java.sql.Date;
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
import models.Invoice;

public class InvoiceReportController {
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	Connection con = new JDBCConnector().getConnection();

	@FXML
	private TextField sumField;
	@FXML
	private TableView<Invoice> invoiceTable;
	@FXML
	private TableColumn<Invoice, Integer> invoiceIDColumn;
	@FXML
	private TableColumn<Invoice, Integer> employeeIDColumn;
	@FXML
	private TableColumn<Invoice, Integer> customerIDColumn;
	@FXML
	private TableColumn<Invoice, Integer> orderIDColumn;
	@FXML
	private TableColumn<Invoice, Date> invoiceDateColumn;
	@FXML
	private ObservableList<Invoice> masterData = FXCollections.observableArrayList();
	@FXML
	private Button backButton, employeeButton, tireButton, customerButton, orderButton, invoiceButton;
	@FXML
	private void initialize() {

		String query = "Select * from invoices";
		ResultSet rs;
		try {
			rs = con.createStatement().executeQuery(query);
			while (rs.next()) {
				//Creates an invoice using data from the database then adds it to masterData
				Invoice test = new 	Invoice(rs.getInt("invoiceID"), rs.getDate("invoiceDate"), rs.getInt("customerID"),
						 			rs.getInt("employeeID"), rs.getInt("orderID")); 
				masterData.add(test);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Sets the list of invoices in to the table
		invoiceTable.setItems(masterData);
		
		//Assigns the invoices values in to the columns
		invoiceIDColumn.setCellValueFactory(new Callback<CellDataFeatures<Invoice, Integer>, ObservableValue<Integer>>(){
			public ObservableValue<Integer> call(CellDataFeatures<Invoice, Integer> p) {
				ObservableValue<Integer> obs = new ReadOnlyObjectWrapper<>(p.getValue().getInvoiceID());
				return obs;
			}
		});
		
		customerIDColumn.setCellValueFactory(new Callback<CellDataFeatures<Invoice, Integer>, ObservableValue<Integer>>(){
			public ObservableValue<Integer> call(CellDataFeatures<Invoice, Integer> p) {
				ObservableValue<Integer> obs = new ReadOnlyObjectWrapper<>(p.getValue().getCustomerID());
				return obs;
			}
		});
		
		orderIDColumn.setCellValueFactory(new Callback<CellDataFeatures<Invoice, Integer>, ObservableValue<Integer>>(){
			public ObservableValue<Integer> call(CellDataFeatures<Invoice, Integer> p) {
				ObservableValue<Integer> obs = new ReadOnlyObjectWrapper<>(p.getValue().getOrderID());
				return obs;
			}
		});
		
		employeeIDColumn.setCellValueFactory(new Callback<CellDataFeatures<Invoice, Integer>, ObservableValue<Integer>>(){
			public ObservableValue<Integer> call(CellDataFeatures<Invoice, Integer> p) {
				ObservableValue<Integer> obs = new ReadOnlyObjectWrapper<>(p.getValue().getEmployeeID());
				return obs;
			}
		});
		
		invoiceDateColumn.setCellValueFactory(new Callback<CellDataFeatures<Invoice, Date>, ObservableValue<Date>>(){
			public ObservableValue<Date> call(CellDataFeatures<Invoice, Date> p) {
				ObservableValue<Date> obs = new ReadOnlyObjectWrapper<>(p.getValue().getInvoiceDate());
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
