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
}
