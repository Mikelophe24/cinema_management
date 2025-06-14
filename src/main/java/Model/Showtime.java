package Model;

public class Showtime {
    private int id;
    private int movieId;
    private String movieTitle; // để hiển thị
    private String date;
    private String time;
    private String room;

    public Showtime() {}

    public Showtime(int id, int movieId, String movieTitle, String date, String time, String room) {
        this.id = id;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.date = date;
        this.time = time;
        this.room = room;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
