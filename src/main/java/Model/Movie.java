package Model;

import java.time.LocalDateTime;

public class Movie {
	private int id;
	private String title;
	private String country;
	private String releaseYear;
	private int duration;
	private String director;
	private String description;
	private String poster;
	private Double rating;
	private int voteCount;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Movie() {
	}

	public Movie(int id, String title, String country, String releaseYear, int duration, String director,
			String description, String poster, Double rating, int voteCount, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.title = title;
		this.country = country;
		this.releaseYear = releaseYear;
		this.duration = duration;
		this.director = director;
		this.description = description;
		this.poster = poster;
		this.rating = rating;
		this.voteCount = voteCount;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", country=" + country + ", releaseYear=" + releaseYear
				+ ", duration=" + duration + ", director=" + director + ", description=" + description + ", poster="
				+ poster + ", rating=" + rating + ", voteCount=" + voteCount + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}

}
