package Dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import Helper.DatabaseExecutor;
import Model.Movie;
import Model.MovieGenre;

public class MovieDao {
	// Common SQL
	private static final String SQL_CREATE = "INSERT INTO movies (title, country, release_year, duration, director, description, poster, rating, vote_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	static final String SQL_QUERY_ONE = "SELECT * FROM movies WHERE id = ?";
	static final String SQL_QUERY_LIST = "SELECT * FROM movies";
	private static final String SQL_CREATE_FK_MOVIE_GENRE = "INSERT INTO movie_genres(movie_id, genre_id) VALUE (?, ?)";
	static final String SQL_QUERY_LIST_GENRE_BY_MOVIE_ID = "SELECT genre_id FROM movie_genres WHERE movie_id = ?";
	static final String SQL_CHECK_EXIST_BY_ID = "SELECT 1 FROM movies WHERE id = ?";
	private static final String SQL_DELETE = "DELETE FROM movies WHERE id = ?";
	private static final String SQL_DELETE_FK_MOVIE_GENRE_BY_GENRE_ID = "DELETE FROM movie_genres WHERE genre_id = ?";
	private static final String SQL_DELETE_FK_MOVIE_GENRE_BY_MOVIE_ID = "DELETE FROM movie_genres WHERE movie_id = ?";

	// Constants
	private static final String UPDATED_FIELDS[] = { "title", "country", "release_year", "duration", "director",
			"description", "poster", "rating", "vote_count" };

	public static MovieWithGenres create(Movie movie, int[] genreIds) {
		String title = movie.getTitle();
		String country = movie.getCountry();
		int releaseYear = movie.getReleaseYear();
		int duration = movie.getDuration();
		String director = movie.getDirector();
		String description = movie.getDescription();
		String poster = movie.getPoster();
		Double rating = movie.getRating();
		int voteCount = movie.getVoteCount();

		long id = DatabaseExecutor.insert(SQL_CREATE, title, country, releaseYear, duration, director, description,
				poster, rating, voteCount);

		if (id > 0) {
			for (int genreId : genreIds) {
				long insertedRes = DatabaseExecutor.insert(SQL_CREATE_FK_MOVIE_GENRE, id, genreId);
				if (insertedRes != 0) {
					throw new RuntimeException("Create record fk_movie_genre_movie failed");
				}
			}

			Movie createdMovie = DatabaseExecutor.queryOne(SQL_QUERY_ONE, Movie.class, id);

			List<MovieGenre> genreList = new ArrayList<>();
			for (int genreId : genreIds) {
				MovieGenre genre = MovieGenreDao.queryOne(genreId);
				if (genre != null) {
					genreList.add(genre);
				}
			}

			return new MovieWithGenres(createdMovie, genreList.toArray(new MovieGenre[0]));
		}

		return null;
	}

	public static MovieWithGenres queryOne(int id) {
		Movie movie = DatabaseExecutor.queryOne(SQL_QUERY_ONE, Movie.class, id);
		List<GenresOfMovie> genresOfMovie = DatabaseExecutor.queryList(SQL_QUERY_LIST_GENRE_BY_MOVIE_ID,
				GenresOfMovie.class, id);
//		System.out.println("Movie: " + movie);
//		System.out.println("Genres: " + genresOfMovie);
		List<MovieGenre> movieGenresDetail = new ArrayList<MovieGenre>();
		for (GenresOfMovie g : genresOfMovie) {
			MovieGenre genre = MovieGenreDao.queryOne(g.getGenreId());
			if (genre != null) {
				movieGenresDetail.add(genre);
			}
		}
//		System.out.println("Genres: " + movieGenresDetail);
		return new MovieWithGenres(movie, movieGenresDetail.toArray(new MovieGenre[0]));
	}

	public static List<MovieWithGenres> queryList() {
		List<Movie> movies = DatabaseExecutor.queryList(SQL_QUERY_LIST, Movie.class);
		List<MovieWithGenres> movieWithGenresList = new ArrayList<MovieDao.MovieWithGenres>();
		for (Movie movie : movies) {
			if (movie != null && movie.getId() > 0) {
				MovieWithGenres movieWithGenre = MovieDao.queryOne(movie.getId());
				movieWithGenresList.add(movieWithGenre);
			}
		}
		System.out.println("List movies: " + movieWithGenresList);
		return movieWithGenresList;
	}

