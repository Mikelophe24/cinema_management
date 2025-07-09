package Model;

import java.math.BigDecimal;
import java.sql.Date;

public class Ticket {
    private int ticketId;
    private int accountId;
    private int scheduleId;
    private int seatCount;
    private BigDecimal totalAmount;
    private Date bookingDate;
    private String status;

    public Ticket() {
    }

    public Ticket(int ticketId, int accountId, int scheduleId, int seatCount,
                  BigDecimal totalAmount, Date bookingDate, String status) {
        this.ticketId = ticketId;
        this.accountId = accountId;
        this.scheduleId = scheduleId;
        this.seatCount = seatCount;
        this.totalAmount = totalAmount;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    // Constructor không có ticketId (dùng khi insert)
    public Ticket(int accountId, int scheduleId, int seatCount,
                  BigDecimal totalAmount, Date bookingDate, String status) {
        this.accountId = accountId;
        this.scheduleId = scheduleId;
        this.seatCount = seatCount;
        this.totalAmount = totalAmount;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    // Getters and Setters
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", accountId=" + accountId +
                ", scheduleId=" + scheduleId +
                ", seatCount=" + seatCount +
                ", totalAmount=" + totalAmount +
                ", bookingDate=" + bookingDate +
                ", status='" + status + '\'' +
                '}';
    }
}
