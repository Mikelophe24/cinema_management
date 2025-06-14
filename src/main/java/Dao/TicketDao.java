package Dao;

import Model.Ticket;
import util.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDao {

    public boolean addTicket(Ticket ticket) {
        String sql = "INSERT INTO tickets (user_id, movie_id, showtime, seat) VALUES (?, ?, ?, ?)";

        try (Connection conn = MyConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ticket.getUserId());
            stmt.setInt(2, ticket.getMovieId());
            stmt.setString(3, ticket.getShowtime());
            stmt.setString(4, ticket.getSeat());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Ticket> getTicketsByUserId(int userId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT t.*, m.title FROM tickets t JOIN movies m ON t.movie_id = m.id WHERE t.user_id = ?";

        try (Connection conn = MyConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("id"));
                ticket.setUserId(rs.getInt("user_id"));
                ticket.setMovieId(rs.getInt("movie_id"));
                ticket.setShowtime(rs.getString("showtime"));
                ticket.setSeat(rs.getString("seat"));
                ticket.setMovieTitle(rs.getString("title"));
                tickets.add(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }
}
