package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class MovieSchedule {
	private int id;
	private int theaterId;
	private int movieId;
	private LocalDate showDate;
	private LocalTime startTime;
	private int duration;

	public MovieSchedule() {
	}

	public MovieSchedule(int id, int theaterId, int movieId, LocalDate showDate, LocalTime startTime, int duration) {
		this.id = id;
		this.theaterId = theaterId;
		this.movieId = movieId;
		this.showDate = showDate;
		this.startTime = startTime;
		this.duration = duration;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTheaterId() {
		return theaterId;
	}

	public void setTheaterId(int theaterId) {
		this.theaterId = theaterId;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public LocalDate getShowDate() {
		return showDate;
	}

	public void setShowDate(LocalDate showDate) {
		this.showDate = showDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "MovieSchedule [id=" + id + ", theaterId=" + theaterId + ", movieId=" + movieId + ", showDate="
				+ showDate + ", startTime=" + startTime + ", duration=" + duration + "," + ", ticketPrice=" + "]";
	}

}
