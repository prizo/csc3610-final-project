package misc;

import java.util.Comparator;
import models.Employee;

public class DateComparator implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {

		return o1.getStartDate().compareTo(o2.getStartDate());
		
	}

}
