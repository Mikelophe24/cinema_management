package Dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import Enum.SeatScheduleEnum;
import Helper.DatabaseExecutor;
import Model.MovieSchedule;
import Model.Seat;

public class MovieScheduleDao {
	static final String SQL_CHECK_EXIST_BY_ID = "SELECT 1 FROM movie_schedules WHERE id = ?";
	static final String SQL_CHECK_EXIST = "SELECT 1 FROM movie_schedules WHERE theater_id = ? and movie_id = ?";
	private static final String SQL_CREATE = "INSERT INTO movie_schedules(theater_id, movie_id, show_date, start_time, duration, price)"
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	static final String SQL_QUERY_ONE = "SELECT * FROM movie_schedules WHERE id = ?";
	static final String SQL_QUERY_ONE_BY_FK_ID = "SELECT * FROM movie_schedules WHERE theater_id = ? and movie_id = ?";
	static final String SQL_QUERY_LIST_BY_THEATER_AND_DATE = "SELECT * FROM movie_schedules WHERE theater_id = ? AND show_date = ?";
	static final String SQL_QUERY_LIST = "SELECT * FROM movie_schedules";
	static final String SQL_QUERY_LIST_BY_THEATER_ID = "SELECT * FROM movie_schedules WHERE theater_id = ?";
	static final String SQL_QUERY_LIST_BY_MOVIE_ID = "SELECT * FROM movie_schedules WHERE movie_id = ?";
	static final String SQL_CHECK_EXIST_BY_THEATER_ID = "SELECT 1 FROM movie_schedules WHERE theater_id = ?";
	private static final String SQL_DELETE_BY_THEATER_ID = "DELETE FROM movie_schedules WHERE theater_id = ?";
	private static final String SQL_DELETE = "DELETE FROM movie_schedules WHERE id = ?";
	static final String SQL_QUERY_FIRST_SCHEDULE_BY_MOVIE_ID = "SELECT * FROM movie_schedules "
			+ "WHERE movie_id = ? ORDER BY show_date ASC, start_time ASC LIMIT 1";
	static final String SQL_QUERY_FIRST_SCHEDULE_BY_SHOW_DATE = "SELECT * FROM movie_schedules WHERE show_date = ?";
	// Constants

	public static MovieSchedule create(MovieSchedule movieSchedule) {
		LocalDate showDate = movieSchedule.getShowDate();
		LocalTime startTime = movieSchedule.getStartTime();
		int duration = movieSchedule.getDuration();
		LocalTime endTime = startTime.plusMinutes(duration);

		int compareShowDate = showDate.compareTo(LocalDate.now());
		System.out.println("compareShowDate: " + compareShowDate);
		if (compareShowDate < 0) {
			throw new RuntimeException("Showtimes past current date");
		}

		List<MovieSchedule> existSchedules = DatabaseExecutor.queryList(SQL_QUERY_LIST_BY_THEATER_AND_DATE,
				MovieSchedule.class, movieSchedule.getTheaterId(), showDate);

		for (MovieSchedule exist : existSchedules) {
			LocalTime existStart = exist.getStartTime();
			LocalTime existEnd = existStart.plusMinutes(exist.getDuration());

			boolean isOverlap = startTime.isBefore(existEnd) && endTime.isAfter(existStart);

			System.out.println("Check overlap with schedule id: " + exist.getId() + ", existStart=" + existStart
					+ ", existEnd=" + existEnd + ", overlap=" + isOverlap);

			if (isOverlap) {
				throw new RuntimeException("Schedule overlaps with existing schedule (id: " + exist.getId() + ")");
			}
		}

		long id = DatabaseExecutor.insert(SQL_CREATE, movieSchedule.getTheaterId(), movieSchedule.getMovieId(),
				movieSchedule.getShowDate(), movieSchedule.getStartTime(), movieSchedule.getDuration(), movieSchedule.getPrice());

		if (id > 0) {
			List<Seat> seats = DatabaseExecutor.queryList(SeatDao.SQL_QUERY_LIST_BY_THEATER_ID, Seat.class,
					movieSchedule.getTheaterId());

			for (Seat seat : seats) {
				long seatScheduleId = DatabaseExecutor.insert(SeatScheduleDao.SQL_CREATE, seat.getId(), id,
						SeatScheduleEnum.Status.AVAILABLE.name(), null);
				if (seatScheduleId < 0) {
					throw new RuntimeException("Create seat_schedules failed");
				}
			}

			return DatabaseExecutor.queryOne(SQL_QUERY_ONE, MovieSchedule.class, id);
		}

		return null;
	}

	public static MovieSchedule queryOne(int id) {
		return DatabaseExecutor.queryOne(SQL_QUERY_ONE, MovieSchedule.class, id);
	}

	public static List<MovieSchedule> queryList() {
		return DatabaseExecutor.queryList(SQL_QUERY_LIST, MovieSchedule.class);
	}

	public static List<MovieSchedule> queryListByTheaterId(int theaterId) {
		return DatabaseExecutor.queryList(SQL_QUERY_LIST_BY_THEATER_ID, MovieSchedule.class, theaterId);
	}

	public static List<MovieSchedule> queryListByMovieId(int movieId) {
		return DatabaseExecutor.queryList(SQL_QUERY_LIST_BY_MOVIE_ID, MovieSchedule.class, movieId);
	}

	public static MovieSchedule queryFirstScheduleOfMovie(int movieId) {
		return DatabaseExecutor.queryOne(SQL_QUERY_FIRST_SCHEDULE_BY_MOVIE_ID, MovieSchedule.class, movieId);
	}

