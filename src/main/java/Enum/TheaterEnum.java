package Enum;

public class TheaterEnum {

	public enum Status {
		ACTIVE("active"), CLOSED("closed"), MAINTENANCE("maintenance");

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

		public static boolean isValidStatus(String value) {
			if (value == "" || value == null)
				return false;
			for (TheaterEnum.Status role : TheaterEnum.Status.values()) {
				if (role.getValue().equalsIgnoreCase(value)) {
					return true;
				}
			}
			return false;
		}

	}
}
