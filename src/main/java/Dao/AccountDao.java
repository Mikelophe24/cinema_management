package Dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import Enum.AccountEnum;
import Helper.DatabaseExecutor;
import Model.Account;
import util.Bcrypt;

public class AccountDao {
	// SQL
	private static final String SQL_GET_BY_USERNAME = "SELECT * FROM accounts WHERE username = ?";
	private static final String SQL_CREATE_BY_ID = "INSERT INTO accounts (username, password, role, status, display_name, avatar) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM accounts WHERE id = ?";
	private static final String SQL_CHECK_EXIST_BY_ID = "SELECT 1 FROM accounts WHERE id = ?";
	private static final String SQL_CHECK_EXIST_BY_USERNAME = "SELECT 1 FROM accounts WHERE username = ?";
	// Constants
	private static final String UPDATED_FIELDS[] = { "password", "role", "avatar", "display_name", "status" };

	public Account login(String username, String password) {
		Account account = DatabaseExecutor.queryOne(SQL_GET_BY_USERNAME, Account.class, username);
		return account;
	}

	public Account register(String username, String password, String displayName, String avatar, String role) {
		boolean isExist = DatabaseExecutor.exists(SQL_CHECK_EXIST_BY_USERNAME, username);
		if (isExist) {
			throw new RuntimeException("Username has exist");
		}

		AccountEnum.Role accountRole = AccountEnum.Role.CUSTOMER;
		if (AccountEnum.Role.isValidRole(role)) {
			accountRole = AccountEnum.Role.fromValue(role);
		} else {
			throw new RuntimeException("Invalid Role");
		}

		Account newAccount = new Account();
		newAccount.setUsername(username);
		newAccount.setPassword(Bcrypt.hash(password));
		newAccount.setRole(accountRole);
		newAccount.setStatus(AccountEnum.Status.ACTIVE);
		newAccount.setDisplayName(displayName);
		newAccount.setAvatar(null);

		int rows = DatabaseExecutor.update(SQL_CREATE_BY_ID, newAccount.getUsername(), newAccount.getPassword(),
				newAccount.getRole().name(), newAccount.getStatus().name(), newAccount.getDisplayName(),
				newAccount.getAvatar());

		if (rows > 0) {
			return DatabaseExecutor.queryOne(SQL_GET_BY_USERNAME, Account.class, username);
		}

		return null;
	}

	public boolean update(int id, Map<String, Object> updateFields) {
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

//		int affectedRows = DatabaseExecutor.update(sql.toString(), params.toArray());
		return DatabaseExecutor.update(sql.toString(), params.toArray()) > 0;
	}

	public boolean delete(int id) {
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
		AccountDao accountDao = new AccountDao();

		// Register
//		try {
//			Account account = accountDao.register("test", "123456", "Test User", null,
//					AccountEnum.Role.CUSTOMER.getValue());
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
		try {
			accountDao.delete(4);
			System.out.println("Delete OK");
		} catch (Exception e) {
			System.err.println("Delete Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
