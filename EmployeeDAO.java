package DAO;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import core.CourseDB;
import core.DepartmentDB;
import core.Employee;
import core.EnrollmentDB;
import core.ProfessorDB;


public class EmployeeDAO {

	private static Connection myConn;
	
	public EmployeeDAO() throws Exception {
		
		// get db properties
		Properties props = new Properties();
		props.load(new FileInputStream("demo.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		// connect to database
		myConn = DriverManager.getConnection(dburl, user, password);
		
		System.out.println("DB connection successful to: " + dburl);
	}
	
	public void deleteEmployee(int employeeId) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("delete from employees where id=?");
			
			// set param
			myStmt.setInt(1, employeeId);
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
	}
	
	public void updateEmployee(Employee theEmployee) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("update employees"
					+ " set first_name=?, last_name=?, email=?, salary=?"
					+ " where id=?");
			
			// set params
			myStmt.setString(1, theEmployee.getFirstName());
			myStmt.setString(2, theEmployee.getLastName());
			myStmt.setString(3, theEmployee.getEmail());
			myStmt.setBigDecimal(4, theEmployee.getSalary());
			myStmt.setInt(5, theEmployee.getId());
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
		
	}
	
	public void addEmployee(Employee theEmployee) throws Exception {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("insert into employees"
					+ " (first_name, last_name, email, salary)"
					+ " values (?, ?, ?, ?)");
			
			// set params
			myStmt.setString(1, theEmployee.getFirstName());
			myStmt.setString(2, theEmployee.getLastName());
			myStmt.setString(3, theEmployee.getEmail());
			myStmt.setBigDecimal(4, theEmployee.getSalary());
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
		
	}
	
