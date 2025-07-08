package Model;

import java.time.LocalDate;

public class User {
	protected int id;
	protected int accountId;
	protected String fullName;
	protected String email;
	protected String phoneNumber;
	protected String address;
	protected LocalDate birthday;
	protected int gender;

	public User() {

	}

	public User(int id, int accountId, String fullName, String email, String phoneNumber, String address,
			LocalDate birthday, int gender) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.fullName = fullName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.birthday = birthday;
		this.gender = gender;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

//	@Override
//	public String toString() {
//		return "User [id=" + id + ", accountId=" + accountId + ", fullName=" + fullName + ", email=" + email
//				+ ", phoneNumber=" + phoneNumber + ", address=" + address + ", birthday=" + birthday + ", gender="
//				+ gender + "]";
//	}
}
