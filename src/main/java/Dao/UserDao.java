package Dao;

import Enum.AccountEnum;

public class UserDao {
	private AccountEnum.Role role;
	private String table;

	public UserDao(AccountEnum.Role role) {
		this.role = role;
		this.table = role.getValue() + "s";
	}
}
