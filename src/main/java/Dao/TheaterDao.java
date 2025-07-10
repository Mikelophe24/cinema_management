package Dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import Enum.TheaterEnum;
import Helper.DatabaseExecutor;
import Model.Seat;
import Model.Theater;
import util.SeatGenerator;

public class TheaterDao {
	// Common SQL
	private static final String SQL_CREATE = "INSERT INTO theaters(name, status, capacity, seat_count, description, image) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL_CHECK_UNIQUE_NAME = "SELECT 1 FROM theaters WHERE name = ?";
	static final String SQL_QUERY_ONE = "SELECT * FROM theaters WHERE id = ?";
	private static final String SQL_QUERY_LIST = "SELECT * FROM theaters";
	static final String SQL_CHECK_EXIST_BY_ID = "SELECT 1 FROM theaters WHERE id = ?";
//	private static final String SQL_DELETE_BY_ACCOUNT_ID = "DELETE FROM theaters WHERE id = ?";
	private static final String SQL_DELETE = "DELETE FROM theaters WHERE id = ?";

	// Constants
	private static final String UPDATED_FIELDS[] = { "name", "status", "capacity", "seat_count", "description",
			"image" };

	public static Theater create(Theater theater) {

		boolean isExist = DatabaseExecutor.exists(SQL_CHECK_UNIQUE_NAME, theater.getName());
		if (isExist) {
			throw new RuntimeException("Theater has exist");
		}

		long id = DatabaseExecutor.insert(SQL_CREATE, theater.getName(), theater.getStatus().getValue(),
				theater.getCapacity(), theater.getSeatCount(), theater.getDescription(), theater.getImage());

		if (id > 0) {
			int seatCount = theater.getSeatCount();
			int capacity = theater.getCapacity();
			int coupleSeatCount = (capacity - seatCount) * 2;
			int normalSeatCount = capacity - coupleSeatCount;
			List<Seat> seatsGenerated = SeatGenerator.generateSeats((int) id, coupleSeatCount, normalSeatCount, 12);
			List<Seat> seatsCreated = new ArrayList<Seat>();
			System.out.println("Seats: " + seatsGenerated);
			for (Seat seat : seatsGenerated) {
				Seat seatCreated = SeatDao.create(seat);
				if (seatCreated != null) {
					seatsCreated.add(seatCreated);
				}
			}
			return DatabaseExecutor.queryOne(SQL_QUERY_ONE, Theater.class, id);
		}

		return null;
	}

	// Query one
	public static TheaterWithSeats queryOne(int id) {
		Theater theater = DatabaseExecutor.queryOne(SQL_QUERY_ONE, Theater.class, id);
		if (theater == null) {
			throw new RuntimeException("Theater not found");
		}
		List<Seat> seats = SeatDao.queryListByTheaterId(id);
		return new TheaterWithSeats(theater, seats.toArray(new Seat[0]));
	}

	public static Theater queryOneWithoutSeat(int id) {
		Theater theater = DatabaseExecutor.queryOne(SQL_QUERY_ONE, Theater.class, id);
		if (theater == null) {
			throw new RuntimeException("Theater not found");
		}
		return theater;
	}

	// Query list
	public static List<TheaterWithSeats> queryList() {
		List<Theater> theaters = DatabaseExecutor.queryList(SQL_QUERY_LIST, Theater.class);
		List<TheaterWithSeats> theaterWithSeats = new ArrayList<TheaterWithSeats>();
		for (Theater theater : theaters) {
			List<Seat> seats = SeatDao.queryListByTheaterId(theater.getId());
			theaterWithSeats.add(new TheaterWithSeats(theater, seats.toArray(new Seat[0])));
		}
		return theaterWithSeats;
	}

	public static boolean update(int id, Map<String, Object> updateFields) {
		if (updateFields == null || updateFields.isEmpty()) {
			throw new IllegalArgumentException("No fields to update");
		}

		int isUpdateSeat = 0;

		StringBuilder sql = new StringBuilder("UPDATE theaters SET ");
		List<Object> params = new ArrayList<>();
		int count = 0;

		for (Map.Entry<String, Object> entry : updateFields.entrySet()) {
			String key = entry.getKey();
			boolean isAllowed = Arrays.stream(UPDATED_FIELDS).anyMatch(field -> field.equalsIgnoreCase(key));
			if (!isAllowed) {
				throw new RuntimeException("Field " + key + " not allowed");
			}

			if (key == "capacity" || key == "seat_count") {
				isUpdateSeat++;
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

		Theater theater = DatabaseExecutor.queryOne(SQL_QUERY_ONE, Theater.class, id);
		if (theater == null) {
			throw new RuntimeException("Theater not found");
		}

		sql.append(" WHERE id = ?");
		params.add(id);

		boolean resUpdated = DatabaseExecutor.update(sql.toString(), params.toArray()) > 0;
		if (resUpdated) {
			if (isUpdateSeat == 2) {
				boolean isExistSeat = DatabaseExecutor.exists(SeatDao.SQL_CHECK_EXIST_BY_THEATER_ID, id);
				if (isExistSeat) {
					boolean resDeleted = SeatDao.deleteByTheaterId(id);
					System.out.println("Res Deleted: " + resDeleted);
					if (!resDeleted) {
						throw new RuntimeException("Delete seats of this theater failed");
					}
				}

				int seatCount = theater.getSeatCount();
				int capacity = theater.getCapacity();
				int coupleSeatCount = (capacity - seatCount) * 2;
				int normalSeatCount = capacity - coupleSeatCount;
				List<Seat> seatsGenerated = SeatGenerator.generateSeats((int) id, coupleSeatCount, normalSeatCount, 12);
				for (Seat seat : seatsGenerated) {
					Seat seatCreated = SeatDao.create(seat);
					if (seatCreated == null) {
						throw new RuntimeException("Update seats of this theater failed");
					}
				}
			}
		}
		return resUpdated;
	}

	// Delete
	public static boolean delete(int id) {
		boolean isExist = DatabaseExecutor.exists(SQL_CHECK_EXIST_BY_ID, id);
		if (!isExist) {
			throw new RuntimeException("Theater not found");
		}
		return DatabaseExecutor.delete(SQL_DELETE, id) > 0;
	}

	// Nested Class
	public static class TheaterWithSeats {
		private final Theater theater;
		private final Seat[] seats;

		public TheaterWithSeats(Theater theater, Seat[] seats) {
			this.theater = theater;
			this.seats = seats;
		}

		public Theater getTheater() {
			return theater;
		}

		public Seat[] getSeats() {
			return seats;
		}

		@Override
		public String toString() {
			return "TheaterWithSeats [theater=" + theater + ", seats=" + Arrays.toString(seats) + "]";
		}
	}

	public static void main(String[] args) {
		try {
			List<TheaterWithSeats> theaterWithSeats = TheaterDao.queryList();
			System.out.println("All: " + theaterWithSeats);

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
