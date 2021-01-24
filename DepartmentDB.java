package core;


import java.math.BigDecimal;

public class DepartmentDB
{

	private int id;
	private String lastName;
	private String firstName;
	private String email;
	private BigDecimal salary;
	

	public DepartmentDB(String lastName, String firstName, String email,
			BigDecimal salary) {

		this(0, lastName, firstName, email, salary);
	}
	
	public DepartmentDB(int id, String lastName, String firstName, String email,
			BigDecimal salary) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.salary = salary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	
	public String toString() {
		return String
				.format("DepartmentDB [id=%s, lastName=%s, firstName=%s, email=%s, salary=%s]",
						id, lastName, firstName, email, salary);
	}
	
	
		
}

