package Dao;

import java.time.LocalDate;

import Enum.AccountEnum;
import Helper.DatabaseExecutor;
import Model.Customer;
import Model.Employee;

public class UserDao<T> {
	private AccountEnum.Role role;
	private String table;
	private Class<T> clazz;

	@SuppressWarnings("unchecked")
	public UserDao(AccountEnum.Role role) {
		if (!AccountEnum.Role.isValidRole(role.getValue())) {
			throw new RuntimeException("Invalid Role");
		}
		this.role = role;
		this.table = role.getValue() + "s";

		switch (role) {
		case EMPLOYEE:
			this.clazz = (Class<T>) Employee.class;
			break;
		case CUSTOMER:
			this.clazz = (Class<T>) Customer.class;
			break;
		default:
			throw new RuntimeException("Invalid Role");
		}
	}

	public T create(int accountId, String fullName, String email, String phoneNumber, String address,
			LocalDate birthday, int gender, LocalDate hireDate) {

		boolean isExistAccount = DatabaseExecutor.exists(AccountDao.SQL_CHECK_EXIST_BY_ID, accountId);
		if (!isExistAccount) {
			throw new RuntimeException("Account not exist");
		}

		String SQL_CHECK_EXIST_BY_ACCOUNT_ID = "SELECT 1 FROM " + this.table + " WHERE id = ?";

		boolean isExistUser = DatabaseExecutor.exists(SQL_CHECK_EXIST_BY_ACCOUNT_ID, accountId);
		if (isExistUser) {
			throw new RuntimeException(this.role.getValue().toUpperCase() + " has exist");
		}

		boolean isEmployee = this.role == AccountEnum.Role.EMPLOYEE;

		String sqlCreate = "INSERT INTO " + this.table
				+ " (account_id, full_name, email, phone_number, address, birthday, gender";
		String sqlQueryOne = "SELECT * FROM " + this.table + " WHERE account_id = ?";

		if (isEmployee) {
			sqlCreate += ", hire_date)";
		} else {
			sqlCreate += ")";
		}

		sqlCreate += " VALUES (?, ?, ?, ?, ?, ?, ?";
		if (isEmployee) {
			sqlCreate += ", ?)";
		} else {
			sqlCreate += ")";
		}

		long rows;
		if (isEmployee) {
			rows = DatabaseExecutor.insert(sqlCreate, accountId, fullName, email, phoneNumber, address, birthday,
					gender, hireDate);
		} else {
			rows = DatabaseExecutor.insert(sqlCreate, accountId, fullName, email, phoneNumber, address, birthday,
					gender);
		}

		if (rows > 0) {
			return DatabaseExecutor.queryOne(sqlQueryOne, clazz, accountId);
		}

		return null;
	}

	public static void main(String[] args) {
		try {
			UserDao<Employee> dao = new UserDao<>(AccountEnum.Role.EMPLOYEE);
			Employee emp = dao.create(3, "Nguyễn Văn A", "a@example.com", "0123456789", "123 Đường ABC",
					LocalDate.of(2000, 1, 1), 1, LocalDate.now());
			System.out.println(emp);
		} catch (Exception e) {
			System.err.println("UserDao Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
