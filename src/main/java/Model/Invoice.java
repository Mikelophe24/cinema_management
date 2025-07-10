package Model;

import java.time.LocalDate;

public class Invoice {
	private int id;
	private int accountId;
	private int scheduleId;
	private Double totalAmount;
	private LocalDate bookingDate;

	public Invoice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Invoice(int id, int accountId, int scheduleId, Double totalAmount, LocalDate bookingDate) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.scheduleId = scheduleId;
		this.totalAmount = totalAmount;
		this.bookingDate = bookingDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	@Override
	public String toString() {
		return "Invoice [id=" + id + ", accountId=" + accountId + ", scheduleId=" + scheduleId + ", totalAmount="
				+ totalAmount + ", bookingDate=" + bookingDate + "]";
	}

}
