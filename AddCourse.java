package Demo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import DAO.EmployeeDAO;
import core.CourseDB;




public class AddCourse extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	
	
	private EmployeeDAO employeeDAO;
	private EmployeeSearchApp employeeSearchApp ;
	 
	private CourseDB previousCourseDB = null;
	private boolean updateMode = false;
	
public AddCourse(EmployeeSearchApp theEmployeeSearchApp,
			EmployeeDAO theEmployeeDAO, CourseDB thePreviousCourseDB, boolean theUpdateMode) {
		this();
		employeeDAO = theEmployeeDAO;
		employeeSearchApp = theEmployeeSearchApp;

		previousCourseDB = thePreviousCourseDB;
		
		updateMode = theUpdateMode;

		if (updateMode) {
			setTitle("Update Course");
			
			populateGui(previousCourseDB);
		}
	}
	private void populateGui(CourseDB theCourseDB) {
		textField.setText(theCourseDB.getFirstName());
		textField_1.setText(theCourseDB.getLastName());
		textField_2.setText(theCourseDB.getEmail());
		textField_3.setText(theCourseDB.getSalary().toString());		
	}

	public AddCourse(EmployeeSearchApp theEmployeeSearchApp,
			EmployeeDAO theEmployeeDAO) {
		this(theEmployeeSearchApp, theEmployeeDAO, null, false);
	}

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddCourse dialog = new AddCourse();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	
	public AddCourse() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblFirstname = new JLabel("FirstName");
		lblFirstname.setBounds(10, 10, 81, 23);
		contentPanel.add(lblFirstname);
		
		textField = new JTextField();
		textField.setBounds(101, 12, 247, 19);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblLastname = new JLabel("LastName");
		lblLastname.setBounds(10, 60, 46, 13);
		contentPanel.add(lblLastname);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 107, 46, 13);
		contentPanel.add(lblEmail);
		
		JLabel lblSalary = new JLabel("Units");
		lblSalary.setBounds(10, 158, 46, 13);
		contentPanel.add(lblSalary);
		
		textField_1 = new JTextField();
		textField_1.setBounds(101, 57, 247, 19);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(101, 104, 247, 19);
		contentPanel.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(101, 155, 247, 19);
		contentPanel.add(textField_3);
		textField_3.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						saveCourseDB();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	protected BigDecimal convertStringToBigDecimal(String salaryStr) {

		BigDecimal result = null;

		try {
			double salaryDouble = Double.parseDouble(salaryStr);

			result = BigDecimal.valueOf(salaryDouble);
		} catch (Exception exc) {
			System.out.println("Invalid value. Defaulting to 0.0");
			result = BigDecimal.valueOf(0.0);
		}

		return result;
	}
	
	protected void saveCourseDB() {
// get the employee info from gui
			String firstName = textField.getText();
			String lastName = textField_1.getText();
			String email = textField_2.getText();

			String salaryStr = textField_3.getText();
			BigDecimal salary = convertStringToBigDecimal(salaryStr);

			CourseDB tempCourseDB = null;

			if (updateMode) {
				tempCourseDB = previousCourseDB;
				
				tempCourseDB.setLastName(lastName);
				tempCourseDB.setFirstName(firstName);
				tempCourseDB.setEmail(email);
				tempCourseDB.setSalary(salary);
				
			} else {
				tempCourseDB = new CourseDB(lastName, firstName, email, salary);
			}

			try {
				// save to the database
				if (updateMode) {
					EmployeeDAO.updateCourseDB(tempCourseDB);
				} else {
					EmployeeDAO.addCourseDB(tempCourseDB);
				}

				// close dialog
				setVisible(false);
				dispose();

				// refresh gui list
				employeeSearchApp.refreshCourseDBView();

				// show success message
				JOptionPane.showMessageDialog(employeeSearchApp,
						"Course saved succesfully.", "Course Saved",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(employeeSearchApp,
						"Error saving Course: " + exc.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}	
		
	}
	
	
	
	
}


