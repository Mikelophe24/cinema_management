package Model;

public class TicketDetail {
    private int id;
    private int ticketId;
    private int seatId;

    public TicketDetail() {
    }

    public TicketDetail(int ticketId, int seatId) {
        this.ticketId = ticketId;
        this.seatId = seatId;
    }

    public TicketDetail(int id, int ticketId, int seatId) {
        this.id = id;
        this.ticketId = ticketId;
        this.seatId = seatId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    @Override
    public String toString() {
        return "TicketDetail{" +
                "id=" + id +
                ", ticketId=" + ticketId +
                ", seatId=" + seatId +
                '}';
    }
}
