package Demo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.EmployeeDAO;
import core.ProfessorDB;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;


public class AddProfessor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	 

	@SuppressWarnings("unused")
	private EmployeeDAO employeeDAO;

	private EmployeeSearchApp employeeSearchApp;

	private ProfessorDB previousProfessorDB = null;
	private boolean updateMode = false;

	public AddProfessor(EmployeeSearchApp theEmployeeSearchApp,
			EmployeeDAO theEmployeeDAO, ProfessorDB thepreviousProfessorDB, boolean theUpdateMode) {
		this();
		employeeDAO = theEmployeeDAO;
		employeeSearchApp = theEmployeeSearchApp;

		previousProfessorDB = thepreviousProfessorDB;
		
		updateMode = theUpdateMode;

		if (updateMode) {
			setTitle("Update Professor");
			
			populateGui(previousProfessorDB);
		}
	}
	private void populateGui(ProfessorDB theProfessorDB) {
		textField.setText(theProfessorDB.getFirstName());
		textField_1.setText(theProfessorDB.getLastName());
		textField_2.setText(theProfessorDB.getEmail());
		textField_3.setText(theProfessorDB.getSalary().toString());		
	}

	public AddProfessor(EmployeeSearchApp theEmployeeSearchApp,
			EmployeeDAO theEmployeeDAO) {
		this(theEmployeeSearchApp, theEmployeeDAO, null, false);
	}
	 
	
	
	
	public static void main(String[] args) {
		try {
			AddProfessor dialog = new AddProfessor();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddProfessor() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblFirstname = new JLabel("Professor First Name");
		lblFirstname.setBounds(10, 10, 126, 13);
		contentPanel.add(lblFirstname);
		
		JLabel lblLastname = new JLabel("Professor LastName");
		lblLastname.setBounds(10, 54, 126, 13);
		contentPanel.add(lblLastname);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 111, 83, 13);
		contentPanel.add(lblEmail);
		
		JLabel lblSalary = new JLabel("Salary");
		lblSalary.setBounds(10, 160, 83, 13);
		contentPanel.add(lblSalary);
		
		textField = new JTextField();
		textField.setBounds(192, 7, 199, 19);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(192, 51, 199, 19);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(192, 108, 199, 19);
		contentPanel.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(192, 157, 199, 19);
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
						
						saveProfessorDB();
						
						
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
	
	protected void saveProfessorDB() {
// get the employee info from gui
			String firstName = textField.getText();
			String lastName = textField_1.getText();
			String email = textField_2.getText();

			String salaryStr = textField_3.getText();
			BigDecimal salary = convertStringToBigDecimal(salaryStr);

			ProfessorDB tempProfessorDB = null;

			if (updateMode) {
				tempProfessorDB = previousProfessorDB;
				
				tempProfessorDB.setLastName(lastName);
				tempProfessorDB.setFirstName(firstName);
				tempProfessorDB.setEmail(email);
				tempProfessorDB.setSalary(salary);
				
			} else {
				tempProfessorDB = new ProfessorDB(lastName, firstName, email, salary);
			}

			try {
				// save to the database
				if (updateMode) {
					EmployeeDAO.updateProfessorDB(tempProfessorDB);
				} else {
					EmployeeDAO.addProfessorDB(tempProfessorDB);
				}

				// close dialog
				setVisible(false);
				dispose();

				// refresh gui list
				employeeSearchApp.refreshProfessorDBView();

				// show success message
				JOptionPane.showMessageDialog(employeeSearchApp,
						"Professor saved succesfully.", "Professor Saved",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(employeeSearchApp,
						"Error saving Professor: " + exc.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}	
		
	}
	
	
	 
	 
	
	
}
