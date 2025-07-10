package util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
					String fieldName = toCamelCase(column);
					Field field = getField(classType, fieldName);
					field.setAccessible(true);
					Class<?> fieldType = field.getType();

					if (fieldType.isEnum()) {
						Method fromValueMethod = fieldType.getMethod("fromValue", String.class);
						Object enumValue = fromValueMethod.invoke(null, value.toString());
						field.set(instance, enumValue);
					} else if (fieldType.equals(LocalDateTime.class) && value instanceof Timestamp) {
						field.set(instance, ((Timestamp) value).toLocalDateTime());
					} else if (fieldType.equals(LocalDate.class) && value instanceof java.sql.Date) {
						field.set(instance, ((java.sql.Date) value).toLocalDate());
					} else if (fieldType.equals(LocalTime.class) && value instanceof java.sql.Time) {
						field.set(instance, ((java.sql.Time) value).toLocalTime());
					} else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
						field.set(instance, Boolean.parseBoolean(value.toString()));
					} else if (fieldType.equals(Double.class) && value instanceof BigDecimal) {
						field.set(instance, ((BigDecimal) value).doubleValue());
					} else if (fieldType.equals(Float.class) && value instanceof BigDecimal) {
						field.set(instance, ((BigDecimal) value).floatValue());
					} else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
						if (value instanceof Number) {
							field.set(instance, ((Number) value).intValue());
						}
					} else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
						if (value instanceof Number) {
							field.set(instance, ((Number) value).longValue());
						}
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
				sb.append(Character.toLowerCase(c));
			}
		}
		return sb.toString();
	}

	private static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
		Class<?> current = clazz;
		while (current != null) {
			try {
				return current.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				current = current.getSuperclass();
			}
		}
		throw new NoSuchFieldException("Field '" + fieldName + "' not found in class hierarchy.");
	}
}
