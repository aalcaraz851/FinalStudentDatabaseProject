package Demo;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import DAO.EmployeeDAO;
import core.CourseDB;
import core.DepartmentDB;
import core.Employee;
import core.EnrollmentDB;
import core.ProfessorDB;


public class EmployeeSearchApp extends JFrame {

	
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable table;
	private EmployeeDAO employeeDAO;
	private JTabbedPane tabbedPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel;
	private JLabel lblEnterLastName;
	private JTextField textField;
	private JButton btnSearch;
	private JButton btnDelete;
	private JLabel lblProfessorName;
	private JLabel lblEnrollment;
	private JLabel lblDepartment;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTable table_1;
	private JTable table_2;
	private JTable table_3;
	private JTable table_4;
	private JButton btnAddCourse;
	private JButton btnUpdate_1;
	private JButton btnDelete_1;
	private JButton btnAddprofessor;
	private JButton btnUpdate_2;
	private JButton btnDelete_2;
	private JButton btnAddenrollment;
	private JButton btnUpdate_3;
	private JButton btnDelete_3;
	private JButton btnAdddepartment;
	private JButton btnUpdate_4;
	private JButton btnDelete_4;
	
	
	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeSearchApp frame = new EmployeeSearchApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EmployeeSearchApp() {
		
		// create the DAO
		try {
			employeeDAO = new EmployeeDAO();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
		}
		
		setTitle("Student Database Final");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(250, 250, 559, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 28, 525, 373);
		contentPane.add(tabbedPane);
		
		panel_1 = new JPanel();
		tabbedPane.addTab("Student", null, panel_1, null);
		panel_1.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 411, 40);
		panel_1.add(panel);
		panel.setLayout(null);
		
		lblEnterLastName = new JLabel("Student Name");
		lblEnterLastName.setBounds(10, 19, 86, 13);
		panel.add(lblEnterLastName);
		
		textField = new JTextField();
		textField.setBounds(106, 16, 149, 19);
		textField.setColumns(10);
		panel.add(textField);
		
		btnSearch = new JButton("Search");
		btnSearch.setBounds(265, 16, 85, 21);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get last name from the text field

				// Call DAO and get employees for the last name

				// If last name is empty, then get all employees

				// Print out employees				
				
