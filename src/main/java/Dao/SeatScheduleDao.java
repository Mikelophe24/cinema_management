package Dao;

import Helper.DatabaseExecutor;

public class SeatScheduleDao {

    // Cập nhật trạng thái ghế trong lịch chiếu
    public int updateSeatStatus(int seatId, int scheduleId, int status, Integer ticketId) {
        String sql = "UPDATE seat_schedules SET status = ?, ticket_id = ? WHERE seat_id = ? AND schedule_id = ?";
        return DatabaseExecutor.update(sql, status, ticketId, seatId, scheduleId);
    }

    // Đặt ghế (status = 1)
    public int bookSeat(int seatId, int scheduleId, int ticketId) {
        return updateSeatStatus(seatId, scheduleId, 1, ticketId);
    }

    // Huỷ đặt ghế (status = 0)
    public int unbookSeat(int seatId, int scheduleId) {
        return updateSeatStatus(seatId, scheduleId, 0, null);
    }
}
