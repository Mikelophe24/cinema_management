package Model;

import java.time.LocalDate;

public class Customer extends User {

	public Customer() {
		super();
	}

	public Customer(int id, int accountId, String fullName, String email, String phoneNumber, String address,
					LocalDate birthday, int gender) {
		super(id, accountId, fullName, email, phoneNumber, address, birthday, gender);
	}

	// Nếu các thuộc tính nằm trong lớp cha User thì bạn cần bổ sung getter tại đây (nếu lớp cha không có)
	public int getId() {
		return super.getId();
	}

	public int getAccountId() {
		return super.getAccountId();
	}

	public String getFullName() {
		return super.getFullName();
	}

	public String getEmail() {
		return super.getEmail();
	}

	public String getPhoneNumber() {
		return super.getPhoneNumber();
	}

	public String getAddress() {
		return super.getAddress();
	}

	public LocalDate getBirthday() {
		return super.getBirthday();
	}

	public int getGender() {
		return super.getGender();
	}
}
