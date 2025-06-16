package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Model.Movie;
import util.MappingDBData;
import util.MyConnection;

public class MovieDao {
	private static final String SQL_FILTER_MOVIES = "SELECT * FROM movies WHERE title LIKE ? OR director LIKE ? OR country LIKE ? OR description LIKE ? LIMIT ? OFFSET ?";
	private static final String SQL_GET_MOVIES = "SELECT * FROM movies LIMIT ? OFFSET ?";
	private static final String SQL_ADD_MOVIE = "INSERT INTO movies (title, country, release_year, duration, director, description, poster, rating, vote_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final Integer PAGE_SIZE = 10;
	private static final Integer CURRENT_PAGE = 1;

	public List<Movie> getMovies(String keyword, Integer page, Integer pageSize) {
		List<Movie> moviesList = new ArrayList<>();

		if (page == null || page < 1)
			page = CURRENT_PAGE;
		if (pageSize == null || pageSize < 1)
			pageSize = PAGE_SIZE;
		int offset = (page - 1) * pageSize;

		try (Connection conn = MyConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						keyword == null || keyword.trim().isEmpty() ? SQL_GET_MOVIES : SQL_FILTER_MOVIES)) {

			if (keyword == null || keyword.trim().isEmpty()) {
				stmt.setInt(1, pageSize);
				stmt.setInt(2, offset);
			} else {
				String kw = "%" + keyword + "%";
				stmt.setString(1, kw);
				stmt.setString(2, kw);
				stmt.setString(3, kw);
				stmt.setString(4, kw);
				stmt.setInt(5, pageSize);
				stmt.setInt(6, offset);
			}

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Movie movie = MappingDBData.mapResultSetToObject(rs, Movie.class);
				moviesList.add(movie);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return moviesList;
	}

	public boolean addMovie(Movie movie) {
		try (Connection conn = MyConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL_ADD_MOVIE)) {
			stmt.setString(1, movie.getTitle());
			stmt.setString(2, movie.getCountry());
			stmt.setString(3, movie.getReleaseYear());
			stmt.setInt(4, movie.getDuration());
			stmt.setString(5, movie.getDirector());
			stmt.setString(6, movie.getDescription());
			stmt.setString(7, movie.getPoster());
			if (movie.getRating() != null) {
				stmt.setDouble(8, movie.getRating());
			} else {
				stmt.setNull(8, java.sql.Types.DOUBLE);
			}
			stmt.setInt(9, movie.getVoteCount());
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateMovieFields(int movieId, Map<String, Object> updateData) {
		if (updateData == null || updateData.isEmpty())
			return false;

		StringBuilder sql = new StringBuilder("UPDATE movies SET ");
		List<Object> values = new ArrayList<>();

		int i = 0;
		for (String field : updateData.keySet()) {
			sql.append(field).append("=?");
			if (i < updateData.size() - 1) {
				sql.append(", ");
			}
			values.add(updateData.get(field));
			i++;
		}

		sql.append(" WHERE id=?");
		values.add(movieId);

		try (Connection conn = MyConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

			for (int j = 0; j < values.size(); j++) {
				Object value = values.get(j);
				if (value instanceof String) {
					stmt.setString(j + 1, (String) value);
				} else if (value instanceof Integer) {
					stmt.setInt(j + 1, (Integer) value);
				} else if (value instanceof Double) {
					stmt.setDouble(j + 1, (Double) value);
				} else if (value == null) {
					stmt.setNull(j + 1, java.sql.Types.NULL);
				} else {
					stmt.setObject(j + 1, value);
				}
			}

			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteMovie(int id) {
		String sql = "DELETE FROM movies WHERE id=?";

		try (Connection conn = MyConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
