package Dao;

import java.util.List;

import Helper.DatabaseExecutor;
import Model.Invoice;

public class InvoiceDao {
	// Common SQL
	private static final String SQL_CREATE = "INSERT INTO invoices(account_id, schedule_id, total_amount, booking_date) VALUES (?, ?, ?, ?)";
	private static final String SQL_CREATE_ORDER_PRODUCT_DETAIL = "INSERT INTO order_details(invoice_id, product_id, quantity) VALUES (?, ?, ?)";
	static final String SQL_CHECK_EXIST = "SELECT 1 FROM invoices WHERE schedule_id = ? and account_id = ?";
	static final String SQL_QUERY_ONE = "SELECT * FROM invoices WHERE id = ?";
	static final String SQL_QUERY_LIST = "SELECT * FROM invoices";
	static final String SQL_QUERY_LIST_BY_ACCOUNT_ID = "SELECT * FROM invoices WHERE account_id = ?";
	static final String SQL_QUERY_LIST_BY_SCHEDULE_ID = "SELECT * FROM invoices WHERE schedule_id = ?";
	static final String SQL_CHECK_EXIST_BY_ID = "SELECT 1 FROM invoices WHERE id = ?";
	static final String SQL_CHECK_EXIST_IN_THEATER = "SELECT 1 FROM invoices WHERE name = ? and theater_id = ?";
	static final String SQL_CHECK_EXIST_BY_THEATER_ID = "SELECT 1 FROM invoices WHERE theater_id = ?";
	private static final String SQL_DELETE_BY_SCHEDULE_ID = "DELETE FROM invoices WHERE schedul_id = ?";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM invoices WHERE id = ?";

	// Constants
//	private static final String UPDATED_FIELDS[] = { "name", "description" };

	public static Invoice create(Invoice invoice) {

		boolean isExistAccount = DatabaseExecutor.exists(AccountDao.SQL_CHECK_EXIST_BY_ID, invoice.getAccountId());
		if (!isExistAccount) {
			throw new RuntimeException("Account not found");
		}

		boolean isExistSchedule = DatabaseExecutor.exists(MovieScheduleDao.SQL_CHECK_EXIST_BY_ID,
				invoice.getScheduleId());
		if (!isExistSchedule) {
			throw new RuntimeException("Schedule not found");
		}

		long id = DatabaseExecutor.insert(SQL_CREATE, invoice.getAccountId(), invoice.getScheduleId(),
				invoice.getTotalAmount(), invoice.getBookingDate());

		if (id > 0) {
			return DatabaseExecutor.queryOne(SQL_QUERY_ONE, Invoice.class, id);
		}

		return null;
	}

	public static boolean createOrderProductDetail(int invoiceId, int productId, int quantity) {

		boolean isExistInvoice = DatabaseExecutor.exists(SQL_CHECK_EXIST_BY_ID, invoiceId);
		if (!isExistInvoice) {
			throw new RuntimeException("Invoice not found");
		}

		boolean isExistProduct = DatabaseExecutor.exists(ProductDao.SQL_CHECK_EXIST_BY_ID, productId);
		if (!isExistProduct) {
			throw new RuntimeException("Product not found");
		}

		long id = DatabaseExecutor.insert(SQL_CREATE_ORDER_PRODUCT_DETAIL, invoiceId, productId, quantity);
		return id > 0;
	}

	public static Invoice queryOne(int id) {
		return DatabaseExecutor.queryOne(SQL_QUERY_ONE, Invoice.class, id);
	}

	public static List<Invoice> queryListByAccountId(int accountId) {
		return DatabaseExecutor.queryList(SQL_QUERY_LIST_BY_ACCOUNT_ID, Invoice.class, accountId);
	}

	public static List<Invoice> queryListByScheduleId(int scheduleId) {
		return DatabaseExecutor.queryList(SQL_QUERY_LIST_BY_SCHEDULE_ID, Invoice.class, scheduleId);
	}

	public static boolean deleteById(int id) {
		return DatabaseExecutor.delete(SQL_DELETE_BY_ID, id) > 0;
	}

	public static boolean deleteByScheduleId(int scheduleId) {
		return DatabaseExecutor.delete(SQL_DELETE_BY_SCHEDULE_ID, scheduleId) > 0;
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
