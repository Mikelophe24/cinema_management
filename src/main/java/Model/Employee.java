package Model;

import java.time.LocalDate;

public class Employee extends User {
	private LocalDate hireDate;

	public Employee() {
		super();
	}

	public Employee(int id, int accountId, String fullName, String email, String phoneNumber, String address,
			LocalDate birthday, int gender, LocalDate hireDate) {
		super(id, accountId, fullName, email, phoneNumber, address, birthday, gender);
		this.hireDate = hireDate;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	@Override
	public String toString() {
		return "Employee{" + "id=" + id + ", accountId=" + accountId + ", fullName='" + fullName + '\'' + ", email='"
				+ email + '\'' + ", phoneNumber='" + phoneNumber + '\'' + ", address='" + address + '\'' + ", birthday="
				+ birthday + ", gender=" + gender + ", hireDate=" + hireDate + '}';
	}
}
