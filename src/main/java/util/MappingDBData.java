package util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class MappingDBData {
	public static <T> T mapResultSetToObject(ResultSet rs, Class<T> classType) {
		try {
			T instance = classType.getDeclaredConstructor().newInstance();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			for (int i = 1; i <= columnCount; i++) {
				String column = metaData.getColumnLabel(i);
				Object value = rs.getObject(i);

				if (value == null)
					continue;

				try {
					Field field = classType.getDeclaredField(toCamelCase(column));
					field.setAccessible(true);
					Class<?> fieldType = field.getType();

					if (fieldType.isEnum()) {
						Method fromValueMethod = fieldType.getMethod("fromValue", String.class);
						Object enumValue = fromValueMethod.invoke(null, value.toString());
						field.set(instance, enumValue);
					} else if (fieldType.equals(LocalDateTime.class) && value instanceof Timestamp) {
						field.set(instance, ((Timestamp) value).toLocalDateTime());
					} else {
						field.set(instance, value);
					}
				} catch (NoSuchFieldException ignored) {
				}
			}
			return instance;
		} catch (Exception e) {
			throw new RuntimeException("Failed to map ResultSet to " + classType.getSimpleName(), e);
		}
	}

	private static String toCamelCase(String input) {
		StringBuilder sb = new StringBuilder();
		boolean nextUpper = false;
		for (char c : input.toCharArray()) {
			if (c == '_') {
				nextUpper = true;
			} else if (nextUpper) {
				sb.append(Character.toUpperCase(c));
				nextUpper = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
