package Model;

import Enum.SeatEnum;
import Enum.SeatEnum.Type;

public class Seat {
	private int id;
	private int theaterId;
	private String name;
	private SeatEnum.Type type;

	protected Seat() {
		super();
	}

	protected Seat(int id, int theaterId, String name, Type type) {
		super();
		this.id = id;
		this.theaterId = theaterId;
		this.name = name;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTheaterId() {
		return theaterId;
	}

	public void setTheaterId(int theaterId) {
		this.theaterId = theaterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SeatEnum.Type getType() {
		return type;
	}

	public void setType(SeatEnum.Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Seat [id=" + id + ", theaterId=" + theaterId + ", name=" + name + ", type=" + type + "]";
	}

}