	public List<Employee> getAllEmployees() throws Exception {
		List<Employee> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from employees order by last_name");
			
			while (myRs.next()) {
				Employee tempEmployee = convertRowToEmployee(myRs);
				list.add(tempEmployee);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Employee> searchEmployees(String lastName) throws Exception {
		List<Employee> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			lastName += "%";
			myStmt = myConn.prepareStatement("select * from employees where last_name like ?  order by last_name");
			
			myStmt.setString(1, lastName);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Employee tempEmployee = convertRowToEmployee(myRs);
				list.add(tempEmployee);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	private Employee convertRowToEmployee(ResultSet myRs) throws SQLException {
		
		int id = myRs.getInt("id");
		String lastName = myRs.getString("last_name");
		String firstName = myRs.getString("first_name");
		String email = myRs.getString("email");
		BigDecimal salary = myRs.getBigDecimal("salary");
		
		Employee tempEmployee = new Employee(id, lastName, firstName, email, salary);
		
		return tempEmployee;
	}

	
	   //CourseDB
	 
		public static void deleteCourseDB(int id) throws SQLException {
			PreparedStatement myStmt = null;

			try {
				// prepare statement
				myStmt = myConn.prepareStatement("delete from course where id=?");
				
				// set param
				myStmt.setInt(1, id);
				
				// execute SQL
				myStmt.executeUpdate();			
			}
			finally {
				close(myStmt);
			}
		}
		
		public static void updateCourseDB(CourseDB theCourseDB) throws SQLException {
			PreparedStatement myStmt = null;

			try {
				// prepare statement
				myStmt = myConn.prepareStatement("update course"
						+ " set first_name=?, last_name=?, email=?, salary=?"
						+ " where id=?");
				
				// set params
				myStmt.setString(1, theCourseDB.getFirstName());
				myStmt.setString(2, theCourseDB.getLastName());
				myStmt.setString(3, theCourseDB.getEmail());
				myStmt.setBigDecimal(4, theCourseDB.getSalary());
				myStmt.setInt(5, theCourseDB.getId());
				
				// execute SQL
				myStmt.executeUpdate();			
			}
			finally {
				close(myStmt);
			}
			
		}
		
		public static void addCourseDB(CourseDB theCourseDB) throws Exception {
			PreparedStatement myStmt = null;

			try {
				// prepare statement
				myStmt = myConn.prepareStatement("insert into course"
						+ " (first_name, last_name, email, salary)"
						+ " values (?, ?, ?, ?)");
				
				// set params
				myStmt.setString(1, theCourseDB.getFirstName());
				myStmt.setString(2, theCourseDB.getLastName());
				myStmt.setString(3, theCourseDB.getEmail());
				myStmt.setBigDecimal(4, theCourseDB.getSalary());
				
				// execute SQL
				myStmt.executeUpdate();			
			}
			finally {
				close(myStmt);
			}
			
		}
		
		
		public static List<CourseDB> getAllCourseDB() throws Exception {
			List<CourseDB> list = new ArrayList<>();
			
			Statement myStmt = null;
			ResultSet myRs = null;
			
			try {
				myStmt = myConn.createStatement();
				myRs = myStmt.executeQuery("select * from course order by last_name");
				
				while (myRs.next()) {
					CourseDB tempCourseDB = convertRowToCourseDB(myRs);
					list.add(tempCourseDB);
				}

				return list;		
			}
			finally {
				close(myStmt, myRs);
			}
		}
		
		public static List<CourseDB> searchCourseDB(String lastName) throws Exception {
			List<CourseDB> list = new ArrayList<>();

			PreparedStatement myStmt = null;
			ResultSet myRs = null;

			try {
				lastName += "%";
				myStmt = myConn.prepareStatement("select * from course where last_name like ?  order by last_name");
				
				myStmt.setString(1, lastName);
				
				myRs = myStmt.executeQuery();
				
				while (myRs.next()) {
					CourseDB tempCourseDB = convertRowToCourseDB(myRs);
					list.add(tempCourseDB);
				}
				
				return list;
			}
			finally {
				close(myStmt, myRs);
			}
		}
		
		private static CourseDB convertRowToCourseDB(ResultSet myRs) throws SQLException {
			
			int id = myRs.getInt("id");
			String lastName = myRs.getString("last_name");
			String firstName = myRs.getString("first_name");
			String email = myRs.getString("email");
			BigDecimal salary = myRs.getBigDecimal("salary");
			
			CourseDB tempCourseDB = new CourseDB(id, lastName, firstName, email, salary);
			
			return tempCourseDB;
		}


	
		
		//EnrollmentDB
		public static void deleteEnrollmentDB(int enrollmentDBId) throws SQLException {
			PreparedStatement myStmt = null;

			try {
				// prepare statement
				myStmt = myConn.prepareStatement("delete from enrollment where id=?");
				
				// set param
				myStmt.setInt(1, enrollmentDBId);
				
				// execute SQL
				myStmt.executeUpdate();			
			}
			finally {
				close(myStmt);
			}
		}
		
		public static void updateEnrollmentDB(EnrollmentDB theEnrollmentDB) throws SQLException {
			PreparedStatement myStmt = null;

			try {
				// prepare statement
				myStmt = myConn.prepareStatement("update enrollment"
						+ " set first_name=?, last_name=?, email=?, salary=?"
						+ " where id=?");
				
				// set params
				myStmt.setString(1, theEnrollmentDB.getFirstName());
				myStmt.setString(2, theEnrollmentDB.getLastName());
				myStmt.setString(3, theEnrollmentDB.getEmail());
				myStmt.setBigDecimal(4, theEnrollmentDB.getSalary());
				myStmt.setInt(5, theEnrollmentDB.getId());
				
				// execute SQL
				myStmt.executeUpdate();			
			}
			finally {
				close(myStmt);
			}
			
		}
		
		public static void addEnrollmentDB(EnrollmentDB theEnrollmentDB) throws Exception {
			PreparedStatement myStmt = null;

			try {
				// prepare statement
				myStmt = myConn.prepareStatement("insert into enrollment"
						+ " (first_name, last_name, email, salary)"
						+ " values (?, ?, ?, ?)");
				
				// set params
				myStmt.setString(1, theEnrollmentDB.getFirstName());
				myStmt.setString(2, theEnrollmentDB.getLastName());
				myStmt.setString(3, theEnrollmentDB.getEmail());
				myStmt.setBigDecimal(4, theEnrollmentDB.getSalary());
				
				// execute SQL
				myStmt.executeUpdate();			
			}
			finally {
				close(myStmt);
			}
			
		}
		

		public static List<EnrollmentDB> getAllEnrollmentDB() throws Exception {
			List<EnrollmentDB> list = new ArrayList<>();
			
			Statement myStmt = null;
			ResultSet myRs = null;
			
			try {
				myStmt = myConn.createStatement();
				myRs = myStmt.executeQuery("select * from enrollment order by last_name");
				
				while (myRs.next()) {
					EnrollmentDB tempEnrollmentDB = convertRowToEnrollmentDB(myRs);
					list.add(tempEnrollmentDB);
				}

				return list;		
			}
			finally {
				close(myStmt, myRs);
			}
		}
		
		public static List<EnrollmentDB> searchEnrollmentDB(String lastName) throws Exception {
			List<EnrollmentDB> list = new ArrayList<>();

			PreparedStatement myStmt = null;
			ResultSet myRs = null;

			try {
				lastName += "%";
				myStmt = myConn.prepareStatement("select * from enrollment where last_name like ?  order by last_name");
				
				myStmt.setString(1, lastName);
				
				myRs = myStmt.executeQuery();
				
				while (myRs.next()) {
					EnrollmentDB tempEnrollmentDB = convertRowToEnrollmentDB(myRs);
					list.add(tempEnrollmentDB);
				}
				
				return list;
			}
			finally {
				close(myStmt, myRs);
			}
		}
		
		
		private static EnrollmentDB convertRowToEnrollmentDB(ResultSet myRs) throws SQLException {
			
			int id = myRs.getInt("id");
			String lastName = myRs.getString("last_name");
			String firstName = myRs.getString("first_name");
			String email = myRs.getString("email");
			BigDecimal salary = myRs.getBigDecimal("salary");
			
			EnrollmentDB tempEnrollmentDB = new EnrollmentDB(id, lastName, firstName, email, salary);
			
			return tempEnrollmentDB;
		}

		
		
		
		
		//ProfessorDB
		
		public static void deleteProfessorDB(int profeessordbId) throws SQLException {
			PreparedStatement myStmt = null;

			try {
				// prepare statement
				myStmt = myConn.prepareStatement("delete from professor where id=?");
				
				// set param
				myStmt.setInt(1, profeessordbId);
				
				// execute SQL
				myStmt.executeUpdate();			
			}
			finally {
				close(myStmt);
			}
		}
		
		public static void updateProfessorDB(ProfessorDB theProfessorDB) throws SQLException {
			PreparedStatement myStmt = null;

			try {
				// prepare statement
				myStmt = myConn.prepareStatement("update professor"
						+ " set first_name=?, last_name=?, email=?, salary=?"
						+ " where id=?");
				
				// set params
				myStmt.setString(1, theProfessorDB.getFirstName());
				myStmt.setString(2, theProfessorDB.getLastName());
				myStmt.setString(3, theProfessorDB.getEmail());
				myStmt.setBigDecimal(4, theProfessorDB.getSalary());
				myStmt.setInt(5, theProfessorDB.getId());
				
				// execute SQL
				myStmt.executeUpdate();			
			}
			finally {
				close(myStmt);
			}
			
		}
		
		public static void addProfessorDB(ProfessorDB theProfessorDB) throws Exception {
			PreparedStatement myStmt = null;

			try {
				// prepare statement
				myStmt = myConn.prepareStatement("insert into professor"
						+ " (first_name, last_name, email, salary)"
						+ " values (?, ?, ?, ?)");
				
				// set params
				myStmt.setString(1, theProfessorDB.getFirstName());
				myStmt.setString(2, theProfessorDB.getLastName());
				myStmt.setString(3, theProfessorDB.getEmail());
				myStmt.setBigDecimal(4, theProfessorDB.getSalary());
				
				// execute SQL
				myStmt.executeUpdate();			
			}
			finally {
				close(myStmt);
			}
			
		}
		

		public static List<ProfessorDB> getAllProfessorDB() throws Exception {
			List<ProfessorDB> list = new ArrayList<>();
			
			Statement myStmt = null;
			ResultSet myRs = null;
			
			try {
				myStmt = myConn.createStatement();
				myRs = myStmt.executeQuery("select * from professor order by last_name");
				
				while (myRs.next()) {
					ProfessorDB tempProfessorDB = convertRowToProfessorDB(myRs);
					list.add(tempProfessorDB);
				}

				return list;		
			}
			finally {
				close(myStmt, myRs);
			}
		}
		
		public static List<ProfessorDB> searchProfessorDB(String lastName) throws Exception {
			List<ProfessorDB> list = new ArrayList<>();

			PreparedStatement myStmt = null;
			ResultSet myRs = null;

			try {
				lastName += "%";
				myStmt = myConn.prepareStatement("select * from professor where last_name like ?  order by last_name");
				
				myStmt.setString(1, lastName);
				
				myRs = myStmt.executeQuery();
				
				while (myRs.next()) {
					ProfessorDB tempProfessorDB = convertRowToProfessorDB(myRs);
					list.add(tempProfessorDB);
				}
				
				return list;
			}
			finally {
				close(myStmt, myRs);
			}
		}
		
		private static ProfessorDB convertRowToProfessorDB(ResultSet myRs) throws SQLException {
			
			int id = myRs.getInt("id");
			String lastName = myRs.getString("last_name");
			String firstName = myRs.getString("first_name");
			String email = myRs.getString("email");
			BigDecimal salary = myRs.getBigDecimal("salary");
			
			ProfessorDB tempProfessorDB = new ProfessorDB(id, lastName, firstName, email, salary);
			
			return tempProfessorDB;
		}


		//DepartmentDB
		
		public static void deleteDepartmentDB(int id) throws SQLException {
			PreparedStatement myStmt = null;

			try {
				// prepare statement
				myStmt = myConn.prepareStatement("delete from department where id=?");
				
				// set param
				myStmt.setInt(1, id);
				
				// execute SQL
				myStmt.executeUpdate();			
			}
			finally {
				close(myStmt);
			}
		}
		
		public static void updateDepatmentDB(DepartmentDB theDepartmentDB) throws SQLException {
			PreparedStatement myStmt = null;

			try {
				// prepare statement
				myStmt = myConn.prepareStatement("update department"
						+ " set first_name=?, last_name=?, email=?, salary=?"
						+ " where id=?");
				
				// set params
				myStmt.setString(1, theDepartmentDB.getFirstName());
				myStmt.setString(2, theDepartmentDB.getLastName());
				myStmt.setString(3, theDepartmentDB.getEmail());
				myStmt.setBigDecimal(4, theDepartmentDB.getSalary());
				myStmt.setInt(5, theDepartmentDB.getId());
				
				// execute SQL
				myStmt.executeUpdate();			
			}
			finally {
				close(myStmt);
			}
			
		}
		
		public static void addDepartmentDB(DepartmentDB theDepartmentDB) throws Exception {
			PreparedStatement myStmt = null;

			try {
				// prepare statement
				myStmt = myConn.prepareStatement("insert into department"
						+ " (first_name, last_name, email, salary)"
						+ " values (?, ?, ?, ?)");
				
				// set params
				myStmt.setString(1, theDepartmentDB.getFirstName());
				myStmt.setString(2, theDepartmentDB.getLastName());
				myStmt.setString(3, theDepartmentDB.getEmail());
				myStmt.setBigDecimal(4, theDepartmentDB.getSalary());
				
				// execute SQL
				myStmt.executeUpdate();			
			}
			finally {
				close(myStmt);
			}
			
		}
		

		public static List<DepartmentDB> getAllDepartmentDB() throws Exception {
			List<DepartmentDB> list = new ArrayList<>();
			
			Statement myStmt = null;
			ResultSet myRs = null;
			
			try {
				myStmt = myConn.createStatement();
				myRs = myStmt.executeQuery("select * from department order by last_name");
				
				while (myRs.next()) {
					DepartmentDB tempDepartmentDB = convertRowToDepatmentDB(myRs);
					list.add(tempDepartmentDB);
				}

				return list;		
			}
			finally {
				close(myStmt, myRs);
			}
		}
		
		public static List<DepartmentDB> searchDepartmentDB(String lastName) throws Exception {
			List<DepartmentDB> list = new ArrayList<>();

			PreparedStatement myStmt = null;
			ResultSet myRs = null;

			try {
				lastName += "%";
				myStmt = myConn.prepareStatement("select * from department where last_name like ?  order by last_name");
				
				myStmt.setString(1, lastName);
				
				myRs = myStmt.executeQuery();
				
				while (myRs.next()) {
					DepartmentDB tempDepartmentDB = convertRowToDepatmentDB(myRs);
					list.add(tempDepartmentDB);
				}
				
				return list;
			}
			finally {
				close(myStmt, myRs);
			}
		}
		
		private static DepartmentDB convertRowToDepatmentDB(ResultSet myRs) throws SQLException {
			
			int id = myRs.getInt("id");
			String lastName = myRs.getString("last_name");
			String firstName = myRs.getString("first_name");
			String email = myRs.getString("email");
			BigDecimal salary = myRs.getBigDecimal("salary");
			
			DepartmentDB tempDepartmentDB = new DepartmentDB(id, lastName, firstName, email, salary);
			
			return tempDepartmentDB;
		}

		
		
		
	
	
	
	
	
	
	
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
			throws SQLException {

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			
		}
		
		if (myConn != null) {
			myConn.close();
		}
	}

	private static void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);		
	}

	private static void close(Statement myStmt) throws SQLException {
		close(null, myStmt, null);		
	}
	
	public static void main(String[] args) throws Exception {
		
		EmployeeDAO dao = new EmployeeDAO();
		System.out.println(dao.searchEmployees("thom"));

		System.out.println(dao.getAllEmployees());
	}

}
