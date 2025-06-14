package Dao;

import Model.Showtime;
import util.MyConnection;

import java.sql.*;
import java.util.*;

public class ShowtimeDao {
    public List<Showtime> getAllShowtimes() {
        List<Showtime> list = new ArrayList<>();
        String sql = "SELECT s.*, m.title AS movie_title FROM showtimes s JOIN movies m ON s.movie_id = m.id";

        try (Connection conn = MyConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Showtime(
                    rs.getInt("id"),
                    rs.getInt("movie_id"),
                    rs.getString("movie_title"),
                    rs.getDate("show_date").toString(),
                    rs.getTime("show_time").toString(),
                    rs.getString("room")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    
        public boolean updateShowtime(Showtime s) {
        String sql = "UPDATE showtimes SET movie_id=?, show_date=?, show_time=?, room=? WHERE id=?";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, s.getMovieId());
            stmt.setDate(2, java.sql.Date.valueOf(s.getDate()));
            stmt.setTime(3, java.sql.Time.valueOf(s.getTime()));
            stmt.setString(4, s.getRoom());
            stmt.setInt(5, s.getId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

        
    public boolean addShowtime(Showtime s) {
        String sql = "INSERT INTO showtimes (movie_id, show_date, show_time, room) VALUES (?, ?, ?, ?)";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, s.getMovieId());
            stmt.setDate(2, java.sql.Date.valueOf(s.getDate()));
            stmt.setTime(3, Time.valueOf(s.getTime()));
            stmt.setString(4, s.getRoom());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteShowtime(int id) {
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM showtimes WHERE id = ?")) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
