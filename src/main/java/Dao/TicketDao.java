package Dao;

import java.util.List;

import Helper.DatabaseExecutor;
import Model.Ticket;

public class TicketDao {
	// Common SQL
	private static final String SQL_CREATE = "INSERT INTO tickets(account_id, schedule_id, seat_id, seat_count, invoice_id) VALUES (?, ?, ?, ?, ?)";
	static final String SQL_CHECK_EXIST = "SELECT 1 FROM tickets WHERE schedule_id = ? and account_id = ?";
	static final String SQL_QUERY_ONE = "SELECT * FROM tickets WHERE id = ?";
	static final String SQL_QUERY_LIST = "SELECT * FROM tickets";
	static final String SQL_QUERY_LIST_BY_ACCOUNT_ID = "SELECT * FROM tickets WHERE account_id = ?";
	static final String SQL_QUERY_LIST_BY_SCHEDULE_ID = "SELECT * FROM tickets WHERE schedule_id = ?";
	static final String SQL_CHECK_EXIST_BY_ID = "SELECT 1 FROM tickets WHERE id = ?";
	static final String SQL_CHECK_EXIST_IN_THEATER = "SELECT 1 FROM tickets WHERE name = ? and theater_id = ?";
	static final String SQL_CHECK_EXIST_BY_THEATER_ID = "SELECT 1 FROM tickets WHERE theater_id = ?";
	private static final String SQL_DELETE_BY_SCHEDULE_ID = "DELETE FROM tickets WHERE schedul_id = ?";
	private static final String SQL_DELETE_BY_SEAT_ID = "DELETE FROM tickets WHERE seat_id = ?";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM tickets WHERE id = ?";

	// Constants
//	private static final String UPDATED_FIELDS[] = { "name", "description" };

	public static Ticket create(Ticket ticket) {

		boolean isExistAccount = DatabaseExecutor.exists(AccountDao.SQL_CHECK_EXIST_BY_ID, ticket.getAccountId());
		if (!isExistAccount) {
			throw new RuntimeException("Account not found");
		}

		boolean isExistSchedule = DatabaseExecutor.exists(MovieScheduleDao.SQL_CHECK_EXIST_BY_ID,
				ticket.getScheduleId());
		if (!isExistSchedule) {
			throw new RuntimeException("Schedule not found");
		}

//		boolean isExistInvoice = DatabaseExecutor.exists(SQL_CHECK_EXIST, ticket.getScheduleId(),
//				ticket.getAccountId());
//		if (isExistInvoice) {
//			throw new RuntimeException("Invoice has exist");
//		}

		long id = DatabaseExecutor.insert(SQL_CREATE, ticket.getAccountId(), ticket.getScheduleId(), ticket.getSeaId(),
				ticket.getSeatCount(), ticket.getInvoiceId());

		if (id > 0) {
			return DatabaseExecutor.queryOne(SQL_QUERY_ONE, Ticket.class, id);
		}

		return null;
	}

	public static Ticket queryOne(int id) {
		return DatabaseExecutor.queryOne(SQL_QUERY_ONE, Ticket.class, id);
	}

	public static List<Ticket> queryListByAccountId(int accountId) {
		return DatabaseExecutor.queryList(SQL_QUERY_LIST_BY_ACCOUNT_ID, Ticket.class, accountId);
	}

	public static List<Ticket> queryListByScheduleId(int scheduleId) {
		return DatabaseExecutor.queryList(SQL_QUERY_LIST_BY_SCHEDULE_ID, Ticket.class, scheduleId);
	}

	public static boolean deleteById(int id) {
		return DatabaseExecutor.delete(SQL_DELETE_BY_ID, id) > 0;
	}

	public static boolean deleteByScheduleId(int scheduleId) {
		return DatabaseExecutor.delete(SQL_DELETE_BY_SCHEDULE_ID, scheduleId) > 0;
	}

	public static boolean deleteBySeatId(int seatId) {
		return DatabaseExecutor.delete(SQL_DELETE_BY_SEAT_ID, seatId) > 0;
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
