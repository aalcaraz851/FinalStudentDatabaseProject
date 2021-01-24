package Demo;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import core.CourseDB;



public class CourseTableModel extends AbstractTableModel {

	public static final int OBJECT_COL = -1;
	private static final int LAST_NAME_COL = 0;
	private static final int FIRST_NAME_COL = 1;
	private static final int EMAIL_COL = 2;
	private static final int SALARY_COL = 3;

	private String[] columnNames = { "Last Name", "First Name", "Email",
			"Units" };
	private List<CourseDB> courseDB;

	public CourseTableModel(List<CourseDB> theCourseDB) {
		courseDB = theCourseDB;
	}

	
	public int getColumnCount() {
		return columnNames.length;
	}


	public int getRowCount() {
		return courseDB.size();
	}


	public String getColumnName(int col) {
		return columnNames[col];
	}

	
	public Object getValueAt(int row, int col) {

		CourseDB tempCourseDB = courseDB.get(row);

		switch (col) {
		case LAST_NAME_COL:
			return tempCourseDB.getLastName();
		case FIRST_NAME_COL:
			return tempCourseDB.getFirstName();
		case EMAIL_COL:
			return tempCourseDB.getEmail();
		case SALARY_COL:
			return tempCourseDB.getSalary();
		case OBJECT_COL:
			return tempCourseDB;
		default:
			return tempCourseDB.getLastName();
		}
	}


	public Class<? extends Object> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
