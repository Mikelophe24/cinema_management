package Enum;

public class AccountEnum {

	public enum Role {
		ADMIN("admin"), CUSTOMER("customer"), EMPLOYEE("employee");

		private final String value;

		Role(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Role fromValue(String value) {
			for (Role role : Role.values()) {
				if (role.getValue().equalsIgnoreCase(value)) {
					return role;
				}
			}
			throw new IllegalArgumentException("No enum constant Role with value: " + value);
		}

		public static boolean isValidRole(String value) {
			if (value == "" || value == null)
				return false;
			for (AccountEnum.Role role : AccountEnum.Role.values()) {
				if (role.getValue().equalsIgnoreCase(value)) {
					return true;
				}
			}
			return false;
		}

	}

	public enum Status {
		ACTIVE("active"), INACTIVE("inactive"), BANNED("banned");

		private final String value;

		Status(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Status fromValue(String value) {
			for (Status status : Status.values()) {
				if (status.getValue().equalsIgnoreCase(value)) {
					return status;
				}
			}
			throw new IllegalArgumentException("No enum constant Status with value: " + value);
		}
	}
}
