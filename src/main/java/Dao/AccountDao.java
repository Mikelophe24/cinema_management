package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Model.Account;
import util.Bcrypt;
import util.MappingDBData;
import util.MyConnection;

public class AccountDao {
	private static final String SQL_GET_ACCOUNT_BY_USERNAME = "SELECT * FROM accounts WHERE username = ?";

	public Account login(String username, String password) {
		System.out.println(password);
		System.out.println(Bcrypt.hash(password));

		try (Connection conn = MyConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_GET_ACCOUNT_BY_USERNAME)) {
			stmt.setString(1, username);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Account account = MappingDBData.mapResultSetToObject(rs, Account.class);
					String hashPassword = account.getPassword();
					if (Bcrypt.compare(password, hashPassword)) {
						System.out.println("Accout Login: " + account);
						return account;
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Lỗi khi đăng nhập tài khoản:");
			e.printStackTrace();
		}
		return null;
	}
}
