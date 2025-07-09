package Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.MappingDBData;
import util.MyConnection;

public class DatabaseExecutor {

	public static <T> T queryOne(String sql, Class<T> clazz, Object... params) {
		try (Connection conn = MyConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			setParams(stmt, params);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return MappingDBData.mapResultSetToObject(rs, clazz);
				}
			}
		} catch (Exception e) {
			handleException(e, sql);
		}
		return null;
	}

	public static <T> List<T> queryList(String sql, Class<T> clazz, Object... params) {
		List<T> list = new ArrayList<>();
		try (Connection conn = MyConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			setParams(stmt, params);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					list.add(MappingDBData.mapResultSetToObject(rs, clazz));
				}
			}
		} catch (Exception e) {
			handleException(e, sql);
		}
		return list;
	}

	public static int update(String sql, Object... params) {
		try (Connection conn = MyConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			setParams(stmt, params);
			return stmt.executeUpdate();
		} catch (Exception e) {
			handleException(e, sql);
		}
		return 0;
	}

	public static long insert(String sql, Object... params) {
		try (Connection conn = MyConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			setParams(stmt, params);
			int affectedRows = stmt.executeUpdate();

			if (affectedRows == 0) {
				return -1;
			}

			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getLong(1);
				} else {
					return 0;
				}
			}

		} catch (Exception e) {
			handleException(e, sql);
		}
		return -1;
	}

	public static int delete(String sql, Object... params) {
		return update(sql, params);
	}

	public static int count(String sql, Object... params) {
		try (Connection conn = MyConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			setParams(stmt, params);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (Exception e) {
			handleException(e, sql);
		}
		return 0;
	}

	public static boolean exists(String sql, Object... params) {
		try (Connection conn = MyConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			setParams(stmt, params);
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next();
			}
		} catch (Exception e) {
			handleException(e, sql);
		}
		return false;
	}

	public static int[] executeBatch(String sql, List<Object[]> batchParams) {
		try (Connection conn = MyConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			for (Object[] params : batchParams) {
				setParams(stmt, params);
				stmt.addBatch();
			}
			return stmt.executeBatch();
		} catch (Exception e) {
			handleException(e, sql);
		}
		return new int[0];
	}

	public static boolean execute(String sql, Object... params) {
		try (Connection conn = MyConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			setParams(stmt, params);
			return stmt.execute();
		} catch (Exception e) {
			handleException(e, sql);
		}
		return false;
	}

	private static void setParams(PreparedStatement stmt, Object... params) throws SQLException {
		if (params == null)
			return;
		for (int i = 0; i < params.length; i++) {
			stmt.setObject(i + 1, params[i]);
		}
	}

	private static void handleException(Exception e, String sql) {
		System.err.println("SQL Error with query: " + sql);
		e.printStackTrace();
	}
}