	public static List<MovieSchedule> queryListScheduleByShowDate(String showDate) {
		return DatabaseExecutor.queryList(SQL_QUERY_FIRST_SCHEDULE_BY_SHOW_DATE, MovieSchedule.class, showDate);
	}
	
	public static List<MovieSchedule> queryListByMovieAndTheater(int movieId, int theaterId) {
		String sql = "SELECT * FROM movie_schedules WHERE movie_id = ? AND theater_id = ?";
		return DatabaseExecutor.queryList(sql, MovieSchedule.class, movieId, theaterId);
	}

	private static final String UPDATED_FIELDS[] = { "theater_id", "movie_id", "show_date", "start_time", "price" };

	public static boolean update(int id, Map<String, Object> updateFields) {
		if (updateFields == null || updateFields.isEmpty()) {
			throw new IllegalArgumentException("No fields to update");
		}

		MovieSchedule existSchedule = DatabaseExecutor.queryOne(SQL_QUERY_ONE, MovieSchedule.class, id);
		if (existSchedule == null) {
			throw new RuntimeException("Schedule not found");
		}

		LocalDate newShowDate = existSchedule.getShowDate();
		LocalTime newStartTime = existSchedule.getStartTime();
		int theaterId = existSchedule.getTheaterId();
		int movieId = existSchedule.getMovieId();
		int duration = existSchedule.getDuration();

		StringBuilder sql = new StringBuilder("UPDATE movie_schedules SET ");
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

			switch (key) {
			case "theater_id":
				boolean resDeleted = MovieScheduleDao.delete(id);
				if (resDeleted) {
					int newTheaterId = (int) entry.getValue();
					// Lấy giá hiện tại từ schedule cũ
					double currentPrice = existSchedule.getPrice();
					MovieScheduleDao.create(new MovieSchedule(0, newTheaterId, movieId, newShowDate, newStartTime,
							duration, currentPrice));
				}
				return true;
			case "movie_id":
				movieId = (int) entry.getValue();
				break;
			case "show_date":
				newShowDate = (LocalDate) entry.getValue();
				break;
			case "start_time":
				newStartTime = (LocalTime) entry.getValue();
				break;
			default:
				break;
			}
		}

		if (count == 0) {
			return false;
		}

		int compareScheduleDateToNow = newShowDate.compareTo(LocalDate.now());
		int compareScheduleTimeToNow = newStartTime.compareTo(LocalTime.now());
		if (compareScheduleDateToNow < 0 || (compareScheduleDateToNow == 0 && compareScheduleTimeToNow <= 0)) {
			throw new RuntimeException("Showtimes that have already taken place cannot be edited");
		}

		LocalTime newEndTime = newStartTime.plusMinutes(duration);
		List<MovieSchedule> existSchedules = DatabaseExecutor.queryList(SQL_QUERY_LIST_BY_THEATER_AND_DATE,
				MovieSchedule.class, theaterId, newShowDate);

		for (MovieSchedule other : existSchedules) {
			if (other.getId() == id) {
				continue;
			}
			LocalTime otherStart = other.getStartTime();
			LocalTime otherEnd = otherStart.plusMinutes(other.getDuration());
			boolean isOverlap = newStartTime.isBefore(otherEnd) && newEndTime.isAfter(otherStart);
			if (isOverlap) {
				throw new RuntimeException("New showtime overlaps with existing schedule (id: " + other.getId() + ")");
			}
		}

		sql.append(" WHERE id = ?");
		params.add(id);

		boolean resUpdated = DatabaseExecutor.update(sql.toString(), params.toArray()) > 0;
		return resUpdated;
	}

	public static boolean delete(int id) {
		boolean isExist = DatabaseExecutor.exists(SQL_CHECK_EXIST_BY_ID, id);
		if (!isExist) {
			throw new RuntimeException("Schedule not found");
		}
		return DatabaseExecutor.delete(SQL_DELETE, id) > 0;
	}

	public static void main(String[] args) {
		try {
//			 Create 
//			MovieSchedule movieSchedule = MovieScheduleDao
//					.create(new MovieSchedule(0, 4, 8, LocalDate.of(2025, 7, 11), LocalTime.of(7, 30), 148));
//			System.out.println("Created: " + movieSchedule);

			// Update
//			Map<String, Object> updateFields = new HashMap<>();
//			updateFields.put("theater_id", 4);
//			updateFields.put("movie_id", 14);
//			updateFields.put("show_date", LocalDate.of(2025, 7, 10));
//			updateFields.put("start_time", LocalTime.of(9, 30));

//			boolean updated = MovieScheduleDao.update(14, updateFields);
//			if (updated) {
//				System.out.println("Update OK");
//			} else {
//				System.out.println("Update failed");
//			}

			// Delete
//			boolean deleted = MovieScheduleDao.delete(1);
//			if (deleted) {
//				System.out.println("Delete OK");
//			} else {
//				System.out.println("Delete failed");
//			}

			// Get All Customers
//			List<MovieSchedule> movieSchedules = MovieScheduleDao.queryList();
//			List<MovieSchedule> movieSchedulesByMovieId = MovieScheduleDao.queryListByMovieId(8);
//			List<MovieSchedule> movieSchedulesByTheaterId = MovieScheduleDao.queryListByTheaterId(4);
//			System.out.println("All: " + movieSchedules);
//			System.out.println("movieSchedulesByMovieId: " + movieSchedulesByMovieId);
//			System.out.println("movieSchedulesByTheaterId: " + movieSchedulesByTheaterId);

		} catch (Exception e) {
			System.err.println("ScheduleDao Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
