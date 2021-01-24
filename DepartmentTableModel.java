package Demo;



import java.util.List;

import javax.swing.table.AbstractTableModel;

import core.DepartmentDB;




public class DepartmentTableModel extends AbstractTableModel {

	public static final int OBJECT_COL = -1;
	private static final int LAST_NAME_COL = 0;
	private static final int FIRST_NAME_COL = 1;
	private static final int EMAIL_COL = 2;
	private static final int SALARY_COL = 3;

	private String[] columnNames = { "StudentId", "CourseId", "Semester",
			"Grade" };
	private List<DepartmentDB> departmentDB;

	public DepartmentTableModel(List<DepartmentDB> theDepartmentDB) {
		departmentDB = theDepartmentDB;
	}

	
	public int getColumnCount() {
		return columnNames.length;
	}


	public int getRowCount() {
		return departmentDB.size();
	}


	public String getColumnName(int col) {
		return columnNames[col];
	}

	
	public Object getValueAt(int row, int col) {

		DepartmentDB tempDepartmentDB = departmentDB.get(row);

		switch (col) {
		case LAST_NAME_COL:
			return tempDepartmentDB.getLastName();
		case FIRST_NAME_COL:
			return tempDepartmentDB.getFirstName();
		case EMAIL_COL:
			return tempDepartmentDB.getEmail();
		case SALARY_COL:
			return tempDepartmentDB.getSalary();
		case OBJECT_COL:
			return tempDepartmentDB;
		default:
			return tempDepartmentDB.getLastName();
		}
	}

	
	public Class<? extends Object> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
