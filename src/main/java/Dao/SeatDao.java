package Dao;

import java.util.List;

import Enum.SeatEnum;
import Helper.DatabaseExecutor;
import Model.Seat;

public class SeatDao {
	// Common SQL
	private static final String SQL_CREATE = "INSERT INTO seats(theater_id, name, type) VALUES (?, ?, ?)";
	static final String SQL_CHECK_UNIQUE_NAME = "SELECT 1 FROM seats WHERE name = ?";
	static final String SQL_QUERY_ONE = "SELECT * FROM seats WHERE id = ?";
	static final String SQL_QUERY_LIST = "SELECT * FROM seats";
	static final String SQL_QUERY_LIST_BY_THEATER_ID = "SELECT * FROM seats WHERE theater_id = ?";
	static final String SQL_CHECK_EXIST_BY_ID = "SELECT 1 FROM seats WHERE id = ?";
	static final String SQL_CHECK_EXIST_IN_THEATER = "SELECT 1 FROM seats WHERE name = ? and theater_id = ?";
	static final String SQL_CHECK_EXIST_BY_THEATER_ID = "SELECT 1 FROM seats WHERE theater_id = ?";
	private static final String SQL_DELETE_BY_THEATER_ID = "DELETE FROM seats WHERE theater_id = ?";

	// Constants
//	private static final String UPDATED_FIELDS[] = { "name", "description" };

	public static Seat create(Seat seat) {

		boolean isExistSeat = DatabaseExecutor.exists(SQL_CHECK_EXIST_IN_THEATER, seat.getName(), seat.getTheaterId());
		if (isExistSeat) {
			throw new RuntimeException("Seat has exist");
		}

		boolean isExistTheater = DatabaseExecutor.exists(TheaterDao.SQL_CHECK_EXIST_BY_ID, seat.getTheaterId());
		if (!isExistTheater) {
			throw new RuntimeException("Theater no exist");
		}

		long id = DatabaseExecutor.insert(SQL_CREATE, seat.getTheaterId(), seat.getName(), seat.getType().getValue());

		if (id > 0) {
			return DatabaseExecutor.queryOne(SQL_QUERY_ONE, Seat.class, id);
		}

		return null;
	}

	public static Seat queryOne(int id) {
		return DatabaseExecutor.queryOne(SQL_QUERY_ONE, Seat.class, id);
	}

	public static List<Seat> queryListByTheaterId(int theaterId) {
		return DatabaseExecutor.queryList(SQL_QUERY_LIST_BY_THEATER_ID, Seat.class, theaterId);
	}

	public static boolean deleteByTheaterId(int theaterId) {
		return DatabaseExecutor.delete(SQL_DELETE_BY_THEATER_ID, theaterId) > 0;
	}

	public static void main(String[] args) {
		try {
//			 Create 
			Seat seat = SeatDao.create(new Seat(1, "A1", SeatEnum.Type.NORMAL));
			System.out.println("Created: " + seat);

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
