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
import core.DepartmentDB;


public class AddDepartment extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	
	
	
	
	
	private EmployeeDAO employeeDAO;

	private EmployeeSearchApp employeeSearchApp;

	private DepartmentDB previousDepartmentDB = null;
	private boolean updateMode = false;

	public AddDepartment(EmployeeSearchApp theEmployeeSearchApp,
			EmployeeDAO theEmployeeDAO, DepartmentDB thePreviousDepartmentDB, boolean theUpdateMode) {
		this();
		employeeDAO = theEmployeeDAO;
		employeeSearchApp = theEmployeeSearchApp;

		previousDepartmentDB = thePreviousDepartmentDB;
		
		updateMode = theUpdateMode;

		if (updateMode) {
			setTitle("Update Grade");
			
			populateGui(previousDepartmentDB);
		}
	}
	private void populateGui(DepartmentDB theDepartmentDB) {
		textField.setText(theDepartmentDB.getFirstName());
		textField_1.setText(theDepartmentDB.getLastName());
		textField_2.setText(theDepartmentDB.getEmail());
		textField_3.setText(theDepartmentDB.getSalary().toString());		
	}

	public AddDepartment(EmployeeSearchApp theEmployeeSearchApp,
			EmployeeDAO theEmployeeDAO) {
		this(theEmployeeSearchApp, theEmployeeDAO, null, false);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddDepartment dialog = new AddDepartment();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddDepartment() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblFirstname = new JLabel("StudentId");
			lblFirstname.setBounds(10, 26, 114, 24);
			contentPanel.add(lblFirstname);
		}
		{
			JLabel lblLastname = new JLabel("CourseID");
			lblLastname.setBounds(10, 75, 81, 24);
			contentPanel.add(lblLastname);
		}
		{
			JLabel lblSalary = new JLabel("Semester");
			lblSalary.setBounds(10, 122, 114, 24);
			contentPanel.add(lblSalary);
		}
		{
			JLabel lblEmail = new JLabel("Grade");
			lblEmail.setBounds(10, 177, 114, 13);
			contentPanel.add(lblEmail);
		}
		{
			textField = new JTextField();
			textField.setBounds(167, 29, 240, 19);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			textField_1 = new JTextField();
			textField_1.setBounds(167, 78, 240, 19);
			contentPanel.add(textField_1);
			textField_1.setColumns(10);
		}
		{
			textField_2 = new JTextField();
			textField_2.setBounds(167, 125, 240, 19);
			contentPanel.add(textField_2);
			textField_2.setColumns(10);
		}
		{
			textField_3 = new JTextField();
			textField_3.setBounds(167, 174, 240, 19);
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
						saveDepartmentDB();
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
	
	protected void saveDepartmentDB() {
// get the employee info from gui
			String firstName = textField.getText();
			String lastName = textField_1.getText();
			String email = textField_2.getText();

			String salaryStr = textField_3.getText();
			BigDecimal salary = convertStringToBigDecimal(salaryStr);

			DepartmentDB tempDepartmentDB = null;

			if (updateMode) {
				tempDepartmentDB = previousDepartmentDB;
				
				tempDepartmentDB.setLastName(lastName);
				tempDepartmentDB.setFirstName(firstName);
				tempDepartmentDB.setEmail(email);
				tempDepartmentDB.setSalary(salary);
				
			} else {
				tempDepartmentDB = new DepartmentDB(lastName, firstName, email, salary);
			}

			try {
				// save to the database
				if (updateMode) {
					EmployeeDAO.updateDepatmentDB(tempDepartmentDB);
				} else {
					EmployeeDAO.addDepartmentDB(tempDepartmentDB);
				}

				// close dialog
				setVisible(false);
				dispose();

				// refresh gui list
				employeeSearchApp.refreshDepartmentDBView();

				// show success message
				JOptionPane.showMessageDialog(employeeSearchApp,
						"Grade saved succesfully.", "Grade Saved",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(employeeSearchApp,
						"Error saving Grade: " + exc.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}	
		
	}
	
	
	 
	
	
	
}
