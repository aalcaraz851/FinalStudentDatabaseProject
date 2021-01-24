package Demo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.EmployeeDAO;
import core.EnrollmentDB;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;


public class AddEnrollment extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	
	
	private EmployeeDAO employeeDAO;

	private EmployeeSearchApp employeeSearchApp;

	private EnrollmentDB previousEnrollmentDB = null;
	private boolean updateMode = false;

	public AddEnrollment(EmployeeSearchApp theEmployeeSearchApp,
			EmployeeDAO theEmployeeDAO, EnrollmentDB thePreviousEnrollmentDB, boolean theUpdateMode) {
		this();
		employeeDAO = theEmployeeDAO;
		employeeSearchApp = theEmployeeSearchApp;

		previousEnrollmentDB = thePreviousEnrollmentDB;
		
		updateMode = theUpdateMode;

		if (updateMode) {
			setTitle("Update EnrollmentDB");
			
			populateGui(previousEnrollmentDB);
		}
	}
	private void populateGui(EnrollmentDB theEnrollmentDB) {
		textField.setText(theEnrollmentDB.getFirstName());
		textField_1.setText(theEnrollmentDB.getLastName());
		textField_2.setText(theEnrollmentDB.getEmail());
		textField_3.setText(theEnrollmentDB.getSalary().toString());		
	}

	public AddEnrollment(EmployeeSearchApp theEmployeeSearchApp,
			EmployeeDAO theEmployeeDAO) {
		this(theEmployeeSearchApp, theEmployeeDAO, null, false);
	}

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddEnrollment dialog = new AddEnrollment();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddEnrollment() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblFirstname = new JLabel("StudentID");
			lblFirstname.setBounds(10, 10, 85, 28);
			contentPanel.add(lblFirstname);
		}
		{
			JLabel lblLastname = new JLabel("Year");
			lblLastname.setBounds(10, 48, 85, 26);
			contentPanel.add(lblLastname);
		}
		{
			JLabel lblEmail = new JLabel("CourseID");
			lblEmail.setBounds(10, 100, 85, 26);
			contentPanel.add(lblEmail);
		}
		{
			JLabel lblSalary = new JLabel("Semester");
			lblSalary.setBounds(10, 150, 85, 26);
			contentPanel.add(lblSalary);
		}
		{
			textField = new JTextField();
			textField.setBounds(91, 15, 214, 19);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			textField_1 = new JTextField();
			textField_1.setBounds(91, 52, 214, 19);
			contentPanel.add(textField_1);
			textField_1.setColumns(10);
		}
		{
			textField_2 = new JTextField();
			textField_2.setBounds(91, 104, 214, 19);
			contentPanel.add(textField_2);
			textField_2.setColumns(10);
		}
		{
			textField_3 = new JTextField();
			textField_3.setBounds(91, 154, 214, 19);
			contentPanel.add(textField_3);
			textField_3.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveEnrollmentDB();
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
	
	protected void saveEnrollmentDB() {
// get the employee info from gui
			String firstName = textField.getText();
			String lastName = textField_1.getText();
			String email = textField_2.getText();

			String salaryStr = textField_3.getText();
			BigDecimal salary = convertStringToBigDecimal(salaryStr);

			EnrollmentDB tempEnrollmentDB = null;

			if (updateMode) {
				tempEnrollmentDB = previousEnrollmentDB;
				
				tempEnrollmentDB.setLastName(lastName);
				tempEnrollmentDB.setFirstName(firstName);
				tempEnrollmentDB.setEmail(email);
				tempEnrollmentDB.setSalary(salary);
				
			} else {
				tempEnrollmentDB = new EnrollmentDB(lastName, firstName, email, salary);
			}

			try {
				// save to the database
				if (updateMode) {
					EmployeeDAO.updateEnrollmentDB(tempEnrollmentDB);
				} else {
					EmployeeDAO.addEnrollmentDB(tempEnrollmentDB);
				}

				// close dialog
				setVisible(false);
				dispose();

				// refresh gui list
				employeeSearchApp.refreshEnrollmentDBView();

				// show success message
				JOptionPane.showMessageDialog(employeeSearchApp,
						"Enrollment saved succesfully.", "Enrolled Saved",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(employeeSearchApp,
						"Error saving Enrolled: " + exc.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}	
		
	}
	
	 

}
