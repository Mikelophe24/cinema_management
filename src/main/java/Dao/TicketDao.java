package Dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

import Helper.DatabaseExecutor;
import Model.Ticket;
import util.MyConnection;

public class TicketDao {

    // Thêm vé mới
    public long insertTicket(Ticket ticket) {
        String sql = "INSERT INTO tickets (account_id, schedule_id, seat_count, status, total_amount, booking_date) VALUES (?, ?, ?, ?, ?, ?)";
        return DatabaseExecutor.insert(sql,
                ticket.getAccountId(),
                ticket.getScheduleId(),
                ticket.getSeatCount(),
                ticket.getStatus(),
                ticket.getTotalAmount(),
                ticket.getBookingDate()
        );
    }

    // Cập nhật thông tin vé
    public int updateTicket(Ticket ticket) {
        String sql = "UPDATE tickets SET account_id = ?, schedule_id = ?, seat_count = ?, status = ?, total_amount = ?, booking_date = ? WHERE ticket_id = ?";
        return DatabaseExecutor.update(sql,
                ticket.getAccountId(),
                ticket.getScheduleId(),
                ticket.getSeatCount(),
                ticket.getStatus(),
                ticket.getTotalAmount(),
                ticket.getBookingDate(),
                ticket.getTicketId()
        );
    }

    // Xoá vé theo ID
    public int deleteTicket(int ticketId) {
        String sql = "DELETE FROM tickets WHERE ticket_id = ?";
        return DatabaseExecutor.delete(sql, ticketId);
    }

    // Tìm vé theo ID
    public Ticket findTicketById(int id) {
        String sql = "SELECT    * FROM tickets WHERE ticket_id = ?";

        return DatabaseExecutor.queryOne(sql, Ticket.class, id);
    }

    // Lấy danh sách vé của một người dùng
    public List<Ticket> findTicketsByUserId(int userId) {
        String sql = "SELECT * FROM tickets WHERE account_id = ?";
        return DatabaseExecutor.queryList(sql, Ticket.class, userId);
    }

    // Lấy tất cả vé
    public List<Ticket> getAllTickets() {
        String sql = "SELECT * FROM tickets";
        return DatabaseExecutor.queryList(sql, Ticket.class);
    }

    public int getTicketPrice(int scheduleId) {
        String sql = "SELECT ticket_price FROM movie_schedules WHERE id = ?";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, scheduleId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ticket_price");
            } else {
                throw new RuntimeException("Schedule not found with id: " + scheduleId);
            }

        } catch (SQLException e) {
            System.err.println("SQL Error in getTicketPrice: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }



    // ✅ Test thủ công DAO
    public static void main(String[] args) {
        try {
            TicketDao dao = new TicketDao();

            System.out.println("====== TICKET DAO TEST ======");

            // ✅ 1. Thêm vé mới
            Ticket newTicket = new Ticket(2, 1, 3, new BigDecimal("150000"), new Date(System.currentTimeMillis()), "sold");

            long newId = dao.insertTicket(newTicket);
            System.out.println("Created Ticket ID: " + newId);

            // ✅ 2. Tìm vé theo ID vừa tạo
            Ticket found = dao.findTicketById((int) newId);
            if (found != null) {
                System.out.println("Found Ticket: ID=" + found.getTicketId()
                        + ", SeatCount=" + found.getSeatCount()
                        + ", Status=" + found.getStatus());
            }

            // ✅ 3. Cập nhật vé
            if (found != null) {
                found.setSeatCount(5);
                found.setStatus("sold");
                int updated = dao.updateTicket(found);
                System.out.println(updated > 0 ? "Update OK" : "Update failed");
            }

            // ✅ 4. Lấy tất cả vé của userId = 2
            List<Ticket> tickets = dao.findTicketsByUserId(2);
            System.out.println("Tickets of user 2:");
            for (Ticket t : tickets) {
                System.out.println("- Ticket ID: " + t.getTicketId() + ", Status: " + t.getStatus());
            }

            // ✅ 5. Xoá vé vừa tạo
            int deleted = dao.deleteTicket((int) newId);
            System.out.println(deleted > 0 ? "Delete OK" : "Delete failed");

        } catch (Exception e) {
            System.err.println("TicketDao Error: " + e.getMessage());
            e.printStackTrace();
        }


    }
}
