package Model;

public class Movie {
	private int id;
	private String title;
	private String genre;
	private int duration;

	public Movie() {
	}

	public Movie(int id, String title, String genre, int duration) {
		this.id = id;
		this.title = title;
		this.genre = genre;
		this.duration = duration;
	}

	// Getters & Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return title;
	}
}