	public static boolean update(int id, Map<String, Object> updateFields, int[] genreIds) {
		if ((updateFields == null || updateFields.isEmpty()) && (genreIds == null || genreIds.length == 0)) {
			throw new IllegalArgumentException("No fields to update");
		}

		boolean isExistMovie = DatabaseExecutor.exists(SQL_CHECK_EXIST_BY_ID, id);
		if (!isExistMovie) {
			throw new RuntimeException("Movie not found");
		}

		StringBuilder sql = new StringBuilder("UPDATE movies SET ");
		List<Object> params = new ArrayList<>();
		int count = 0;

		for (Map.Entry<String, Object> entry : updateFields.entrySet()) {
			String key = entry.getKey();

			boolean isAllowed = Arrays.stream(UPDATED_FIELDS).anyMatch(field -> field.equalsIgnoreCase(key));
			if (!isAllowed) {
				throw new RuntimeException("Field '" + key + "' is not allowed to update");
			}

			if (count > 0) {
				sql.append(", ");
			}
			sql.append(key).append(" = ?");
			params.add(entry.getValue());
			count++;
		}

		if (count > 0) {
			sql.append(" WHERE id = ?");
			params.add(id);

			int rowsAffected = DatabaseExecutor.update(sql.toString(), params.toArray());
			if (rowsAffected <= 0) {
				return false;
			}
		}

		if (genreIds != null && genreIds.length > 0) {
			DatabaseExecutor.delete(SQL_DELETE_FK_MOVIE_GENRE_BY_MOVIE_ID, id);

			for (int genreId : genreIds) {
				MovieGenre genre = MovieGenreDao.queryOne(genreId);
				if (genre == null) {
					throw new RuntimeException("Genre with id: " + genreId + " not found");
				}
			}

			for (int genreId : genreIds) {
				long insertedRes = DatabaseExecutor.insert(SQL_CREATE_FK_MOVIE_GENRE, id, genreId);
				if (insertedRes != 0) {
					throw new RuntimeException("Update genre failed for movie_id=" + id + ", genre_id=" + genreId);
				}
			}
		}

		return true;
	}

	public static boolean delete(int id) {
		if (id < 0) {
			return false;
		}
		boolean isExistMovie = DatabaseExecutor.exists(SQL_CHECK_EXIST_BY_ID, id);
		if (!isExistMovie) {
			throw new RuntimeException("Movie not found");
		}
		return DatabaseExecutor.delete(SQL_DELETE, id) > 0;
	}

	// Nested Class
	public static class MovieWithGenres {
		private final Movie movie;
		private final MovieGenre[] genres;

		public MovieWithGenres(Movie movie, MovieGenre[] genres) {
			this.movie = movie;
			this.genres = genres;
		}

		public Movie getMovie() {
			return movie;
		}

		public MovieGenre[] getGenres() {
			return genres;
		}

		@Override
		public String toString() {
			return "MovieWithGenres [movie=" + movie + ", genres=" + Arrays.toString(genres) + "]";
		}
	}

	public static class GenresOfMovie {
		private int movieId;
		private int genreId;

		public int getMovieId() {
			return movieId;
		}

		public void setMovieId(int movieId) {
			this.movieId = movieId;
		}

		public int getGenreId() {
			return genreId;
		}

		public void setGenreId(int genreId) {
			this.genreId = genreId;
		}

		@Override
		public String toString() {
			return "GenresOfMovie [movieId=" + movieId + ", genreId=" + genreId + "]";
		}

	}

