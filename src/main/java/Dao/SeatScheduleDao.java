package Dao;

import Helper.DatabaseExecutor;
import Model.SeatSchedule;

public class SeatScheduleDao {
	static final String SQL_CHECK_EXIST = "SELECT 1 FROM seat_schedules WHERE seat_id = ? and schedule_id = ?";
	static final String SQL_CREATE = "INSERT INTO seat_schedules(seat_id, schedule_id, status, ticket_id) "
			+ "VALUES (?, ?, ?, ?)";
	static final String SQL_QUERY_ONE = "SELECT * FROM seat_schedules WHERE id = ?";
	static final String SQL_QUERY_LIST = "SELECT * FROM seat_schedules";
	static final String SQL_QUERY_LIST_BY_THEATER_ID = "SELECT * FROM seat_schedules WHERE theater_id = ?";
	static final String SQL_CHECK_EXIST_BY_THEATER_ID = "SELECT 1 FROM seat_schedules WHERE theater_id = ?";
	private static final String SQL_DELETE_BY_THEATER_ID = "DELETE FROM seat_schedules WHERE theater_id = ?";

	// Constants
	private static final String UPDATED_FIELDS[] = { "name", "description" };

	public static SeatSchedule create(SeatSchedule seatSchedule) {

		boolean isExist = DatabaseExecutor.exists(SQL_CHECK_EXIST, seatSchedule.getSeatId(),
				seatSchedule.getScheduleId());
		if (isExist) {
			throw new RuntimeException("Schedule has exist");
		}

		long id = DatabaseExecutor.insert(SQL_CREATE, seatSchedule.getSeatId(), seatSchedule.getScheduleId(),
				seatSchedule.getStatus(), seatSchedule.getTicketId());

		if (id > 0) {
			return DatabaseExecutor.queryOne(SQL_QUERY_ONE, SeatSchedule.class, id);
		}

		return null;
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
