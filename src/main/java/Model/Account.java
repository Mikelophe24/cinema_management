package Model;

import java.time.LocalDateTime;

import Enum.AccountEnum;
import Enum.AccountEnum.Role;
import Enum.AccountEnum.Status;

public class Account {
	private int id;
	private String username;
	private String password;
	private AccountEnum.Role type;
	private AccountEnum.Status status;
	private String display_name;
	private String avatar;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Account() {
	}

	public Account(int accountId, String username, String password, Role type, Status status, String display_name,
			String avatar, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = accountId;
		this.username = username;
		this.password = password;
		this.type = type;
		this.status = status;
		this.display_name = display_name;
		this.avatar = avatar;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public int getAccountId() {
		return id;
	}

	public void setAccountId(int accountId) {
		this.id = accountId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AccountEnum.Role getType() {
		return type;
	}

	public void setType(AccountEnum.Role type) {
		this.type = type;
	}

	public AccountEnum.Status getStatus() {
		return status;
	}

	public void setStatus(AccountEnum.Status status) {
		this.status = status;
	}

	public String getDisplayName() {
		return display_name;
	}

	public void setDisplayName(String display_name) {
		this.display_name = display_name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "AccountModel [id=" + id + ", username=" + username + ", password=" + password + ", type=" + type
				+ ", status=" + status + ", display_name=" + display_name + ", avatar=" + avatar + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