	public static void main(String[] args) {
		try {
			// Create
			// Inception
//			Movie inception = new Movie(0, "Inception", "USA", 2010, 148, "Christopher Nolan",
//					"A thief who enters the dreams of others to steal secrets.", "https://example.com/inception.jpg",
//					8.8, 2000000, null, null);
//			MovieWithGenres inceptionCreated = MovieDao.create(inception, new int[] { 8, 9 }); // Sci-Fi, Fantasy
//
//			// Titanic
//			Movie titanic = new Movie(0, "Titanic", "USA", 1997, 195, "James Cameron",
//					"A love story on the ill-fated Titanic ship.", "https://example.com/titanic.jpg", 7.8, 1500000,
//					null, null);
//			MovieWithGenres titanicCreated = MovieDao.create(titanic, new int[] { 10, 6 }); // Romance, Drama
//
//			// The Conjuring
//			Movie conjuring = new Movie(0, "The Conjuring", "USA", 2013, 112, "James Wan",
//					"Paranormal investigators help a family terrorized by dark forces.",
//					"https://example.com/conjuring.jpg", 7.5, 800000, null, null);
//			MovieWithGenres conjuringCreated = MovieDao.create(conjuring, new int[] { 7, 11 }); // Horror, Thriller
//
//			// The Lion King
//			Movie lionKing = new Movie(0, "The Lion King", "USA", 1994, 88, "Roger Allers, Rob Minkoff",
//					"A lion cub prince flees after the death of his father.", "https://example.com/lionking.jpg", 8.5,
//					1000000, null, null);
//			MovieWithGenres lionKingCreated = MovieDao.create(lionKing, new int[] { 12, 9 }); // Anime, Fantasy
//
//			// Avatar
//			Movie avatar = new Movie(0, "Avatar", "USA", 2009, 162, "James Cameron",
//					"A marine on an alien planet becomes torn between following orders and protecting the world he feels is his home.",
//					"https://example.com/avatar.jpg", 7.9, 1300000, null, null);
//			MovieWithGenres avatarCreated = MovieDao.create(avatar, new int[] { 8, 9 }); // Sci-Fi, Fantasy
//
//			// The Godfather
//			Movie godfather = new Movie(0, "The Godfather", "USA", 1972, 175, "Francis Ford Coppola",
//					"The aging patriarch of an organized crime dynasty transfers control to his reluctant son.",
//					"https://example.com/godfather.jpg", 9.2, 1700000, null, null);
//			MovieWithGenres godfatherCreated = MovieDao.create(godfather, new int[] { 6 }); // Drama
//
//			// Interstellar
//			Movie interstellar = new Movie(0, "Interstellar", "USA", 2014, 169, "Christopher Nolan",
//					"A team travels through a wormhole in space in an attempt to ensure humanity's survival.",
//					"https://example.com/interstellar.jpg", 8.6, 1400000, null, null);
//			MovieWithGenres interstellarCreated = MovieDao.create(interstellar, new int[] { 8, 9 }); // Sci-Fi, Fantasy

			// The Dark Knight
//			Movie darkKnight = new Movie(0, "The Dark Knight", "USA", 2008, 152, "Christopher Nolan",
//					"Batman faces the Joker, a criminal mastermind who plunges Gotham into chaos.",
//					"https://example.com/darkknight.jpg", 9.0, 2200000, null, null);
//			MovieWithGenres darkKnightCreated = MovieDao.create(darkKnight, new int[] { 4, 11 }); // Action, Thriller

//			System.out.println("Created movie: " + movieWithGenreCreated.getMovie().getTitle());
//			System.out.println("Genres:");
//			for (MovieGenre genre : movieWithGenreCreated.getGenres()) {
//				System.out.println(" - " + genre.getName());
//			}

			// Query one
//			MovieWithGenres movieWithGenre = MovieDao.queryOne(2);
//			System.out.println("Qeury one: " + movieWithGenre);

			// Update
//			Map<String, Object> updates = new HashMap<>();
//			updates.put("title", "Updated Title");
//			updates.put("rating", 9.0);
//
//			int[] newGenres = { 2 };
//			boolean result = MovieDao.update(2, updates, newGenres);
//
//			System.out.println(result ? "Update OK" : "Update Failed");

//			// Delete
//			boolean resDeleted = MovieDao.delete(7);
//			System.out.println(resDeleted ? "Delete OK" : "Delete Failed");

			// Query list
			MovieDao.queryList();

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
