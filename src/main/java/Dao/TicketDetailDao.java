package Dao;

import Helper.DatabaseExecutor;
import Model.TicketDetail;

import java.util.List;

public class TicketDetailDao {

    // Thêm chi tiết vé (ghế đã chọn)
    public long insert(TicketDetail detail) {
        String sql = "INSERT INTO ticket_details (ticket_id, seat_id) VALUES (?, ?)";
        return DatabaseExecutor.insert(sql, detail.getTicketId(), detail.getSeatId());
    }

    // Lấy danh sách ghế theo ticket_id
    public List<TicketDetail> getByTicketId(int ticketId) {
        String sql = "SELECT * FROM ticket_details WHERE ticket_id = ?";
        return DatabaseExecutor.queryList(sql, TicketDetail.class, ticketId);
    }

    // Xoá tất cả ticket_detail theo ticket_id
    public int deleteByTicketId(int ticketId) {
        String sql = "DELETE FROM ticket_details WHERE ticket_id = ?";
        return DatabaseExecutor.delete(sql, ticketId);
    }
}
