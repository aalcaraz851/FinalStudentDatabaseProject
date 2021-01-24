package Demo;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import core.ProfessorDB;



public class ProfessorTableModel extends AbstractTableModel {

	public static final int OBJECT_COL = -1;
	private static final int LAST_NAME_COL = 0;
	private static final int FIRST_NAME_COL = 1;
	private static final int EMAIL_COL = 2;
	private static final int SALARY_COL = 3;

	private String[] columnNames = { "Last Name", "First Name", "Email",
			"Salary" };
	private List<ProfessorDB> professorDB;

	public ProfessorTableModel(List<ProfessorDB> theCourseDB) {
		professorDB = theCourseDB;
	}

	
	public int getColumnCount() {
		return columnNames.length;
	}


	public int getRowCount() {
		return professorDB.size();
	}


	public String getColumnName(int col) {
		return columnNames[col];
	}

	
	public Object getValueAt(int row, int col) {

		ProfessorDB tempProfessorDB = professorDB.get(row);

		switch (col) {
		case LAST_NAME_COL:
			return tempProfessorDB.getLastName();
		case FIRST_NAME_COL:
			return tempProfessorDB.getFirstName();
		case EMAIL_COL:
			return tempProfessorDB.getEmail();
		case SALARY_COL:
			return tempProfessorDB.getSalary();
		case OBJECT_COL:
			return tempProfessorDB;
		default:
			return tempProfessorDB.getLastName();
		}
	}

	
	public Class<? extends Object> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
