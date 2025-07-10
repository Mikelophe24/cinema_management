package Enum;

public class SeatScheduleEnum {
	public enum Status {
		AVAILABLE("available"), SOLD("sold");

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
			for (SeatScheduleEnum.Status status : SeatScheduleEnum.Status.values()) {
				if (status.getValue().equalsIgnoreCase(value)) {
					return true;
				}
			}
			return false;
		}

	}
}