				try {
					String lastName = textField.getText();

					List<Employee> employees = null;

					if (lastName != null && lastName.trim().length() > 0) {
						employees = employeeDAO.searchEmployees(lastName);
					} else {
						employees = employeeDAO.getAllEmployees();
					}
					
					// create the model and update the "table"
					EmployeeTableModel model = new EmployeeTableModel(employees);
					
					table.setModel(model);
					
					/*
					for (Employee temp : employees) {
						System.out.println(temp);
					}
					*/
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
				}
				
			}
		});
		panel.add(btnSearch);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 41, 520, 200);
		panel_1.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnAddemployee = new JButton("AddStudent");
		btnAddemployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// create dialog
				AddEmployee dialog = new AddEmployee(EmployeeSearchApp.this, employeeDAO);

				// show dialog
				dialog.setVisible(true);
	
		panel_1.add(btnSearch);
	}
	});
		
		btnAddemployee.setBounds(0, 254, 131, 32);
		panel_1.add(btnAddemployee);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//get the selected item
				int row = table.getSelectedRow();
				
				// make sure a row is selected
				if (row < 0) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this, "You must select an Student", "Error",
							JOptionPane.ERROR_MESSAGE);				
					return;
				}
				
				// get the current employee
				Employee tempEmployee = (Employee) table.getValueAt(row, EmployeeTableModel.OBJECT_COL);
				
				// create dialog
				AddEmployee dialog = new AddEmployee(EmployeeSearchApp.this, employeeDAO, 
															tempEmployee, true);

				// show dialog
				dialog.setVisible(true);
				
			}
		});
		btnUpdate.setBounds(195, 254, 131, 32);
		panel_1.add(btnUpdate);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					// get the selected row
					int row = table.getSelectedRow();

					// make sure a row is selected
					if (row < 0) {
						JOptionPane.showMessageDialog(EmployeeSearchApp.this, 
								"You must select an Student", "Error", JOptionPane.ERROR_MESSAGE);				
						return;
					}

					// prompt the user
					int response = JOptionPane.showConfirmDialog(
							EmployeeSearchApp.this, "Delete this Student?", "Confirm", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response != JOptionPane.YES_OPTION) {
						return;
					}

					// get the current employee
					Employee tempEmployee = (Employee) table.getValueAt(row, EmployeeTableModel.OBJECT_COL);

					// delete the employee
					employeeDAO.deleteEmployee(tempEmployee.getId());

					// refresh GUI
					refreshEmployeesView();

					// show success message
					JOptionPane.showMessageDialog(EmployeeSearchApp.this,
							"Student deleted succesfully.", "Student Deleted",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception exc) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this,
							"Error deleting Student: " + exc.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
	
			}
		});
		btnDelete.setBounds(383, 254, 131, 32);
		panel_1.add(btnDelete);
		
		panel_2 = new JPanel();
		tabbedPane.addTab("Course", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblCourseName = new JLabel("Course Name");
		lblCourseName.setBounds(10, 10, 84, 31);
		panel_2.add(lblCourseName);
		
		textField_1 = new JTextField();
		textField_1.setBounds(106, 16, 149, 19);
		panel_2.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnSearch_1 = new JButton("Search");
		btnSearch_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				// Get last name from the text field

				// Call DAO and get employees for the last name

				// If last name is empty, then get all employees

				// Print out employees				
				
				try {
					String lastName = textField_1.getText();

					List<CourseDB> course = null;

					if (lastName != null && lastName.trim().length() > 0) 
					{
						course = EmployeeDAO.searchCourseDB(lastName);
					} else {
						course = EmployeeDAO.getAllCourseDB();
					}
					
					// create the model and update the "table"
					CourseTableModel model = new CourseTableModel(course);
					table_1.setModel(model);
					
					/*
					for (CourseDB temp : course) {
						System.out.println(temp);
					}
					*/
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
				}
	
			}
		});
		btnSearch_1.setBounds(265, 16, 85, 21);
		panel_2.add(btnSearch_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 41, 520, 200);
		panel_2.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		btnAddCourse = new JButton("Add Course");
		btnAddCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// create dialog
				AddCourse dialog = new AddCourse(EmployeeSearchApp.this, employeeDAO);

				// show dialog
				dialog.setVisible(true);
	
		panel_2.add(btnSearch_1);
			}
		});
		btnAddCourse.setBounds(0, 254, 131, 32);
		panel_2.add(btnAddCourse);
		
		btnUpdate_1 = new JButton("Update");
		btnUpdate_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				//get the selected item
				int row = table_1.getSelectedRow();
				
				// make sure a row is selected
				if (row < 0) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this, "You must select an Course", "Error",
							JOptionPane.ERROR_MESSAGE);				
					return;
				}
				
				// get the current employee
				CourseDB tempCourseDB = (CourseDB) table_1.getValueAt(row, CourseTableModel.OBJECT_COL);
				
				// create dialog
				AddCourse dialog = new AddCourse(EmployeeSearchApp.this, employeeDAO, 
						tempCourseDB, true);

				// show dialog
				dialog.setVisible(true);
			}
		});
		btnUpdate_1.setBounds(195, 254, 131, 32);
		panel_2.add(btnUpdate_1);
		
		btnDelete_1 = new JButton("Delete");
		btnDelete_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					// get the selected row
					int row = table_1.getSelectedRow();

					// make sure a row is selected
					if (row < 0) {
						JOptionPane.showMessageDialog(EmployeeSearchApp.this, 
								"You must select an Course", "Error", JOptionPane.ERROR_MESSAGE);				
						return;
					}

					// prompt the user
					int response = JOptionPane.showConfirmDialog(
							EmployeeSearchApp.this, "Delete this Coruse?", "Confirm", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response != JOptionPane.YES_OPTION) {
						return;
					}

					// get the current employee
					CourseDB tempCourseDB = (CourseDB) table_1.getValueAt(row, CourseTableModel.OBJECT_COL);

					// delete the employee
					EmployeeDAO.deleteCourseDB(tempCourseDB.getId());

					// refresh GUI
					refreshCourseDBView();

					// show success message
					JOptionPane.showMessageDialog(EmployeeSearchApp.this,
							"Coruse deleted succesfully.", "Coruse Deleted",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception exc) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this,
							"Error deleting Coruse: " + exc.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			
			}
		});
		btnDelete_1.setBounds(383, 254, 131, 32);
		panel_2.add(btnDelete_1);
		
		panel_3 = new JPanel();
		tabbedPane.addTab("Proffessor", null, panel_3, null);
		panel_3.setLayout(null);
		
		lblProfessorName = new JLabel("Professor Name");
		lblProfessorName.setBounds(10, 9, 96, 35);
		panel_3.add(lblProfessorName);
		
		textField_2 = new JTextField();
		textField_2.setBounds(106, 16, 149, 19);
		panel_3.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnSearch_2 = new JButton("Search");
		btnSearch_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get last name from the text field

				// Call DAO and get employees for the last name

				// If last name is empty, then get all employees

				// Print out employees				
				
				try {
					String lastName = textField_2.getText();

					List<ProfessorDB> professor = null;

					if (lastName != null && lastName.trim().length() > 0) 
					{
						professor = EmployeeDAO.searchProfessorDB(lastName);
					} else {
						professor = EmployeeDAO.getAllProfessorDB();
					}
					
					// create the model and update the "table"
					ProfessorTableModel model = new ProfessorTableModel(professor);
					table_2.setModel(model);
					
					/*
					for (ProfessorDB temp : professor) {
						System.out.println(temp);
					}
					*/
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
				}

			}
		});
		btnSearch_2.setBounds(265, 16, 85, 21);
		panel_3.add(btnSearch_2);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(0, 41, 520, 200);
		panel_3.add(scrollPane_2);
		
		table_2 = new JTable();
		scrollPane_2.setViewportView(table_2);
		
		btnAddprofessor = new JButton("AddProfessor");
		btnAddprofessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// create dialog
				AddProfessor dialog = new AddProfessor(EmployeeSearchApp.this, employeeDAO);

				// show dialog
				dialog.setVisible(true);
	
				panel_3.add(btnSearch_2);	
				
				
			}
		});
		btnAddprofessor.setBounds(0, 254, 131, 32);
		panel_3.add(btnAddprofessor);
		
		btnUpdate_2 = new JButton("Update");
		btnUpdate_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				


				//get the selected item
				int row = table_2.getSelectedRow();
				
				// make sure a row is selected
				if (row < 0) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this, "You must select an Professor", "Error",
							JOptionPane.ERROR_MESSAGE);				
					return;
				}
				
				// get the current employee
				ProfessorDB tempProfessorDB = (ProfessorDB) table_2.getValueAt(row, ProfessorTableModel.OBJECT_COL);
				
				// create dialog
				AddProfessor dialog = new AddProfessor(EmployeeSearchApp.this, employeeDAO, 
						tempProfessorDB, true);

				// show dialog
				dialog.setVisible(true);
				
				
			}
		});
		btnUpdate_2.setBounds(195, 254, 131, 32);
		panel_3.add(btnUpdate_2);
		
		btnDelete_2 = new JButton("Delete");
		btnDelete_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {



				try {
					// get the selected row
					int row = table_2.getSelectedRow();

					// make sure a row is selected
					if (row < 0) {
						JOptionPane.showMessageDialog(EmployeeSearchApp.this, 
								"You must select an Professor", "Error", JOptionPane.ERROR_MESSAGE);				
						return;
					}

					// prompt the user
					int response = JOptionPane.showConfirmDialog(
							EmployeeSearchApp.this, "Delete this Professor?", "Confirm", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response != JOptionPane.YES_OPTION) {
						return;
					}

					// get the current employee
					ProfessorDB tempProfessorDB = (ProfessorDB) table_2.getValueAt(row, ProfessorTableModel.OBJECT_COL);

					// delete the employee
					EmployeeDAO.deleteProfessorDB(tempProfessorDB.getId());

					// refresh GUI
					refreshProfessorDBView();

					// show success message
					JOptionPane.showMessageDialog(EmployeeSearchApp.this,
							"Professor deleted succesfully.", "Professor Deleted",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception exc) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this,
							"Error deleting ProfessorDB: " + exc.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}

				
				
			}
		});
		btnDelete_2.setBounds(383, 254, 131, 32);
		panel_3.add(btnDelete_2);
		
		panel_4 = new JPanel();
		tabbedPane.addTab("Enrollment", null, panel_4, null);
		panel_4.setLayout(null);
		
		lblEnrollment = new JLabel("Enrollment");
		lblEnrollment.setBounds(10, 16, 72, 21);
		panel_4.add(lblEnrollment);
		
		textField_3 = new JTextField();
		textField_3.setBounds(105, 16, 149, 19);
		panel_4.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnSearch_3 = new JButton("Search");
		btnSearch_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get last name from the text field

				// Call DAO and get employees for the last name

				// If last name is empty, then get all employees

				// Print out employees				
				
				try {
					String lastName = textField_3.getText();

					List<EnrollmentDB> enrollment = null;

					if (lastName != null && lastName.trim().length() > 0) 
					{
						enrollment = EmployeeDAO.searchEnrollmentDB(lastName);
					} else {
						enrollment = EmployeeDAO.getAllEnrollmentDB();
					}
					
					// create the model and update the "table"
					EnrollmentTableModel model = new EnrollmentTableModel(enrollment);
					table_3.setModel(model);
					
					/*
					for (EnrollmentDB temp : enrollment) {
						System.out.println(temp);
					}
					*/
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
				}
				
				
				
				
				
				
				
				
				
			}
		});
		btnSearch_3.setBounds(265, 16, 85, 21);
		panel_4.add(btnSearch_3);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(0, 41, 520, 200);
		panel_4.add(scrollPane_3);
		
		table_3 = new JTable();
		scrollPane_3.setViewportView(table_3);
		
		btnAddenrollment = new JButton("AddEnrollment");
		btnAddenrollment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// create dialog
				AddEnrollment dialog = new AddEnrollment(EmployeeSearchApp.this, employeeDAO);

				// show dialog
				dialog.setVisible(true);
	
		panel_4.add(btnSearch_3);
		
			}
		});
		btnAddenrollment.setBounds(0, 254, 131, 32);
		panel_4.add(btnAddenrollment);
		
		btnUpdate_3 = new JButton("Update");
		btnUpdate_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				



				//get the selected item
				int row = table_3.getSelectedRow();
				
				// make sure a row is selected
				if (row < 0) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this, "You must select an Enrollment", "Error",
							JOptionPane.ERROR_MESSAGE);				
					return;
				}
				
				// get the current employee
				EnrollmentDB tempEnrollmentDB = (EnrollmentDB) table_3.getValueAt(row, EnrollmentTableModel.OBJECT_COL);
				
				// create dialog
				AddEnrollment dialog = new AddEnrollment(EmployeeSearchApp.this, employeeDAO, 
						tempEnrollmentDB, true);

				// show dialog
				dialog.setVisible(true);
				
				
			}
		});
		btnUpdate_3.setBounds(195, 254, 131, 32);
		panel_4.add(btnUpdate_3);
		
		btnDelete_3 = new JButton("Delete");
		btnDelete_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				

				try {
					// get the selected row
					int row = table_3.getSelectedRow();

					// make sure a row is selected
					if (row < 0) {
						JOptionPane.showMessageDialog(EmployeeSearchApp.this, 
								"You must select an Enrollment", "Error", JOptionPane.ERROR_MESSAGE);				
						return;
					}

					// prompt the user
					int response = JOptionPane.showConfirmDialog(
							EmployeeSearchApp.this, "Delete this Enrollment?", "Confirm", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response != JOptionPane.YES_OPTION) {
						return;
					}

					// get the current employee
					EnrollmentDB tempEnrollmentDB = (EnrollmentDB) table_3.getValueAt(row, EnrollmentTableModel.OBJECT_COL);

					// delete the employee
					EmployeeDAO.deleteEnrollmentDB(tempEnrollmentDB.getId());

					// refresh GUI
					refreshEnrollmentDBView();

					// show success message
					JOptionPane.showMessageDialog(EmployeeSearchApp.this,
							"Enrollment deleted succesfully.", "Enrollment Deleted",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception exc) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this,
							"Error deleting Enrollment: " + exc.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}


			}
		});
		btnDelete_3.setBounds(383, 254, 131, 32);
		panel_4.add(btnDelete_3);
		
		panel_5 = new JPanel();
		tabbedPane.addTab("Grade", null, panel_5, null);
		panel_5.setLayout(null);
		
		lblDepartment = new JLabel("Search CourseID");
		lblDepartment.setBounds(10, 15, 86, 24);
		panel_5.add(lblDepartment);
		
		textField_4 = new JTextField();
		textField_4.setBounds(105, 16, 149, 19);
		panel_5.add(textField_4);
		textField_4.setColumns(10);
		
		JButton btnSearch_4 = new JButton("Search");
		btnSearch_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Get last name from the text field

				// Call DAO and get employees for the last name

				// If last name is empty, then get all employees

				// Print out employees				
				
				try {
					String lastName = textField_4.getText();

					List< DepartmentDB> department = null;

					if (lastName != null && lastName.trim().length() > 0) 
					{
						department = EmployeeDAO.searchDepartmentDB(lastName);
					} else {
						department = EmployeeDAO.getAllDepartmentDB();
					}
					
					// create the model and update the "table"
					DepartmentTableModel model = new DepartmentTableModel(department);
					table_4.setModel(model);

					// refresh GUI CHange if needed to
					
					refreshEmployeesView();

					/*
					for (DepartmentDB temp : department) {
					System.out.println(temp);
					}
					*/
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
				}
				
	
			}
		});
		btnSearch_4.setBounds(265, 16, 85, 21);
		panel_5.add(btnSearch_4);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(0, 41, 520, 200);
		panel_5.add(scrollPane_4);
		
		table_4 = new JTable();
		scrollPane_4.setViewportView(table_4);
		
		btnAdddepartment = new JButton("AddGrade");
		btnAdddepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				// create dialog
				AddDepartment dialog = new AddDepartment(EmployeeSearchApp.this, employeeDAO);

				// show dialog
				dialog.setVisible(true);
	
		panel_5.add(btnSearch_4);
				
				
			}
		});
		btnAdddepartment.setBounds(0, 254, 131, 32);
		panel_5.add(btnAdddepartment);
		
		btnUpdate_4 = new JButton("Update");
		btnUpdate_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				




				//get the selected item
				int row = table_4.getSelectedRow();
				
				// make sure a row is selected
				if (row < 0) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this, "You must select an Grade", "Error",
							JOptionPane.ERROR_MESSAGE);				
					return;
				}
				
				// get the current employee
				DepartmentDB tempDepartmentDB = (DepartmentDB) table_4.getValueAt(row, DepartmentTableModel.OBJECT_COL);
				
				// create dialog
				AddDepartment dialog = new AddDepartment(EmployeeSearchApp.this, employeeDAO, 
						tempDepartmentDB, true);

				// show dialog
				dialog.setVisible(true);
				
			}
		});
		btnUpdate_4.setBounds(195, 254, 131, 32);
		panel_5.add(btnUpdate_4);
		
		btnDelete_4 = new JButton("Delete");
		btnDelete_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					// get the selected row
					int row = table_4.getSelectedRow();

					// make sure a row is selected
					if (row < 0) {
						JOptionPane.showMessageDialog(EmployeeSearchApp.this, 
								"You must select an Grade", "Error", JOptionPane.ERROR_MESSAGE);				
						return;
					}

					// prompt the user
					int response = JOptionPane.showConfirmDialog(
							EmployeeSearchApp.this, "Delete this Grade?", "Confirm", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response != JOptionPane.YES_OPTION) {
						return;
					}

					// get the current employee
					DepartmentDB tempDepartmentDB = (DepartmentDB) table_4.getValueAt(row, DepartmentTableModel.OBJECT_COL);
					// delete the employee
					EmployeeDAO.deleteDepartmentDB(tempDepartmentDB.getId());

					// refresh GUI
					refreshDepartmentDBView();

					// show success message
					JOptionPane.showMessageDialog(EmployeeSearchApp.this,
							"Grade deleted succesfully.", "Grade Deleted",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception exc) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this,
							"Error deleting Grade: " + exc.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}

	
			}
		});
		btnDelete_4.setBounds(383, 254, 131, 32);
		panel_5.add(btnDelete_4);
	}

	protected void refreshDepartmentDBView() {

		try {
			List<DepartmentDB> departmentdb = EmployeeDAO.getAllDepartmentDB();

			// create the model and update the "table"
			DepartmentTableModel model = new DepartmentTableModel(departmentdb);

			table_4.setModel(model);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

	protected void refreshEnrollmentDBView() {

		try {
			List<EnrollmentDB> enrollmentdb = EmployeeDAO.getAllEnrollmentDB();

			// create the model and update the "table"
			EnrollmentTableModel model = new EnrollmentTableModel(enrollmentdb);

			table_3.setModel(model);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void refreshEmployeesView() {

		try {
			List<Employee> employees = employeeDAO.getAllEmployees();

			// create the model and update the "table"
			EmployeeTableModel model = new EmployeeTableModel(employees);

			table.setModel(model);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
	public void refreshProfessorDBView() {

		try {
			List<ProfessorDB> professordb = EmployeeDAO.getAllProfessorDB();

			// create the model and update the "table"
			ProfessorTableModel model = new ProfessorTableModel(professordb);

			table_2.setModel(model);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void refreshCourseDBView() {

		try {
			List<CourseDB> coursedb = EmployeeDAO.getAllCourseDB();

			// create the model and update the "table"
			CourseTableModel model = new CourseTableModel(coursedb);

			table_1.setModel(model);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}

