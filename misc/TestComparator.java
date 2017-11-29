package misc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import helperclasses.JDBCConnector;
import models.Employee;

public class TestComparator {

	public static void main(String[] args) {

		Connection connection = new JDBCConnector().getConnection();
		
		List<Employee> list = new ArrayList<>();
		
		String query = "select * from employees";
		try {
			Statement statement = connection.createStatement();
			ResultSet rset = statement.executeQuery(query);
			
			while (rset.next()) {
				Employee employee = new Employee
						(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(5),
								rset.getDate(4), rset.getBoolean(6));
				list.add(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("List from database\n------------------");
		for (Employee employee : list)
			System.out.println(employee.getFirstName() + "\t" + employee.getStartDate());
		
		System.out.println("\nList sorted using comparator\n----------------------------");
		Collections.sort(list, new DateComparator());
		Iterator<Employee> iterator = list.iterator();
		while (iterator.hasNext()) {
			Employee employee = iterator.next();
			System.out.println(employee.getFirstName() + "\t" + employee.getStartDate());
		}

	}

}
