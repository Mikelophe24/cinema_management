package Dao;

import Enum.AccountEnum;
import Helper.DatabaseExecutor;
import Model.Account;
import util.Bcrypt;

public class AccountDao {
	private static final String SQL_GET_ACCOUNT_BY_USERNAME = "SELECT * FROM accounts WHERE username = ?";
	private static final String SQL_CREATE_ACCOUNT_BY_USERNAME = "INSERT INTO accounts (username, password, role, status, display_name, avatar) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";

	public Account login(String username, String password) {
		Account account = DatabaseExecutor.queryOne(SQL_GET_ACCOUNT_BY_USERNAME, Account.class, username);
		System.out.println("Account: " + account);
		return account;
	}

	public Account register(String username, String password, String displayName, String avatar, String role) {
		Account existing = DatabaseExecutor.queryOne(SQL_GET_ACCOUNT_BY_USERNAME, Account.class, username);
		if (existing != null) {
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

		int rows = DatabaseExecutor.update(SQL_CREATE_ACCOUNT_BY_USERNAME, newAccount.getUsername(),
				newAccount.getPassword(), newAccount.getRole().name(), newAccount.getStatus().name(),
				newAccount.getDisplayName(), newAccount.getAvatar());

		if (rows > 0) {
			return DatabaseExecutor.queryOne(SQL_GET_ACCOUNT_BY_USERNAME, Account.class, username);
		}

		return null;
	}

	public static void main(String[] args) {
		AccountDao accountDao = new AccountDao();

		// Test register no View
		try {
			Account account = accountDao.register("test", "123456", "Test User", null,
					AccountEnum.Role.CUSTOMER.getValue());

			if (account != null) {
				System.out.println("Register OK: " + account.getUsername());
			} else {
				System.out.println("Register failed");
			}
		} catch (Exception e) {
			System.err.println("Register Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
