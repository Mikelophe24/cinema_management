package Dao;

import java.util.List;

import Helper.DatabaseExecutor;
import Model.Product;

public class ProductDao {
	// Common SQL
	private static final String SQL_CREATE = "INSERT INTO products(name, description, image, price, quantity) VALUES (?, ?, ?, ?, ?)";
	static final String SQL_CHECK_UNIQUE_NAME = "SELECT 1 FROM products WHERE name = ?";
	static final String SQL_QUERY_ONE = "SELECT * FROM products WHERE id = ?";
	static final String SQL_QUERY_LIST = "SELECT * FROM products";
	static final String SQL_QUERY_LIST_BY_ACCOUNT_ID = "SELECT * FROM products WHERE account_id = ?";
	static final String SQL_QUERY_LIST_BY_SCHEDULE_ID = "SELECT * FROM products WHERE schedule_id = ?";
	static final String SQL_CHECK_EXIST_BY_ID = "SELECT 1 FROM products WHERE id = ?";
	static final String SQL_CHECK_EXIST_IN_THEATER = "SELECT 1 FROM products WHERE name = ? and theater_id = ?";
	static final String SQL_CHECK_EXIST_BY_THEATER_ID = "SELECT 1 FROM products WHERE theater_id = ?";
	private static final String SQL_DELETE_BY_SCHEDULE_ID = "DELETE FROM products WHERE schedul_id = ?";
	private static final String SQL_DELETE_BY_SEAT_ID = "DELETE FROM products WHERE seat_id = ?";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM products WHERE id = ?";

	// Constants
//	private static final String UPDATED_FIELDS[] = { "name", "description" };

	public static Product create(Product product) {

		boolean isExistName = DatabaseExecutor.exists(SQL_CHECK_UNIQUE_NAME, product.getName());
		if (!isExistName) {
			throw new RuntimeException("Product has exist");
		}

		long id = DatabaseExecutor.insert(SQL_CREATE, product.getName(), product.getDescription(), product.getId(),
				product.getPrice(), product.getQuantity());

		if (id > 0) {
			return DatabaseExecutor.queryOne(SQL_QUERY_ONE, Product.class, id);
		}

		return null;
	}

	public static Product queryOne(int id) {
		return DatabaseExecutor.queryOne(SQL_QUERY_ONE, Product.class, id);
	}

	public static List<Product> queryList() {
		return DatabaseExecutor.queryList(SQL_QUERY_LIST, Product.class);
	}

	public static boolean deleteByID(int id) {
		return DatabaseExecutor.delete(SQL_DELETE_BY_ID, id) > 0;
	}

	public static void main(String[] args) {
		try {
//			 Create 
//			Seat seat = SeatDao.create(new Seat(1, "A1", SeatEnum.Type.NORMAL));
//			System.out.println("Created: " + seat);

			// Get All Customers
//			List<MovieGenre> genres = MovieGenreDao.queryList();
//			System.out.println("All: " + genres);

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
