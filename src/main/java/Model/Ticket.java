package Model;

public class Ticket {

	private int id;
	private int accountId;
	private int scheduleId;
	private int seatCount;
	private int invoiceId;
	private int seaId;

	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ticket(int id, int accountId, int scheduleId, int seatCount, int invoiceId, int seatId) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.scheduleId = scheduleId;
		this.seatCount = seatCount;
		this.invoiceId = invoiceId;
		this.seaId = seatId;
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

	public int getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public int getSeaId() {
		return seaId;
	}

	public void setSeaId(int seaId) {
		this.seaId = seaId;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", accountId=" + accountId + ", scheduleId=" + scheduleId + ", seatCount="
				+ seatCount + ", invoiceId=" + invoiceId + ", seaId=" + seaId + "]";
	}

}
