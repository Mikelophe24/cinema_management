package Model;

import Enum.TheaterEnum;

public class Theater {
	private int id;
	private String name;
	private TheaterEnum.Status status;
	private int capacity;
	private int seatCount;
	private String description;
	private String image;

	public Theater() {
	}

	public Theater(int id, String name, TheaterEnum.Status status, int capacity, int seatCount, String description,
			String image) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.capacity = capacity;
		this.seatCount = seatCount;
		this.description = description;
		this.image = image;
	}

	public Theater(String name, TheaterEnum.Status status, int capacity, int seatCount, String description,
			String image) {
		this.name = name;
		this.status = status;
		this.capacity = capacity;
		this.seatCount = seatCount;
		this.description = description;
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TheaterEnum.Status getStatus() {
		return status;
	}

	public void setStatus(TheaterEnum.Status status) {
		this.status = status;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Theater [id=" + id + ", name=" + name + ", status=" + status + ", capacity=" + capacity + ", seatCount="
				+ seatCount + ", description=" + description + ", image=" + image + "]";
	}

}
