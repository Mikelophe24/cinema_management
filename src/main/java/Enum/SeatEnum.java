package Enum;

public class SeatEnum {
	public enum Type {
		NORMAL("normal"), VIP("vip"), COUPLE("couple");

		private final String value;

		Type(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Type fromValue(String value) {
			for (Type type : Type.values()) {
				if (type.getValue().equalsIgnoreCase(value)) {
					return type;
				}
			}
			throw new IllegalArgumentException("No enum constant Type with value: " + value);
		}

		public static boolean isValidStatus(String value) {
			if (value == "" || value == null)
				return false;
			for (SeatEnum.Type type : SeatEnum.Type.values()) {
				if (type.getValue().equalsIgnoreCase(value)) {
					return true;
				}
			}
			return false;
		}

	}
}
