package Model;

public class Ticket {

    private int id;
    private int userId;
    private int movieId;
    private String showtime;
    private String seat;
    private String movieTitle;
    private User user;

    // Constructor mặc định
    public Ticket() {}

    // Constructor để tạo vé khi đặt
    public Ticket(int userId, int movieId, String showtime, String seat) {
        this.userId = userId;
        this.movieId = movieId;
        this.showtime = showtime;
        this.seat = seat;
    }

    // Constructor đầy đủ dùng khi load từ DB (có cả ID)
    public Ticket(int id, int userId, int movieId, String showtime, String seat) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
        this.showtime = showtime;
        this.seat = seat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
