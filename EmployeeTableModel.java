package Demo;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import core.Employee;


public class EmployeeTableModel extends AbstractTableModel {

	public static final int OBJECT_COL = -1;
	private static final int LAST_NAME_COL = 0;
	private static final int FIRST_NAME_COL = 1;
	private static final int EMAIL_COL = 2;
	private static final int SALARY_COL = 3;


	private String[] columnNames = { "Last Name", "First Name", "Email",
			"GPA" };
	private List<Employee> employees;

	public EmployeeTableModel(List<Employee> theEmployees) {
		employees = theEmployees;
	}

	
	public int getColumnCount() {
		return columnNames.length;
	}


	public int getRowCount() {
		return employees.size();
	}

	
	public String getColumnName(int col) {
		return columnNames[col];
	}

	
	public Object getValueAt(int row, int col) {

		Employee tempEmployee = employees.get(row);

		switch (col) {
		case LAST_NAME_COL:
			return tempEmployee.getLastName();
		case FIRST_NAME_COL:
			return tempEmployee.getFirstName();
		case EMAIL_COL:
			return tempEmployee.getEmail();
		case SALARY_COL:
			return tempEmployee.getSalary();
		case OBJECT_COL:
			return tempEmployee;
		default:
			return tempEmployee.getLastName();
		}
	}



	public Class<? extends Object> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
