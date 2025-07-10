package Dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import Enum.AccountEnum;
import Helper.DatabaseExecutor;
import Model.Account;
import Model.Customer;
import Model.Employee;

public class AccountDao {
	// SQL
	private static final String SQL_GET_ALL = "SELECT * FROM accounts";
	private static final String SQL_GET_BY_USERNAME = "SELECT * FROM accounts WHERE username = ?";
	private static final String SQL_CREATE = "INSERT INTO accounts (username, password, role, status, display_name, avatar) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM accounts WHERE id = ?";
	static final String SQL_CHECK_EXIST_BY_ID = "SELECT 1 FROM accounts WHERE id = ?";
	static final String SQL_CHECK_EXIST_BY_USERNAME = "SELECT 1 FROM accounts WHERE username = ?";
	// Constants
	private static final String UPDATED_FIELDS[] = { "password", "role", "avatar", "display_name", "status",
			"username" };

	public static Account login(String username, String password) {
		try {
			Account account = DatabaseExecutor.queryOne(SQL_GET_BY_USERNAME, Account.class, username);
			return account;
		} catch (Exception e) {
			throw new RuntimeException("Login failed. Please try again");
		}
	}

	public static Account register(String username, String password, String displayName, String avatar,
			AccountEnum.Role role) {
		boolean isExist = DatabaseExecutor.exists(SQL_CHECK_EXIST_BY_USERNAME, username);
		if (isExist) {
			throw new RuntimeException("Username has exist");
		}
//		AccountEnum.Role accountRole = AccountEnum.Role.CUSTOMER;
//		if (AccountEnum.Role.isValidRole(role)) {
//			accountRole = AccountEnum.Role.fromValue(role);
//		} else {
//			throw new RuntimeException("Invalid Role");
//		}
		if (role == null || !AccountEnum.Role.isValidRole(role.getValue())) {
			throw new RuntimeException("Invalid Role");
		}

		long rows = DatabaseExecutor.insert(SQL_CREATE, username, password, role.getValue(),
				AccountEnum.Status.ACTIVE.getValue(), displayName, avatar);

		if (rows > 0) {
			Account acc = DatabaseExecutor.queryOne(SQL_GET_BY_USERNAME, Account.class, username);
			if (role == AccountEnum.Role.CUSTOMER) {
				UserDao<Customer> customerDao = new UserDao<>(role);
				customerDao.create(acc.getAccountId(), null, null, null, null, null, 0, null);
			} else if (role == AccountEnum.Role.EMPLOYEE) {
				UserDao<Employee> employeeDao = new UserDao<>(role);
				employeeDao.create(acc.getAccountId(), null, null, null, null, null, 0, LocalDate.now());
			}
			return acc;
		}

		return null;
	}

	public static List<Account> getAll() {
		List<Account> list = new ArrayList<>();
		list = DatabaseExecutor.queryList(SQL_GET_ALL, Account.class);
		return list;
	}

	public static boolean update(int id, Map<String, Object> updateFields) {
		if (updateFields == null || updateFields.isEmpty()) {
			throw new IllegalArgumentException("No fields update");
		}

		boolean isExist = DatabaseExecutor.exists(SQL_CHECK_EXIST_BY_ID, id);
		if (!isExist) {
			throw new RuntimeException("Account not found");
		}

		StringBuilder sql = new StringBuilder("UPDATE accounts SET ");
		List<Object> params = new ArrayList<>();
		int count = 0;

		for (Map.Entry<String, Object> entry : updateFields.entrySet()) {
			String key = entry.getKey();

			boolean isAllowed = Arrays.stream(UPDATED_FIELDS).anyMatch(field -> field.equalsIgnoreCase(key));
			if (!isAllowed) {
				throw new RuntimeException("Fields " + key + " not allow");
			}

			if (count > 0) {
				sql.append(", ");
			}
			sql.append(key).append(" = ?");
			params.add(entry.getValue());
			count++;
		}

		if (count == 0) {
			return false;
		}

		sql.append(" WHERE id = ?");
		params.add(id);

		System.out.println("Update SQL: " + sql.toString());
		System.out.println("Update Params: " + params);
		int affectedRows = DatabaseExecutor.update(sql.toString(), params.toArray());
		System.out.println("Affected rows: " + affectedRows);
		return affectedRows > 0;
	}

	public static boolean delete(int id) {
		if (id < 0) {
			return false;
		}
		boolean isExist = DatabaseExecutor.exists(SQL_CHECK_EXIST_BY_ID, id);
		if (!isExist) {
			throw new RuntimeException("Account not found");
		}
		return DatabaseExecutor.delete(SQL_DELETE_BY_ID, id) > 0;
	}

	// Main test
	public static void main(String[] args) {
		// Register
//		try {
//			Account account = AccountDao.register("test1", "123456", "Test User", null, AccountEnum.Role.CUSTOMER);
//
//			if (account != null) {
//				System.out.println("Register OK: " + account.getUsername());
//			} else {
//				System.out.println("Register failed");
//			}
//		} catch (Exception e) {
//			System.err.println("Register Error: " + e.getMessage());
//			e.printStackTrace();
//		}

		// Update
//		try {
//			Map<String, Object> updateFields = new HashMap<>();
//			updateFields.put("display_name", "testUpdateAccount");
//			updateFields.put("status", AccountEnum.Status.BANNED.getValue());
//			updateFields.put("avatar", null);
//			updateFields.put("username", "aaaaa");
//
//			accountDao.update(4, updateFields);
//			System.out.println("Upadate OK");
//		} catch (Exception e) {
//			System.err.println("Upadate Error: " + e.getMessage());
//			e.printStackTrace();
//		}

		// Delete
//		try {
//			AccountDao.delete(3);
//			System.out.println("Delete OK");
//		} catch (Exception e) {
//			System.err.println("Delete Error: " + e.getMessage());
//			e.printStackTrace();
//		}

		// Get All
//		try {
//			List<Account> accounts = AccountDao.getAll();
//			System.out.println(accounts);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
	}
}
