package Dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import Helper.DatabaseExecutor;
import Model.MovieGenre;

public class MovieGenreDao {
	// Common SQL
	private static final String SQL_CREATE = "INSERT INTO genres(name, description) VALUES (?, ?)";
	private static final String SQL_CHECK_UNIQUE_NAME = "SELECT 1 FROM genres WHERE name = ?";
	static final String SQL_QUERY_ONE = "SELECT * FROM genres WHERE id = ?";
	private static final String SQL_QUERY_LIST = "SELECT * FROM genres";
	private static final String SQL_CHECK_EXIST_BY_ID = "SELECT 1 FROM genres WHERE id = ?";
	private static final String SQL_DELETE_BY_ACCOUNT_ID = "DELETE FROM genres WHERE id = ?";

	// Constants
	private static final String UPDATED_FIELDS[] = { "name", "description" };

	public static MovieGenre create(String name, String description) {

		boolean isExist = DatabaseExecutor.exists(SQL_CHECK_UNIQUE_NAME, name);
		if (isExist) {
			throw new RuntimeException("Gerne has exist");
		}

		long id = DatabaseExecutor.insert(SQL_CREATE, name, description);

		if (id > 0) {
			return DatabaseExecutor.queryOne(SQL_QUERY_ONE, MovieGenre.class, id);
		}

		return null;
	}

	public static MovieGenre queryOne(int id) {
		return DatabaseExecutor.queryOne(SQL_QUERY_ONE, MovieGenre.class, id);
	}

	public static List<MovieGenre> queryList() {
		return DatabaseExecutor.queryList(SQL_QUERY_LIST, MovieGenre.class);
	}

	public static boolean update(int id, Map<String, Object> updateFields) {
		if (updateFields == null || updateFields.isEmpty()) {
			throw new IllegalArgumentException("No fields to update");
		}

		StringBuilder sql = new StringBuilder("UPDATE genres SET ");
		List<Object> params = new ArrayList<>();
		int count = 0;

		for (Map.Entry<String, Object> entry : updateFields.entrySet()) {
			String key = entry.getKey();

			boolean isAllowed = Arrays.stream(UPDATED_FIELDS).anyMatch(field -> field.equalsIgnoreCase(key));
			if (!isAllowed) {
				throw new RuntimeException("Field " + key + " not allowed");
			}

			if (count > 0) {
				sql.append(", ");
			}
			sql.append(key).append(" = ?");
			params.add(entry.getValue());
			count++;
		}

		if (count == 0) {
			return false;
		}

		boolean isExist = DatabaseExecutor.exists(SQL_CHECK_EXIST_BY_ID, id);
		if (!isExist) {
			throw new RuntimeException("Genre not found");
		}

		sql.append(" WHERE id = ?");
		params.add(id);

		return DatabaseExecutor.update(sql.toString(), params.toArray()) > 0;
	}

	public static boolean delete(int id) {
		if (id < 0) {
			return false;
		}
		boolean isExist = DatabaseExecutor.exists(SQL_CHECK_EXIST_BY_ID, id);
		if (!isExist) {
			throw new RuntimeException("Genre not found");
		}
		return DatabaseExecutor.delete(SQL_DELETE_BY_ACCOUNT_ID, id) > 0;
	}

	public static void main(String[] args) {
		try {
//			 Create 
			MovieGenre gerne = MovieGenreDao.create("Comedy1", "Phim hài hước, gây cười");
			System.out.println("Created: " + gerne);

			// Get All Customers
			List<MovieGenre> genres = MovieGenreDao.queryList();
			System.out.println("All: " + genres);

			// Update
//			Map<String, Object> updateFields = new HashMap<>();
//			updateFields.put("name1", "Updated Name");
//			updateFields.put("description", "Updated Desc");
//
//			boolean updated = MovieGenreDao.update(2, updateFields);
//			if (updated) {
//				System.out.println("Update OK");
//			} else {
//				System.out.println("Update failed");
//			}

			// Delete
//			boolean deleted = MovieGenreDao.delete(1);
//			if (deleted) {
//				System.out.println("Delete OK");
//			} else {
//				System.out.println("Delete failed");
//			}

		} catch (Exception e) {
			System.err.println("UserDao Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}