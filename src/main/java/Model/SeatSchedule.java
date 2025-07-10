package Model;

import Enum.SeatScheduleEnum;
import Enum.SeatScheduleEnum.Status;

public class SeatSchedule {
	private int seatId;
	private int scheduleId;
	private SeatScheduleEnum.Status status;
	private int ticketId;

	public SeatSchedule() {
	}

	public SeatSchedule(int seatId, int scheduleId, Status status, int ticketId) {
		this.seatId = seatId;
		this.scheduleId = scheduleId;
		this.status = status;
		this.ticketId = ticketId;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public SeatScheduleEnum.Status getStatus() {
		return status;
	}

	public void setStatus(SeatScheduleEnum.Status status) {
		this.status = status;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	@Override
	public String toString() {
		return "SeatSchedule [seatId=" + seatId + ", scheduleId=" + scheduleId + ", status=" + status + ", ticketId="
				+ ticketId + "]";
	}

}
